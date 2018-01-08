package net.portrix.meld;

import net.portrix.generic.image.ImageUtils;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.api.Blob;
import net.portrix.meld.channel.meld.list.MeldListController;
import net.portrix.meld.media.photos.form.PhotoFormController;
import net.portrix.meld.media.photos.grid.PhotoGridController;
import net.portrix.meld.social.people.category.table.CategoryTableController;
import net.portrix.meld.social.profile.Profile;
import net.portrix.meld.social.profile.ProfileController;
import net.portrix.meld.usercontrol.User;
import net.portrix.meld.usercontrol.group.table.GroupTableController;
import net.portrix.meld.usercontrol.login.form.LoginFormController;
import net.portrix.meld.usercontrol.logout.form.LogoutFormController;
import net.portrix.meld.usercontrol.registration.form.RegistrationFormController;
import net.portrix.meld.usercontrol.role.table.RoleTableController;
import net.portrix.meld.usercontrol.user.table.UserTableController;
import org.apache.commons.io.IOUtils;
import org.picketlink.Identity;
import org.picketlink.credential.DefaultLoginCredentials;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.time.LocalDateTime;

/**
 * @author Patrick Bittner on 06.06.2015.
 */
@Path("/")
@ApplicationScoped
public class ApplicationController {

    private final URLBuilderFactory builderFactory;

    private final ApplicationService service;

    private final DefaultLoginCredentials credentials;

    private final Identity identity;

    @Inject
    public ApplicationController(final URLBuilderFactory builderFactory, ApplicationService service, DefaultLoginCredentials credentials, Identity identity) {
        this.builderFactory = builderFactory;
        this.service = service;
        this.credentials = credentials;
        this.identity = identity;
    }

    public ApplicationController() {
        this(null, null, null, null);
    }

    public static Blob createUserPhotoBlob() {
        final InputStream inputStream = Thread
                .currentThread()
                .getContextClassLoader()
                .getResourceAsStream("/META-INF/images/user.png");
        byte[] bytes = null;
        try {
            bytes = IOUtils.toByteArray(inputStream);

            Blob blob = new Blob();
            blob.setName("user.png");
            blob.setData(ImageUtils.thumnail("user.png", bytes, 200));
            blob.setLastModified(LocalDateTime.now());
            return blob;
        } catch (IOException e) {
            return null;
        }
    }

    @GET
    @Produces("application/json")
    @Transactional
    public Application application() {

        final Application application = new Application();

        Application.User user = new Application.User();
        application.setUser(user);

        if (service.isLoggedIn()) {

            UserTableController.linkUsers(builderFactory)
                    .buildSecured(application::addLink);
            GroupTableController.linkGroups(builderFactory)
                    .buildSecured(application::addLink);
            RoleTableController.linkRoles(builderFactory)
                    .buildSecured(application::addLink);
            MeldListController.linkMeld(builderFactory)
                    .buildSecured(application::addLink);
            ProfileController.linkProfile(builderFactory)
                    .buildSecured(application::addLink);
            CategoryTableController.linkProfile(builderFactory)
                    .buildSecured(application::addLink);
            PhotoGridController.linkList(builderFactory)
                    .buildSecured(application::addLink);


            final User current = service.current();

            Profile profile = service.findProfile(current);

            URI uri = PhotoFormController.linkThumbnail(profile.getUserPhoto(), builderFactory)
                    .generateUri();

            user.setAvatar(uri);

            user.setId(current.getId());
            user.setEmail(current.getName());
            user.setFirstName(current.getFirstName());
            user.setLastName(current.getLastName());
            user.setBirthday(current.getBirthdate());

            LogoutFormController.linkLogout(builderFactory)
                    .build(application::addLink);
        } else {

            user.setEmail("guest");
            LoginFormController.linkLogin(builderFactory)
                    .build(application::addLink);

            RegistrationFormController.linkLogin(builderFactory)
                    .build(application::addLink);

        }

        return application;
    }


}
