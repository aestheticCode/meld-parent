package net.portrix.meld.usercontrol.role.form;

import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilder;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.meld.usercontrol.Identity;
import net.portrix.meld.usercontrol.Permission;
import net.portrix.meld.usercontrol.Role;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author Patrick Bittner on 04/10/16.
 */
@Path("usercontrol")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Name("User Control")
public class RoleFormController {

    private final RoleFormService service;

    private final URLBuilderFactory builderFactory;

    @Inject
    public RoleFormController(RoleFormService service, URLBuilderFactory builderFactory) {
        this.service = service;
        this.builderFactory = builderFactory;
    }

    public RoleFormController() {
        this(null, null);
    }

    @GET
    @Path("role/{id}/form")
    @Name("Role Form Read")
    @Secured
    @Transactional
    public RoleForm read(@PathParam("id") UUID id) {
        final Role role = service.findRole(id);

        RoleForm roleForm = new RoleForm();
        roleForm.setId(role.getId());
        roleForm.setName(role.getName());

        Set<Permission> permissions = role.getPermissions();

        Set<UUID> permissionIds = new HashSet<>();
        for (Permission permission : permissions) {
            permissionIds.add(permission.getId());
        }
        roleForm.setPermissions(permissionIds);

        Set<UUID> members = new HashSet<>();
        for (Identity identity : role.getScopes()) {
            members.add(identity.getId());
        }
        roleForm.setMembers(members);

        linkRead(role, builderFactory)
                .buildSecured(roleForm::addLink);
        linkUpdate(role, builderFactory)
                .buildSecured(roleForm::addLink);
        linkDelete(role, builderFactory)
                .buildSecured(roleForm::addLink);

        return roleForm;
    }

    @PUT
    @Path("role/{id}/form")
    @Name("Role Form Update")
    @Secured
    @Transactional
    public RoleForm update(@PathParam("id") UUID id, RoleForm edit) {
        Role role = service.findRole(id);
        role.setName(edit.getName());

        List<Permission> permissions = service.findAllPermissions();

        for (Permission permission : permissions) {
            if (edit.getPermissions().contains(permission.getId())) {
                role.addPermission(permission);
            } else {
                role.removePermission(permission);
            }
        }

        return read(role.getId());
    }


    @POST
    @Path("role/form")
    @Name("Role Form Save")
    @Secured
    @Transactional
    public RoleForm save(final RoleForm form) {
        Role role = new Role();
        role.setName(form.getName());

        List<Permission> permissions = service.findAllPermissions();

        for (Permission permission : permissions) {
            if (form.getPermissions().contains(permission.getId())) {
                role.addPermission(permission);
            }
        }

        service.saveRole(role);

        return read(role.getId());
    }

    @DELETE
    @Path("role/{id}/form")
    @Name("Role Form Delete")
    @Secured
    public Response delete(@PathParam("id") UUID id) {
        final Role role = service.findRole(id);
        service.deleteRole(role);
        return Response.ok().build();
    }


    @Secured
    @POST
    @Path("role/form/validate")
    @Transactional
    @Name("Role Form Name Validate")
    public boolean validate(RoleNameValidation validation) {
        return service.validateName(validation);
    }


    public static URLBuilder<RoleFormController> linkRead(Role role, URLBuilderFactory builderFactory) {
        return builderFactory
                .from(RoleFormController.class)
                .record(method -> method.read(role.getId()))
                .rel("read");
    }

    public static URLBuilder<RoleFormController> linkUpdate(Role role, URLBuilderFactory builderFactory) {
        return builderFactory
                .from(RoleFormController.class)
                .record(method -> method.update(role.getId(), null))
                .rel("update");
    }

    public static URLBuilder<RoleFormController> linkSave(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(RoleFormController.class)
                .record(method -> method.save(null))
                .rel("save");
    }

    public static URLBuilder<RoleFormController> linkDelete(Role role, URLBuilderFactory builderFactory) {
        return builderFactory
                .from(RoleFormController.class)
                .record(method -> method.delete(role.getId()))
                .rel("delete");
    }


}
