package net.portrix.meld;

import net.portrix.generic.image.ImageUtils;
import net.portrix.generic.rest.LoginToken;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.api.Blob;
import net.portrix.generic.rest.api.Link;
import net.portrix.meld.channel.meld.list.MeldListController;
import net.portrix.meld.media.photos.grid.PhotoGridController;
import net.portrix.meld.social.people.category.table.CategoryTableController;
import net.portrix.meld.social.profile.ProfileController;
import net.portrix.meld.usercontrol.User;
import net.portrix.meld.usercontrol.group.table.GroupTableController;
import net.portrix.meld.usercontrol.login.form.LoginFormController;
import net.portrix.meld.usercontrol.logout.form.LogoutFormController;
import net.portrix.meld.usercontrol.registration.form.RegistrationFormController;
import net.portrix.meld.usercontrol.role.table.RoleTableController;
import net.portrix.meld.usercontrol.user.image.UserImageController;
import net.portrix.meld.usercontrol.user.table.UserTableController;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.picketlink.Identity;
import org.picketlink.credential.DefaultLoginCredentials;
import org.picketlink.idm.credential.TokenCredential;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.io.IOException;
import java.io.InputStream;
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
    public Application application(@CookieParam("rememberMe") String token) {

        final Application application = new Application();

        UserTableController.linkUsers(builderFactory)
                .buildSecured(application::addLink);
        GroupTableController.linkGroups(builderFactory)
                .buildSecured(application::addLink);
        RoleTableController.linkRoles(builderFactory)
                .buildSecured(application::addLink);
        MeldListController.linkMeld(builderFactory)
                .buildSecured(application::addLink);
        ProfileController.linkProfileUser(builderFactory)
                .buildSecured(application::addLink);
        CategoryTableController.linkProfile(builderFactory)
                .buildSecured(application::addLink);
        PhotoGridController.linkList(builderFactory)
                .buildSecured(application::addLink);

        Application.User user = new Application.User();
        application.setUser(user);

        if (service.isLoggedIn()) {
            generateResponse(application, user);
        } else {

            if (StringUtils.isNotEmpty(token)) {
                String[] split = token.split("\\.");
                credentials.setCredential(new TokenCredential(new LoginToken(split[0], split[1])));
                Identity.AuthenticationResult result = identity.login();

                if (result.equals(Identity.AuthenticationResult.SUCCESS)) {
                    generateResponse(application, user);
                } else {
                    user.setEmail("guest");
                    LoginFormController.linkLogin(builderFactory)
                            .build(application::addLink);

                    RegistrationFormController.linkLogin(builderFactory)
                            .build(application::addLink);
                }
            } else {
                user.setEmail("guest");
                LoginFormController.linkLogin(builderFactory)
                        .build(application::addLink);

                RegistrationFormController.linkLogin(builderFactory)
                        .build(application::addLink);
            }
        }





        return application;
    }

    private void generateResponse(Application application, Application.User user) {
        final User current = service.current();

        user.setId(current.getId());
        user.setEmail(current.getName());
        user.setFirstName(current.getFirstName());
        user.setLastName(current.getLastName());
        user.setBirthday(current.getBirthdate());

        final Link avatarLink = builderFactory.from(UserImageController.class)
                .record(method -> method.thumbNail(current.getId()))
                .rel("avatar")
                .generate();

        final Link imageLink = builderFactory.from(UserImageController.class)
                .record(method -> method.image(current.getId()))
                .rel("avatar")
                .generate();

        user.setAvatar(avatarLink);
        user.setImage(imageLink);

        LogoutFormController.linkLogout(builderFactory)
                .build(application::addLink);
    }


}
