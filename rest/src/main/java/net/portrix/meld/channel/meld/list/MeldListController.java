package net.portrix.meld.channel.meld.list;

import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilder;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.api.Blob;
import net.portrix.generic.rest.api.Container;
import net.portrix.generic.rest.api.Link;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.generic.time.TimeUtils;
import net.portrix.meld.channel.*;
import net.portrix.meld.channel.meld.comment.MeldCommentController;
import net.portrix.meld.channel.meld.comment.MeldCommentResponse;
import net.portrix.meld.channel.meld.form.MeldPostFormController;
import net.portrix.meld.channel.meld.like.MeldLikeResponse;
import net.portrix.meld.usercontrol.User;
import net.portrix.meld.usercontrol.user.image.UserImageController;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
    @POST
    @Path("meld/posts")
    @Name("Meld Posts")
    @Transactional
    public Container<MeldItem> list(MeldListSearchType search) {

        final User currentUser = service.currentUser();

        final List<MeldPost> posts = service.findAll(search.getStart(), search.getLimit());

        final long countAll = service.countAll();

        final List<MeldItem> items = new ArrayList<>();

        for (MeldPost post : posts) {

            MeldItem response = (MeldItem) post.accept(new MeldPost.Visitor() {
                @Override
                public Object visit(MeldImagePost post) {
                    MeldImageItem item = new MeldImageItem();
                    item.setId(post.getId());
                    item.setName(post.getUser().getFirstName() + " " + post.getUser().getLastName());
                    item.setText(post.getText());
                    item.setTime(TimeUtils.format(post.getCreated()));
                    for (User user : post.getLikes()) {

                        final Link avatarLink = builderFactory.from(UserImageController.class)
                                .record(method -> method.thumbNail(user.getId()))
                                .rel("avatar")
                                .generate();

                        MeldLikeResponse likeResponse = new MeldLikeResponse();
                        likeResponse.setCurrent(currentUser.equals(user));
                        likeResponse.setAvatar(avatarLink);

                        item.addLike(likeResponse);
                    }
                    if (post.getUser().equals(currentUser)) {
                        MeldPostFormController.linkRead(post, builderFactory)
                                .buildSecured(item::addLink);
                    }
                    createComments(currentUser, post.getComments(), item.getComments());

                    final MeldImage image = post.getImage();

                    Blob blob = new Blob();

                    blob.setName(image.getFileName());
                    blob.setData(image.getImage());
                    blob.setLastModified(image.getLastModified());

                    item.setImage(blob);

                    final Link avatarLink = builderFactory.from(UserImageController.class)
                            .record(method -> method.thumbNail(post.getUser().getId()))
                            .rel("avatar")
                            .generate();

                    item.setAvatar(avatarLink);

                    return item;
                }

                @Override
                public Object visit(MeldTextPost post) {
                    MeldTextItem item = new MeldTextItem();
                    item.setId(post.getId());
                    item.setName(post.getUser().getFirstName() + " " + post.getUser().getLastName());
                    item.setText(post.getText());
                    item.setTime(TimeUtils.format(post.getCreated()));
                    for (User user : post.getLikes()) {

                        final Link avatarLink = builderFactory.from(UserImageController.class)
                                .record(method -> method.thumbNail(user.getId()))
                                .rel("avatar")
                                .generate();

                        MeldLikeResponse likeResponse = new MeldLikeResponse();
                        likeResponse.setCurrent(currentUser.equals(user));
                        likeResponse.setAvatar(avatarLink);

                        item.addLike(likeResponse);
                    }
                    if (post.getUser().equals(currentUser)) {
                        MeldPostFormController.linkRead(post, builderFactory)
                                .buildSecured(item::addLink);
                    }
                    createComments(currentUser, post.getComments(), item.getComments());

                    final Link avatarLink = builderFactory.from(UserImageController.class)
                            .record(method -> method.thumbNail(post.getUser().getId()))
                            .rel("avatar")
                            .generate();

                    item.setAvatar(avatarLink);

                    return item;

                }

                @Override
                public Object visit(MeldYouTubePost post) {
                    MeldYouTubeItem item = new MeldYouTubeItem();
                    item.setId(post.getId());
                    item.setName(post.getUser().getFirstName() + " " + post.getUser().getLastName());
                    item.setText(post.getText());
                    item.setVideoId(post.getVideoId());
                    item.setTime(TimeUtils.format(post.getCreated()));
                    for (User user : post.getLikes()) {

                        final Link avatarLink = builderFactory.from(UserImageController.class)
                                .record(method -> method.thumbNail(user.getId()))
                                .rel("avatar")
                                .generate();

                        MeldLikeResponse likeResponse = new MeldLikeResponse();
                        likeResponse.setCurrent(currentUser.equals(user));
                        likeResponse.setAvatar(avatarLink);

                        item.addLike(likeResponse);
                    }
                    if (post.getUser().equals(currentUser)) {
                        MeldPostFormController.linkRead(post, builderFactory)
                                .buildSecured(item::addLink);
                    }
                    createComments(currentUser, post.getComments(), item.getComments());

                    final Link avatarLink = builderFactory.from(UserImageController.class)
                            .record(method -> method.thumbNail(post.getUser().getId()))
                            .rel("avatar")
                            .generate();

                    item.setAvatar(avatarLink);

                    return item;

                }
            });

            items.add(response);

        }

        return new Container<>(items, (int) countAll);
    }

    private void createComments(User currentUser, List<MeldComment> comments, List<MeldCommentResponse> commentResponses) {
        for (MeldComment comment : comments) {
            MeldCommentResponse commentResponse = new MeldCommentResponse();
            commentResponse.setId(comment.getId());
            commentResponse.setName(comment.getUser().getFirstName() + " " + comment.getUser().getLastName());
            commentResponse.setText(comment.getText());
            commentResponse.setTime(TimeUtils.format(comment.getCreated()));

            if (comment.getUser().equals(currentUser)) {
                MeldCommentController.linkUpdate(comment, builderFactory)
                        .buildSecured(commentResponse::addLink);
            }


            for (User user : comment.getLikes()) {

                final Link avatarLink = builderFactory.from(UserImageController.class)
                        .record(method -> method.thumbNail(user.getId()))
                        .rel("avatar")
                        .generate();

                MeldLikeResponse likeResponse = new MeldLikeResponse();
                likeResponse.setCurrent(currentUser.equals(user));
                likeResponse.setAvatar(avatarLink);

                commentResponse.addLike(likeResponse);
            }

            final Link avatarLink = builderFactory.from(UserImageController.class)
                    .record(method -> method.thumbNail(comment.getUser().getId()))
                    .rel("avatar")
                    .generate();

            commentResponse.setAvatar(avatarLink);

            commentResponses.add(commentResponse);

            createComments(currentUser, comment.getComments(), commentResponse.getComments());
        }
    }


    public static URLBuilder<MeldListController> linkMeld(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(MeldListController.class)
                .record(method -> method.list(new MeldListSearchType()))
                .rel("meld");
    }


}
