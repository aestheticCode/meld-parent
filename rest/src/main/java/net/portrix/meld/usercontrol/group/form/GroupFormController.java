package net.portrix.meld.usercontrol.group.form;

import com.google.common.collect.Sets;
import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilder;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.meld.usercontrol.Group;
import net.portrix.meld.usercontrol.Identity;
import net.portrix.meld.usercontrol.Role;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Patrick Bittner on 05/10/16.
 */
@Path("usercontrol")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Name("User Control")
public class GroupFormController {

    private final GroupFormService service;

    private final URLBuilderFactory builderFactory;

    @Inject
    public GroupFormController(GroupFormService service, URLBuilderFactory builderFactory) {
        this.service = service;
        this.builderFactory = builderFactory;
    }

    public GroupFormController() {
        this(null, null);
    }

    @Secured
    @GET
    @Path("group/create/form")
    @Name("Group Form Create")
    @Transactional
    public GroupForm create() {
        GroupForm form = new GroupForm();

        form.setId(UUID.randomUUID());

        linkSave(builderFactory)
                .buildSecured(form::addLink);

        return form;
    }

    @Secured
    @GET
    @Path("group/{id}/form")
    @Name("Group Form Read")
    @Transactional
    public GroupForm read(@PathParam("id") final UUID id) {
        final Group group = service.findGroup(id);
        GroupForm form = new GroupForm();
        form.setId(group.getId());
        form.setName(group.getName());

        List<UUID> roleIds = new ArrayList<>();
        final List<Role> roles = service.findRoles(group);

        for (Role role : roles) {
            roleIds.add(role.getId());
        }

        form.setRoles(Sets.newHashSet(roleIds));

        List<UUID> memberIds = new ArrayList<>();
        for (Identity identity : group.getMembers()) {
            memberIds.add(identity.getId());
        }

        form.setMembers(Sets.newHashSet(memberIds));

        linkUpdate(group, builderFactory)
                .buildSecured(form::addLink);
        linkDelete(group, builderFactory)
                .buildSecured(form::addLink);

        return form;
    }

    @Secured
    @PUT
    @Path("group/{id}/form")
    @Name("Group Form Update")
    @Transactional
    public GroupForm update(@PathParam("id") final UUID id,final GroupForm groupForm) {
        final Group group = service.findGroup(id);
        group.setName(groupForm.getName());

        final List<Role> roles = service.findRoles();

        for (Role role : roles) {
            if (groupForm.getRoles().contains(role.getId())) {
                role.addScope(group);
            } else {
                role.removeScope(group);
            }
        }

        return read(group.getId());
    }

    @Secured
    @POST
    @Path("group/form")
    @Name("Group Form Save")
    @Transactional
    public GroupForm save(final GroupForm groupForm) {
        Group group = new Group();
        group.setName(groupForm.getName());
        service.save(group);

        final List<Role> roles = service.findRoles(group);

        for (Role role : roles) {
            if (groupForm.getRoles().contains(role.getId())) {
                role.addScope(group);
            } else {
                role.removeScope(group);
            }
        }

        return read(group.getId());
    }

    @Secured
    @DELETE
    @Path("group/{id}/form")
    @Name("Group Form Delete")
    @Transactional
    public void delete(@PathParam("id") UUID id) {
        final Group group = service.findGroup(id);
        service.delete(group);
    }


    @Secured
    @POST
    @Path("group/form/validate")
    @Transactional
    @Name("Group Form Name Validate")
    public boolean validate(GroupNameValidation validation) {
        return service.validateName(validation);
    }

    public static URLBuilder<GroupFormController> linkCreate(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(GroupFormController.class)
                .record(GroupFormController::create)
                .rel("create");
    }

    public static URLBuilder<GroupFormController> linkUpdate(Group group, URLBuilderFactory builderFactory) {
        return builderFactory
                .from(GroupFormController.class)
                .record(method -> method.update(group.getId(), new GroupForm()))
                .rel("update");
    }

    public static URLBuilder<GroupFormController> linkRead(Group group, URLBuilderFactory builderFactory) {
        return builderFactory
                .from(GroupFormController.class)
                .record(method -> method.read(group.getId()))
                .rel("read");
    }

    public static URLBuilder<GroupFormController> linkDelete(Group group, URLBuilderFactory builderFactory) {
        return builderFactory
                .from(GroupFormController.class)
                .record(method -> method.delete(group.getId()))
                .rel("delete");
    }


    public static URLBuilder<GroupFormController> linkSave(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(GroupFormController.class)
                .record(method -> method.save(null))
                .rel("save");

    }
}
