package net.portrix.meld.usercontrol.role.table;

import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilder;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.api.Container;
import net.portrix.generic.rest.api.query.Query;
import net.portrix.generic.rest.api.search.Search;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.meld.usercontrol.Identity;
import net.portrix.meld.usercontrol.Role;
import net.portrix.meld.usercontrol.role.form.RoleFormController;

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
    public Container<RoleItem> list(Search search) {
        List<Role> Roles;
        long count = 0;
        if (search.getLimit() == 0) {
            Roles = new ArrayList<>();
        } else {
            Roles = service.find(search);
            count = service.count(search);
        }

        final List<RoleItem> selects = new ArrayList<>();

        for (Role role : Roles) {
            RoleItem response = new RoleItem();

            response.setId(role.getId());
            response.setName(role.getName());

            for (Identity identity : role.getScopes()) {
                response.getMembers().add(identity.getName());
            }

            RoleFormController.linkRead(role, builderFactory)
                    .buildSecured(response::addLink);

            selects.add(response);
        }

        final Container<RoleItem> container = new Container<>(selects, (int) count);

        RoleFormController.linkSave(builderFactory)
                .buildSecured(container::addLink);

        return container;

    }

    public static URLBuilder<RoleTableController> linkRoles(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(RoleTableController.class)
                .record(method -> method.list(null))
                .rel("roles");
    }


}
