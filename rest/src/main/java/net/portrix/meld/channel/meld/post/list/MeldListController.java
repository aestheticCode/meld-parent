package net.portrix.meld.channel.meld.post.list;

import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilder;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.api.Blob;
import net.portrix.generic.rest.api.Container;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.generic.time.TimeUtils;
import net.portrix.meld.channel.*;
import net.portrix.meld.channel.meld.comment.form.MeldCommentFormController;
import net.portrix.meld.channel.meld.comment.form.MeldCommentResponse;
import net.portrix.meld.channel.meld.post.form.MeldPostFormController;
import net.portrix.meld.channel.meld.like.form.MeldLikeResponse;
import net.portrix.meld.media.photos.Photo;
import net.portrix.meld.media.photos.form.PhotoFormController;
import net.portrix.meld.social.people.Category;
import net.portrix.meld.social.profile.Profile;
import net.portrix.meld.usercontrol.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrick Bittner on 07/10/16.
 */
@Path("channel")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Name("Channel")
public class MeldListController {

    private final MeldListService service;

    private final URLBuilderFactory builderFactory;

    @Inject
    public MeldListController(MeldListService service, URLBuilderFactory builderFactory) {
        this.service = service;
        this.builderFactory = builderFactory;
    }

    public MeldListController() {
        this(null, null);
    }

    @Secured
    @GET
    @Path("meld/posts")
    @Name("Meld Posts")
    @Transactional
    public Container<AbstractMeldItem> list(@BeanParam MeldSearch search) {

        final User currentUser = service.currentUser();

        final List<MeldPost> posts = service.find(search);
        final long countAll = service.count(search);

        final List<AbstractMeldItem> items = new ArrayList<>();

        for (MeldPost post : posts) {

            AbstractMeldItem response = (AbstractMeldItem) post.accept(new MeldPost.Visitor() {
                @Override
                public Object visit(MeldImagePost post) {
                    MeldImageItem item = new MeldImageItem();
                    processPost(item, post, currentUser);
                    final MeldImage image = post.getImage();
                    Blob blob = new Blob();
                    blob.setName(image.getFileName());
                    blob.setData(image.getImage());
                    blob.setLastModified(image.getLastModified());
                    item.setImage(blob);
                    return item;
                }

                @Override
                public Object visit(MeldTextPost post) {
                    MeldTextItem item = new MeldTextItem();
                    processPost(item, post, currentUser);
                    return item;

                }

                @Override
                public Object visit(MeldYouTubePost post) {
                    MeldYouTubeItem item = new MeldYouTubeItem();
                    processPost(item, post, currentUser);
                    item.setVideoId(post.getVideoId());
                    return item;

                }

                @Override
                public Object visit(MeldPhotoPost post) {
                    MeldPhotoItem item = new MeldPhotoItem();
                    processPost(item, post, currentUser);
                    Photo photo = post.getPhoto();
                    URI photoPost = PhotoFormController.linkPhoto(photo, builderFactory)
                            .generateUri();
                    item.setPhoto(photoPost);
                    return item;
                }

                @Override
                public Object visit(MeldLinkPost post) {
                    MeldLinkItem item = new MeldLinkItem();
                    processPost(item, post, currentUser);
                    final MeldImage image = post.getImage();
                    Blob blob = new Blob();
                    blob.setName(image.getFileName());
                    blob.setData(image.getImage());
                    blob.setLastModified(image.getLastModified());
                    item.setImage(blob);
                    item.setLink(post.getLink());
                    return item;

                }
            });

            items.add(response);

        }

        return new Container<>(items, (int) countAll);
    }

    private void processPost(AbstractMeldItem item, MeldPost post, User currentUser) {
        item.setId(post.getId());
        item.setName(post.getUser().getFirstName() + " " + post.getUser().getLastName());
        item.setText(post.getText());
        item.setTime(TimeUtils.format(post.getCreated()));
        Category category = post.getCategory();
        if (category == null) {
            item.setCategory("public");
        } else {
            item.setCategory(category.getName());
        }
        for (User user : post.getLikes()) {


            MeldLikeResponse likeResponse = new MeldLikeResponse();
            likeResponse.setCurrent(currentUser.equals(user));

            Profile profile = service.findProfile(user);

            if (profile != null) {
                URI avatar = PhotoFormController.linkThumbnail(profile.getUserPhoto(), builderFactory)
                        .generateUri();
                likeResponse.setAvatar(avatar);
            }

            item.addLike(likeResponse);
        }
        if (post.getUser().equals(currentUser)) {
            MeldPostFormController.linkRead(post, builderFactory)
                    .buildSecured(item::addLink);
        }
        createComments(currentUser, post.getComments(), item.getComments());

        Profile profile = service.findProfile(post.getUser());
        if (profile != null) {
            URI avatarPost = PhotoFormController.linkThumbnail(profile.getUserPhoto(), builderFactory)
                    .generateUri();
            item.setAvatar(avatarPost);
        }
    }

    private void createComments(User currentUser, List<MeldComment> comments, List<MeldCommentResponse> commentResponses) {
        for (MeldComment comment : comments) {
            MeldCommentResponse commentResponse = new MeldCommentResponse();
            commentResponse.setId(comment.getId());
            commentResponse.setName(comment.getUser().getFirstName() + " " + comment.getUser().getLastName());
            commentResponse.setText(comment.getText());
            commentResponse.setTime(TimeUtils.format(comment.getCreated()));

            if (comment.getUser().equals(currentUser)) {
                MeldCommentFormController.linkUpdate(comment, builderFactory)
                        .buildSecured(commentResponse::addLink);
                MeldCommentFormController.linkDelete(comment, builderFactory)
                        .buildSecured(commentResponse::addLink);
            }


            for (User user : comment.getLikes()) {

                Profile profile = service.findProfile(user);

                MeldLikeResponse likeResponse = new MeldLikeResponse();
                likeResponse.setCurrent(currentUser.equals(user));

                if (profile != null) {
                    URI avatar = PhotoFormController.linkThumbnail(profile.getUserPhoto(), builderFactory)
                            .generateUri();
                    likeResponse.setAvatar(avatar);
                }


                commentResponse.addLike(likeResponse);
            }

            Profile profile = service.findProfile(comment.getUser());
            if (profile != null) {
                URI avatarLink = PhotoFormController.linkThumbnail(profile.getUserPhoto(), builderFactory)
                        .generateUri();

                commentResponse.setAvatar(avatarLink);
            }

            commentResponses.add(commentResponse);

            createComments(currentUser, comment.getComments(), commentResponse.getComments());
        }
    }


    public static URLBuilder<MeldListController> linkMeld(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(MeldListController.class)
                .record(method -> method.list(null))
                .rel("meld");
    }


}
