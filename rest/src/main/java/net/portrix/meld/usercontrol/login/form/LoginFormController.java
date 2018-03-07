package net.portrix.meld.usercontrol.login.form;

import net.portrix.generic.rest.URLBuilder;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.meld.channel.meld.post.list.MeldListController;
import net.portrix.meld.media.photos.grid.PhotoGridController;
import net.portrix.meld.social.communities.CommunitiesController;
import net.portrix.meld.social.people.PeopleController;
import net.portrix.meld.social.profile.ProfileController;
import net.portrix.meld.usercontrol.group.table.GroupTableController;
import net.portrix.meld.usercontrol.logout.form.LogoutFormController;
import net.portrix.meld.usercontrol.role.table.RoleTableController;
import net.portrix.meld.usercontrol.user.table.UserTableController;
import org.picketlink.Identity;
import org.picketlink.authentication.UserAlreadyLoggedInException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.UUID;

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
    @Transactional
    public Response login(LoginForm loginForm) {

        UUID uuid = UUID.randomUUID();

        Identity.AuthenticationResult result;
        try {
            result = service.login(uuid, loginForm);

        } catch (UserAlreadyLoggedInException e) {

            return generateResponse(uuid, loginForm);
        }

        if (result == Identity.AuthenticationResult.FAILED) {
            return Response
                    .status(Response.Status.FORBIDDEN)
                    .entity(loginForm)
                    .build();
        }

        return generateResponse(uuid, loginForm);
    }

    private Response generateResponse(UUID id, LoginForm loginForm) {
        UserTableController.linkUsers(builderFactory)
                .buildSecured(loginForm::addLink);
        GroupTableController.linkGroups(builderFactory)
                .buildSecured(loginForm::addLink);
        RoleTableController.linkRoles(builderFactory)
                .buildSecured(loginForm::addLink);
        MeldListController.linkMeld(builderFactory)
                .buildSecured(loginForm::addLink);
        ProfileController.linkProfile(builderFactory)
                .buildSecured(loginForm::addLink);
        PeopleController.linkPeople(builderFactory)
                .buildSecured(loginForm::addLink);
        CommunitiesController.linkCommunities(builderFactory)
                .buildSecured(loginForm::addLink);
        PhotoGridController.linkList(builderFactory)
                .buildSecured(loginForm::addLink);

        LogoutFormController.linkLogout(builderFactory)
                .build(loginForm::addLink);

        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("dMMMuuuu")
                .toFormatter();
        String birthday = loginForm.getBirthday().format(formatter);
        String userId = loginForm.getFirstName() + loginForm.getLastName() + birthday;


        String tokenValue = userId + "." + id.toString();
        return Response
                .ok()
                .cookie(new NewCookie("rememberMe", tokenValue, "/", "", "", 365 * 24 * 60 * 60, false))
                .entity(loginForm)
                .build();
    }

    public static URLBuilder<LoginFormController> linkLogin(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(LoginFormController.class)
                .record(controller -> controller.login(null))
                .rel("login");
    }


}
