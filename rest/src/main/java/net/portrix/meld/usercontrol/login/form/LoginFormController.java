package net.portrix.meld.usercontrol.login.form;

import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.api.Links;
import net.portrix.generic.rest.api.LinksContainer;
import net.portrix.meld.channel.meld.list.MeldListController;
import net.portrix.meld.social.ProfileController;
import net.portrix.meld.usercontrol.logout.form.LogoutFormController;
import net.portrix.meld.usercontrol.role.table.RoleTableController;
import net.portrix.meld.usercontrol.group.table.GroupTableController;
import net.portrix.meld.usercontrol.user.form.UserFormController;
import net.portrix.meld.usercontrol.user.table.UserTableController;
import org.picketlink.Identity;
import org.picketlink.authentication.UserAlreadyLoggedInException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author by Patrick Bittner on 12.06.15.
 */
@Path("usercontrol")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginFormController {

    private final LoginFormService service;

    private final URLBuilderFactory builderFactory;


    @Inject
    public LoginFormController(LoginFormService service, URLBuilderFactory builderFactory) {
        this.service = service;
        this.builderFactory = builderFactory;
    }

    public LoginFormController() {
        this(null, null);
    }

    @POST
    @Path("login/form")
    public Response login(LoginForm loginForm) {

        Identity.AuthenticationResult result;
        try {
            result = service.login(loginForm);

        } catch (UserAlreadyLoggedInException e) {

            UserTableController.linkUsers(loginForm, builderFactory);
            GroupTableController.linkGroups(loginForm, builderFactory);
            RoleTableController.linkRoles(loginForm, builderFactory);
            MeldListController.linkMeld(loginForm, builderFactory);
            ProfileController.linkProfile(loginForm, builderFactory);
            LogoutFormController.linkLogout(loginForm, builderFactory);

            return Response
                    .ok()
                    .entity(loginForm)
                    .build();
        }

        if (result == Identity.AuthenticationResult.FAILED) {
            return Response
                    .status(Response.Status.FORBIDDEN)
                    .entity(loginForm)
                    .build();
        }

        UserTableController.linkUsers(loginForm, builderFactory);
        GroupTableController.linkGroups(loginForm, builderFactory);
        RoleTableController.linkRoles(loginForm, builderFactory);
        MeldListController.linkMeld(loginForm, builderFactory);
        ProfileController.linkProfile(loginForm, builderFactory);
        LogoutFormController.linkLogout(loginForm, builderFactory);

        return Response
                .ok()
                .entity(loginForm)
                .build();
    }

    public static void linkLogin(LinksContainer container, URLBuilderFactory builderFactory) {
        builderFactory
                .from(LoginFormController.class)
                .record(controller -> controller.login(new LoginForm()))
                .rel("login")
                .build(container::addLink);
    }


}
