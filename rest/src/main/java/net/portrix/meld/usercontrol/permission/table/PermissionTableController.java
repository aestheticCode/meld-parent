package net.portrix.meld.usercontrol.permission.table;

import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.api.Container;
import net.portrix.generic.rest.api.query.Query;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.meld.usercontrol.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
        this( null);
    }

    @POST
    @Path("permission/table")
    @Name("Permission Table")
    @Secured
    public Container<PermissionRowResponse> list(Query search) {

        List<Permission> permissions;
        long count = 0;
        if(search.getLimit() == 0) {
            permissions = new ArrayList<>();
        } else {
            permissions = service.find(search);
            count = service.count(search);
        }

        final List<PermissionRowResponse> selects = new ArrayList<>();

        for (Permission permission : permissions) {
            PermissionRowResponse response = new PermissionRowResponse();

            response.setId(permission.getId());
            response.setName(permission.getName());
            response.setMethod(permission.getMethod());
            response.setPath(permission.getPath());

            selects.add(response);
        }

        return new Container<>(selects, (int) count);

    }

}
