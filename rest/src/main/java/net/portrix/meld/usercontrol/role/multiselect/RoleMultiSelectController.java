package net.portrix.meld.usercontrol.role.multiselect;

import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilder;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.api.Container;
import net.portrix.generic.rest.api.query.Query;
import net.portrix.generic.rest.jsr339.Name;
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
public class RoleMultiSelectController {

    private final RoleMultiSelectService service;

    private final URLBuilderFactory builderFactory;

    @Inject
    public RoleMultiSelectController(RoleMultiSelectService service, URLBuilderFactory builderFactory) {
        this.service = service;
        this.builderFactory = builderFactory;
    }

    public RoleMultiSelectController() {
        this(null, null);
    }

    @POST
    @Path("role/multiselect")
    @Name("Role MultiSelect")
    @Secured
    @Transactional
    public Container<RoleSelect> list(Query search) {
        List<Role> Roles;
        long count = 0;
        if (search.getLimit() == 0) {
            Roles = new ArrayList<>();
        } else {
            Roles = service.findRoles(search);
            count = service.countRoles(search);
        }


        final List<RoleSelect> selects = new ArrayList<>();

        for (Role role : Roles) {
            RoleSelect response = new RoleSelect();

            response.setId(role.getId());
            response.setName(role.getName());

            RoleFormController.linkRead(role, builderFactory)
                    .buildSecured(response::addLink);

            selects.add(response);
        }

        final Container<RoleSelect> container = new Container<>(selects, (int) count);

        RoleFormController.linkSave(builderFactory)
                .buildSecured(container::addLink);

        return container;

    }

    public static URLBuilder<RoleMultiSelectController> linkRoles(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(RoleMultiSelectController.class)
                .record(method -> method.list(new Query()))
                .rel("roles");
    }


}
