package net.portrix.meld;

import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.api.Link;
import net.portrix.meld.channel.meld.list.MeldListController;
import net.portrix.meld.social.people.category.list.CategoryController;
import net.portrix.meld.social.profile.ProfileController;
import net.portrix.meld.usercontrol.User;
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
        CategoryController.linkProfile(builderFactory)
                .buildSecured(application::addLink);

        Application.User user = new Application.User();
        application.setUser(user);

        if (service.isLoggedIn()) {
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
        } else {

            user.setEmail("guest");
            LoginFormController.linkLogin(builderFactory)
                    .build(application::addLink);

        }

        return application;
    }


}
