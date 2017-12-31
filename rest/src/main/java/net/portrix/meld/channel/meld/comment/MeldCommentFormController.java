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
import net.portrix.meld.media.photos.form.PhotoFormController;
import net.portrix.meld.social.profile.Profile;
import net.portrix.meld.usercontrol.User;
import net.portrix.meld.usercontrol.user.image.UserImageController;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.net.URI;
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
    @Path("meld/post/{id}/comment/create")
    @Name("Meld Post create Comment")
    @Transactional
    public MeldCommentResponse create(@PathParam("id") UUID id) {
        MeldCommentResponse response = new MeldCommentResponse();

        MeldPost post = service.findPost(id);

        linkSave(post, builderFactory)
                .buildSecured(response::addLink);

        return response;
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

        MeldCommentFormController.linkDelete(meldComment, builderFactory)
                .buildSecured(response::addLink);

        for (User user : meldComment.getLikes()) {
            MeldLikeResponse likeResponse = new MeldLikeResponse();
            likeResponse.setCurrent(current.equals(user));

            Profile profile = service.findProfile(user);
            if (profile != null) {
                URI avatar = PhotoFormController.linkThumbnail(profile.getUserPhoto(), builderFactory)
                        .generateUri();

                likeResponse.setAvatar(avatar);
            }


            response.addLike(likeResponse);
        }

        final URI avatarLink = builderFactory.from(UserImageController.class)
                .record(method -> method.thumbNail(current.getId()))
                .generateUri();

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

        MeldCommentFormController.linkDelete(meldComment, builderFactory)
                .buildSecured(response::addLink);

        for (User user : meldComment.getLikes()) {
            MeldLikeResponse likeResponse = new MeldLikeResponse();
            likeResponse.setCurrent(current.equals(user));
            Profile profile = service.findProfile(user);
            if (profile != null) {
                URI avatar = PhotoFormController.linkThumbnail(profile.getUserPhoto(), builderFactory)
                        .generateUri();

                likeResponse.setAvatar(avatar);
            }

            response.addLike(likeResponse);
        }

        Profile profile = service.findProfile(current);
        URI avatarLink = PhotoFormController.linkThumbnail(profile.getUserPhoto(), builderFactory)
                .generateUri();

        response.setAvatar(avatarLink);

        return response;


    }

    @DELETE
    @Secured
    @Path("meld/post/comment/{id}")
    @Name("Meld Post add Comment")
    @Transactional
    public void delete(@PathParam("id") UUID id) {
        MeldComment comment = service.findComment(id);

        User user = service.currentUser();

        if (comment.getUser() == user) {

            MeldPost post = service.findPost(comment);

            post.removeComment(comment);

            service.deleteComment(comment);

        }
    }


    public static URLBuilder<MeldCommentFormController> linkCreate(MeldPost post, URLBuilderFactory builderFactory) {
        return builderFactory
                .from(MeldCommentFormController.class)
                .record(method -> method.create(post.getId()))
                .rel("create");
    }

    public static URLBuilder<MeldCommentFormController> linkUpdate(MeldComment comment, URLBuilderFactory builderFactory) {
        return builderFactory
                .from(MeldCommentFormController.class)
                .record(method -> method.update(comment.getId(), null))
                .rel("update");
    }

    public static URLBuilder<MeldCommentFormController> linkSave(MeldPost post, URLBuilderFactory builderFactory) {
        return builderFactory
                .from(MeldCommentFormController.class)
                .record(method -> method.save(post.getId(), null))
                .rel("save");
    }

    public static URLBuilder<MeldCommentFormController> linkDelete(MeldComment comment, URLBuilderFactory builderFactory) {
        return builderFactory
                .from(MeldCommentFormController.class)
                .record(method -> method.delete(comment.getId()))
                .rel("delete");
    }

}
