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
    @Path("profile")
    @Name("Profile Read")
    @Secured
    public ProfileResponse read() {
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
            image.setName(profile.getPhoto().getFileName());
            image.setData(profile.getPhoto().getImage());
            image.setLastModified(profile.getPhoto().getLastModified());
            response.setImage(image);
            return response;
        }

    }

    @POST
    @Path("profile")
    @Name("Profile Read")
    @Secured
    @Transactional
    public ProfileResponse update(ProfileForm form) {

        final User user = service.currentUser();

        Profile profile = service.find(user);

        if (profile == null) {
            profile = new Profile();
            profile.setUser(user);
        }

        Photo photo = service.find(form.getPhotoId());
        profile.setPhoto(photo);

        if (profile.getId() == null) {
            service.save(profile);
        }

        return read();
    }

    public static URLBuilder<ProfileController> linkProfile(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(ProfileController.class)
                .record(ProfileController::read)
                .rel("profile");
    }


}
