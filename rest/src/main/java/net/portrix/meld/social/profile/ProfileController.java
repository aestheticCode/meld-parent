package net.portrix.meld.social.profile;

import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilder;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.api.Blob;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.meld.media.photos.Photo;
import net.portrix.meld.social.profile.education.EducationFormController;
import net.portrix.meld.usercontrol.User;
import org.apache.commons.io.IOUtils;
import org.picketlink.Identity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
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
            final InputStream inputStream = Thread
                    .currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("/META-INF/images/way.jpg");

            try {
                byte[] bytes = IOUtils.toByteArray(inputStream);

                Blob image = new Blob();

                image.setName("way.jpg");
                image.setData(bytes);
                image.setLastModified(LocalDateTime.now());

                response.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (identity.isLoggedIn()) {
                linkProfileBackgroundUpdate(factory)
                        .buildSecured(response::addLink);
            }

            return response;
        } else {
            Blob image = new Blob();
            image.setName(profile.getBackgroundPhoto().getFileName());
            image.setData(profile.getBackgroundPhoto().getImage());
            image.setLastModified(profile.getBackgroundPhoto().getLastModified());
            response.setImage(image);

            if (identity.isLoggedIn()) {
                linkProfileBackgroundUpdate(factory)
                        .buildSecured(response::addLink);
            }

            return response;
        }

    }

    @GET
    @Path("user/current/profile/user")
    @Name("Profile User Read")
    @Secured
    public ProfileResponse readUser() {
        User user = service.currentUser();
        return readUser(user.getId());
    }

    @GET
    @Path("user/{id}/profile/user")
    @Name("Profile User Read")
    @Secured
    public ProfileResponse readUser(@PathParam("id") UUID id) {
        ProfileResponse response = new ProfileResponse();

        User current = service.findUser(id);

        response.setName(current.getFirstName() + " " + current.getLastName());

        Profile profile = service.find(current);

        if (profile == null) {
            final InputStream inputStream = Thread
                    .currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("/META-INF/images/user.png");

            try {
                byte[] bytes = IOUtils.toByteArray(inputStream);

                Blob image = new Blob();

                image.setName("way.jpg");
                image.setData(bytes);
                image.setLastModified(LocalDateTime.now());

                response.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (identity.isLoggedIn()) {
                linkProfileUserUpdate(factory)
                        .buildSecured(response::addLink);
            }

            return response;
        } else {
            Blob image = new Blob();
            image.setName(profile.getUserPhoto().getFileName());
            image.setData(profile.getUserPhoto().getImage());
            image.setLastModified(profile.getUserPhoto().getLastModified());
            response.setImage(image);

            if (identity.isLoggedIn()) {
                linkProfileUserUpdate(factory)
                        .buildSecured(response::addLink);
            }

            return response;
        }
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

        return readUser();
    }


    public static URLBuilder<ProfileController> linkProfileUser(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(ProfileController.class)
                .record(ProfileController::readUser)
                .rel("profile");
    }

    public static URLBuilder<ProfileController> linkProfileBackground(URLBuilderFactory builderFactory) {
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
