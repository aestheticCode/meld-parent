package net.portrix.meld.social.profile;

import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilder;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.meld.media.photos.Photo;
import net.portrix.meld.media.photos.form.PhotoFormController;
import net.portrix.meld.usercontrol.User;
import org.picketlink.Identity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

/**
 * @author Patrick Bittner on 25/11/2016.
 */
@Path("social")
@ApplicationScoped
@Name("Social")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProfileController {

    private final ProfileService service;

    private final URLBuilderFactory factory;

    private final Identity identity;


    @Inject
    public ProfileController(ProfileService service, URLBuilderFactory factory, Identity identity) {
        this.service = service;
        this.factory = factory;
        this.identity = identity;
    }

    public ProfileController() {
        this(null, null, null);
    }

    @GET
    @Path("user/current/profile/background")
    @Name("Profile Background Read")
    @Secured
    public ProfileResponse readBackground() {
        final User user = service.currentUser();
        return readBackground(user.getId());
    }

    @GET
    @Path("user/{id}/profile/background")
    @Name("Profile Background Read")
    @Secured
    public ProfileResponse readBackground(@PathParam("id") UUID id) {
        final ProfileResponse response = new ProfileResponse();

        User current = service.findUser(id);

        response.setName(current.getFirstName() + " " + current.getLastName());

        Profile profile = service.find(current);

        if (profile == null) {
            return new ProfileResponse();
        }

        response.setImage(PhotoFormController.linkPhoto(profile.getUserPhoto(), factory)
                .generateUri());

        response.setBackground(PhotoFormController.linkPhoto(profile.getBackgroundPhoto(), factory)
                .generateUri());

        if (identity.isLoggedIn()) {
            linkProfileBackgroundUpdate(factory)
                    .buildSecured(response::addLink);
        }

        return response;
    }


    @POST
    @Path("user/current/profile/background")
    @Name("Profile Background Read")
    @Secured
    @Transactional
    public ProfileResponse updateBackground(ProfileForm form) {

        final User user = service.currentUser();

        Profile profile = service.find(user);

        if (profile == null) {
            profile = new Profile();
            profile.setUser(user);
        }

        Photo photo = service.find(form.getPhotoId());
        profile.setBackgroundPhoto(photo);

        if (profile.getId() == null) {
            service.save(profile);
        }

        return readBackground();
    }

    @POST
    @Path("user/current/profile/user")
    @Name("Profile User Update")
    @Secured
    @Transactional
    public ProfileResponse updateUser(ProfileForm form) {

        final User user = service.currentUser();

        Profile profile = service.find(user);

        if (profile == null) {
            profile = new Profile();
            profile.setUser(user);
        }

        Photo photo = service.find(form.getPhotoId());
        profile.setUserPhoto(photo);

        if (profile.getId() == null) {
            service.save(profile);
        }

        return readBackground();
    }



    public static URLBuilder<ProfileController> linkProfile(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(ProfileController.class)
                .record(ProfileController::readBackground)
                .rel("profile");
    }

    public static URLBuilder<ProfileController> linkProfileUserUpdate(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(ProfileController.class)
                .record((method) -> method.updateUser(null))
                .rel("update");
    }

    public static URLBuilder<ProfileController> linkProfileBackgroundUpdate(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(ProfileController.class)
                .record((method) -> method.updateBackground(null))
                .rel("update");
    }

}
