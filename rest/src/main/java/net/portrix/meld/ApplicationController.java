package net.portrix.meld;

import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.api.Link;
import net.portrix.meld.channel.meld.list.MeldListController;
import net.portrix.meld.social.ProfileController;
import net.portrix.meld.usercontrol.group.table.GroupTableController;
import net.portrix.meld.usercontrol.login.form.LoginFormController;
import net.portrix.meld.usercontrol.logout.form.LogoutFormController;
import net.portrix.meld.usercontrol.role.table.RoleTableController;
import net.portrix.meld.usercontrol.user.image.UserImageController;
import net.portrix.meld.usercontrol.user.table.UserTableController;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * @author Patrick Bittner on 06.06.2015.
 */
@Path("/")
@ApplicationScoped
public class ApplicationController {

    private final URLBuilderFactory builderFactory;

    private final ApplicationService service;

    @Inject
    public ApplicationController(final URLBuilderFactory builderFactory, ApplicationService service) {
        this.builderFactory = builderFactory;
        this.service = service;
    }

    public ApplicationController() {
        this(null, null);
    }

    @GET
    @Produces("application/json")
    @Transactional
    public Application application() {

        final Application application = new Application();

        UserTableController.linkUsers(application, builderFactory);
        GroupTableController.linkGroups(application, builderFactory);
        RoleTableController.linkRoles(application, builderFactory);
        MeldListController.linkMeld(application, builderFactory);
        ProfileController.linkProfile(application, builderFactory);

        Application.User user = new Application.User();
        application.setUser(user);

        if (service.isLoggedIn()) {
            final net.portrix.meld.usercontrol.User current = service.current();


            user.setId(current.getId());
            user.setEmail(current.getName());
            user.setFirstName(current.getFirstName());
            user.setLastName(current.getLastName());
            user.setBirthday(current.getBirthdate());

            final Link avatarLink = builderFactory.from(UserImageController.class)
                    .record(method -> method.thumbNail(current.getId()))
                    .rel("avatar")
                    .generate();

            user.setAvatar(avatarLink);

            LogoutFormController.linkLogout(application, builderFactory);
        } else {

            user.setEmail("guest");
            LoginFormController.linkLogin(application, builderFactory);

        }

        return application;
    }


}
