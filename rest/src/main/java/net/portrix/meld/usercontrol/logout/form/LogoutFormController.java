package net.portrix.meld.usercontrol.logout.form;

import net.portrix.generic.rest.URLBuilder;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.api.Link;
import net.portrix.generic.rest.api.LinksContainer;
import net.portrix.meld.usercontrol.login.form.LoginFormController;
import net.portrix.meld.usercontrol.registration.form.RegistrationFormController;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Patrick Bittner on 13.06.2015.
 */
@Path("usercontrol")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LogoutFormController {

    private final LogoutFormService service;

    private final URLBuilderFactory builderFactory;

    @Inject
    public LogoutFormController(LogoutFormService service, URLBuilderFactory builderFactory) {
        this.service = service;
        this.builderFactory = builderFactory;
    }

    public LogoutFormController() {
        this(null, null);
    }

    @GET
    @Path("logout/form")
    public Response logout() {

        service.logout();

        final LinksContainer container = new LinksContainer() {

            private HashSet<Link> links = new HashSet<>();

            @Override
            public Set<Link> getLinks() {
                return links;
            }

            @Override
            public boolean addLink(Link link) {
                return links.add(link);
            }
        };

        LoginFormController.linkLogin(builderFactory)
                .build(container::addLink);

        RegistrationFormController.linkLogin(builderFactory)
                .build(container::addLink);

        return Response
                .ok(container)
                .cookie(new NewCookie("rememberMe", "", "/", "", "", 0, false))
                .build();
    }

    public static URLBuilder<LogoutFormController> linkLogout(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(LogoutFormController.class)
                .record(LogoutFormController::logout)
                .rel("logout");
    }

}
