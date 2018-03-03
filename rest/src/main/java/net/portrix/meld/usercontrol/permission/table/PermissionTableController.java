package net.portrix.meld.usercontrol.permission.table;

import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilder;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.api.Container;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.meld.usercontrol.Permission;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrick Bittner on 01/10/16.
 */
@Path("usercontrol")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Name("User Control")
public class PermissionTableController {

    private final PermissionTableService service;

    @Inject
    public PermissionTableController(PermissionTableService service) {
        this.service = service;
    }

    public PermissionTableController() {
        this(null);
    }

    @GET
    @Path("permission/table")
    @Name("Permission Table")
    @Secured
    public Container<PermissionItem> list(@BeanParam PermissionSearch search) {

        List<Permission> permissions;
        long count = 0;
        if (search.getLimit() == 0) {
            permissions = new ArrayList<>();
        } else {
            permissions = service.find(search);
            count = service.count(search);
        }

        final List<PermissionItem> selects = new ArrayList<>();

        for (Permission permission : permissions) {
            PermissionItem response = new PermissionItem();

            response.setId(permission.getId());
            response.setName(permission.getName());
            response.setMethod(permission.getMethod());
            response.setPath(permission.getPath());
            response.setCreated(permission.getCreated());

            selects.add(response);
        }

        return new Container<>(selects, (int) count);

    }

    public static URLBuilder<PermissionTableController> linkRoles(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(PermissionTableController.class)
                .record(method -> method.list(null))
                .rel("permissions");
    }

}
