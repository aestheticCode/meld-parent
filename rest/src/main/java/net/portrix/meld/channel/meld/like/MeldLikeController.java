package net.portrix.meld.channel.meld.like;

import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilder;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.api.Link;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.meld.channel.MeldComment;
import net.portrix.meld.channel.MeldImagePost;
import net.portrix.meld.channel.MeldPost;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Patrick Bittner on 07/12/2016.
 */
@Path("channel")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Name("Channel")
public class MeldLikeController {

    private final MeldLikeService service;

    private final URLBuilderFactory builderFactory;

    @Inject
    public MeldLikeController(MeldLikeService service, URLBuilderFactory builderFactory) {
        this.service = service;
        this.builderFactory = builderFactory;
    }

    public MeldLikeController() {
        this(null, null);
    }

    @Secured
    @GET
    @Path("meld/{id}/plus/one")
    @Name("Meld Post Plus One")
    @Transactional
    public List<MeldLikeResponse> plusOnePost(@PathParam("id") UUID id) {

        final User currentUser = service.currentUser();
        final MeldPost post = service.findPost(id);

        if (post.containsLike(currentUser)) {
            post.removeLike(currentUser);
        } else {
            post.addLike(currentUser);
        }

        List<MeldLikeResponse> likes = new ArrayList<>();
        for (User user : post.getLikes()) {
            MeldLikeResponse response = new MeldLikeResponse();
            response.setCurrent(currentUser.equals(user));

            Profile profile = service.findProfile(user);
            if (profile != null) {
                URI avatar = PhotoFormController.linkThumbnail(profile.getUserPhoto(), builderFactory)
                        .generateUri();

                response.setAvatar(avatar);
            }


            likes.add(response);
        }

        return likes;
    }

    @Secured
    @GET
    @Path("meld/comment/{id}/plus/one")
    @Name("Meld Post Plus One")
    @Transactional
    public List<MeldLikeResponse> plusOneComment(@PathParam("id") UUID id) {

        final User currentUser = service.currentUser();
        final MeldComment post = service.findComment(id);

        if (post.containsLike(currentUser)) {
            post.removeLike(currentUser);
        } else {
            post.addLike(currentUser);
        }

        List<MeldLikeResponse> likes = new ArrayList<>();
        for (User user : post.getLikes()) {
            MeldLikeResponse response = new MeldLikeResponse();
            response.setCurrent(currentUser.equals(user));

            Profile profile = service.findProfile(user);
            if (profile != null) {
                URI avatar = PhotoFormController.linkThumbnail(profile.getUserPhoto(), builderFactory)
                        .generateUri();

                response.setAvatar(avatar);
            }

            likes.add(response);
        }

        return likes;
    }

    public static URLBuilder<MeldLikeController> linkPlusOneComment(MeldComment comment, URLBuilderFactory builderFactory) {
        return builderFactory
                .from(MeldLikeController.class)
                .record(method -> method.plusOneComment(comment.getId()))
                .rel("plusOneComment");
    }

    public static URLBuilder<MeldLikeController> linkPlusOnePost(MeldImagePost post, URLBuilderFactory builderFactory) {
        return builderFactory
                .from(MeldLikeController.class)
                .record(method -> method.plusOnePost(post.getId()))
                .rel("plusOnePost");
    }
}
