package net.portrix.meld.channel.meld.list;

import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.api.Container;
import net.portrix.generic.rest.api.Link;
import net.portrix.generic.rest.api.LinksContainer;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.generic.time.TimeUtils;
import net.portrix.meld.channel.MeldComment;
import net.portrix.meld.channel.MeldImage;
import net.portrix.meld.channel.MeldPost;
import net.portrix.meld.channel.meld.comment.MeldCommentController;
import net.portrix.meld.channel.meld.comment.MeldCommentResponse;
import net.portrix.meld.channel.meld.form.MeldPostFormController;
import net.portrix.meld.channel.meld.image.MeldImageController;
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
    public Container<MeldItemResponse> list(MeldListSearchType search) {

        final User currentUser = service.currentUser();

        final List<MeldPost> posts = service.findAll(search.getStart(), search.getLimit());

        final long countAll = service.countAll();

        final List<MeldItemResponse> items = new ArrayList<>();

        for (MeldPost post : posts) {

            MeldItemResponse item = new MeldItemResponse();
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
                MeldPostFormController.linkRead(post, item, builderFactory);
            }
            createComments(currentUser, post.getComments(), item.getComments());
            items.add(item);

            final MeldImage image = post.getImage();
            if (image != null) {
                final Link imageLink = builderFactory.from(MeldImageController.class)
                        .record(method -> method.image(post.getId()))
                        .rel("image")
                        .generate();

                item.setImage(imageLink);
            }

            final Link avatarLink = builderFactory.from(UserImageController.class)
                    .record(method -> method.thumbNail(post.getUser().getId()))
                    .rel("avatar")
                    .generate();

            item.setAvatar(avatarLink);
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
                MeldCommentController.linkUpdate(comment, commentResponse, builderFactory);
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


    public static void linkMeld(LinksContainer container, URLBuilderFactory builderFactory) {
        builderFactory
                .from(MeldListController.class)
                .record(method -> method.list(new MeldListSearchType()))
                .rel("meld")
                .buildSecured(container::addLink);
    }


}
