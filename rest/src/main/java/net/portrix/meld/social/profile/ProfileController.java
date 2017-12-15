package net.portrix.meld.social.profile;

import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilder;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.api.Blob;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.meld.media.photos.Photo;
import net.portrix.meld.usercontrol.User;
import org.apache.commons.io.IOUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

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

    @Inject
    public ProfileController(ProfileService service) {
        this.service = service;
    }

    public ProfileController() {
        this(null);
    }

    @GET
    @Path("profile/background")
    @Name("Profile Background Read")
    @Secured
    public ProfileResponse readBackground() {
        ProfileResponse response = new ProfileResponse();

        User current = service.currentUser();

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
            return response;
        } else {
            Blob image = new Blob();
            image.setName(profile.getBackgroundPhoto().getFileName());
            image.setData(profile.getBackgroundPhoto().getImage());
            image.setLastModified(profile.getBackgroundPhoto().getLastModified());
            response.setImage(image);
            return response;
        }
    }

    @GET
    @Path("profile/user")
    @Name("Profile User Read")
    @Secured
    public ProfileResponse readUser() {
        ProfileResponse response = new ProfileResponse();

        User current = service.currentUser();

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
            return response;
        } else {
            Blob image = new Blob();
            image.setName(profile.getUserPhoto().getFileName());
            image.setData(profile.getUserPhoto().getImage());
            image.setLastModified(profile.getUserPhoto().getLastModified());
            response.setImage(image);
            return response;
        }
    }


    @POST
    @Path("profile/background")
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
    @Path("profile/user")
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

}
