package net.portrix.meld.channel.meld.comment;

import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilder;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.api.Link;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.generic.time.TimeUtils;
import net.portrix.meld.channel.MeldComment;
import net.portrix.meld.channel.MeldPost;
import net.portrix.meld.channel.meld.like.MeldLikeResponse;
import net.portrix.meld.usercontrol.User;
import net.portrix.meld.usercontrol.user.image.UserImageController;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.Instant;
import java.util.UUID;

/**
 * @author Patrick Bittner on 30/11/2016.
 */
@Path("channel")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Name("Channel")
public class MeldCommentFormController {

    private final MeldCommentService service;

    private final URLBuilderFactory builderFactory;

    @Inject
    public MeldCommentFormController(MeldCommentService service, URLBuilderFactory builderFactory) {
        this.service = service;
        this.builderFactory = builderFactory;
    }

    public MeldCommentFormController() {
        this(null, null);
    }

    @POST
    @Secured
    @Path("meld/post/{id}/comment")
    @Name("Meld Post add Comment")
    @Transactional
    public MeldCommentResponse save(@PathParam("id") UUID id, MeldCommentForm comment) {

        final User current = service.currentUser();

        final MeldPost post = service.findPost(id);

        MeldComment meldComment = new MeldComment();
        meldComment.setText(comment.getText());
        meldComment.setCreated(Instant.now());
        meldComment.setUser(current);

        service.saveComment(meldComment);

        post.addComment(meldComment);

        MeldCommentResponse response = new MeldCommentResponse();

        response.setId(meldComment.getId());
        response.setName(meldComment.getUser().getFirstName() + " " + meldComment.getUser().getLastName());
        response.setTime(TimeUtils.format(meldComment.getCreated()));
        response.setText(meldComment.getText());

        MeldCommentFormController.linkUpdate(meldComment, builderFactory)
                .buildSecured(response::addLink);

        for (User user : meldComment.getLikes()) {
            MeldLikeResponse likeResponse = new MeldLikeResponse();
            likeResponse.setCurrent(current.equals(user));

            final Link avatarLink = builderFactory.from(UserImageController.class)
                    .record(method -> method.thumbNail(user.getId()))
                    .rel("avatar")
                    .generate();

            likeResponse.setAvatar(avatarLink);

            response.addLike(likeResponse);
        }

        final Link avatarLink = builderFactory.from(UserImageController.class)
                .record(method -> method.thumbNail(current.getId()))
                .rel("avatar")
                .generate();

        response.setAvatar(avatarLink);

        return response;
    }

    @PUT
    @Secured
    @Path("meld/post/comment/{id}")
    @Name("Meld Post add Comment")
    @Transactional
    public MeldCommentResponse update(@PathParam("id") UUID id, MeldCommentForm comment) {

        final User current = service.currentUser();

        final MeldComment meldComment = service.findComment(id);

        meldComment.setText(comment.getText());

        MeldCommentResponse response = new MeldCommentResponse();

        response.setId(meldComment.getId());
        response.setName(meldComment.getUser().getFirstName() + " " + meldComment.getUser().getLastName());
        response.setTime(TimeUtils.format(meldComment.getCreated()));
        response.setText(meldComment.getText());

        MeldCommentFormController.linkUpdate(meldComment, builderFactory)
                .buildSecured(response::addLink);

        for (User user : meldComment.getLikes()) {
            final Link avatarLink = builderFactory.from(UserImageController.class)
                    .record(method -> method.thumbNail(user.getId()))
                    .rel("avatar")
                    .generate();

            MeldLikeResponse likeResponse = new MeldLikeResponse();
            likeResponse.setCurrent(current.equals(user));
            likeResponse.setAvatar(avatarLink);

            response.addLike(likeResponse);
        }

        final Link avatarLink = builderFactory.from(UserImageController.class)
                .record(method -> method.thumbNail(current.getId()))
                .rel("avatar")
                .generate();

        response.setAvatar(avatarLink);

        return response;


    }

    public static URLBuilder<MeldCommentFormController> linkUpdate(MeldComment comment, URLBuilderFactory builderFactory) {
        return builderFactory
                .from(MeldCommentFormController.class)
                .record(method -> method.update(comment.getId(), null))
                .rel("update");
    }


}
