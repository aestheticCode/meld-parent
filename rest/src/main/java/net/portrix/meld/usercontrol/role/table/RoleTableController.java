package net.portrix.meld.usercontrol.role.table;

import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.api.Container;
import net.portrix.generic.rest.api.LinksContainer;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.meld.usercontrol.role.form.RoleFormController;
import net.portrix.meld.usercontrol.*;
import net.portrix.meld.usercontrol.role.table.query.Query;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrick Bittner on 30/09/16.
 */
@Path("usercontrol")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Name("User Control")
public class RoleTableController {

    private final RoleTableService service;

    private final URLBuilderFactory builderFactory;

    @Inject
    public RoleTableController(RoleTableService service, URLBuilderFactory builderFactory) {
        this.service = service;
        this.builderFactory = builderFactory;
    }

    public RoleTableController() {
        this(null, null);
    }

    @POST
    @Path("role/table")
    @Name("Role Table")
    @Secured
    @Transactional
    public Container<RoleRowResponse> list(Query search) {
        List<Role> Roles;
        long count = 0;
        if(search.getLimit() == 0) {
            Roles = new ArrayList<>();
        } else {
            Roles = service.findRoles(search);
            count = service.countRoles(search);
        }

        final List<RoleRowResponse> selects = new ArrayList<>();

        for (Role role : Roles) {
            RoleRowResponse response = new RoleRowResponse();

            response.setId(role.getId());
            response.setName(role.getName());

            for (Identity identity : role.getScopes()) {
                response.getMembers().add(identity.getName());
            }

            RoleFormController.linkRead(role, response, builderFactory);

            selects.add(response);
        }

        final Container<RoleRowResponse> container = new Container<>(selects, (int) count);

        RoleFormController.linkSave(container, builderFactory);

        return container;

    }

    public static void linkRoles(LinksContainer container, URLBuilderFactory builderFactory) {
        builderFactory
                .from(RoleTableController.class)
                .record(method -> method.list(new Query()))
                .rel("roles")
                .buildSecured(container::addLink);
    }


}
