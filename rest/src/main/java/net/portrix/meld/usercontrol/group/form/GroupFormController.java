package net.portrix.meld.usercontrol.group.form;

import com.google.common.collect.Sets;
import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.api.LinksContainer;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.meld.usercontrol.*;

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
    @Path("group/{id}/form")
    @Name("Group Form Read")
    @Transactional
    public GroupForm read(@PathParam("id") UUID id) {
        final Group group = service.findGroup(id);
        GroupForm groupType = new GroupForm();
        groupType.setId(group.getId());
        groupType.setName(group.getName());

        List<UUID> roleIds = new ArrayList<>();
        final List<Role> roles = service.findRoles();

        for (Role role : roles) {
            roleIds.add(role.getId());
        }

        groupType.setRoles(Sets.newHashSet(roleIds));

        List<UUID> memberIds = new ArrayList<>();
        for (Identity identity : group.getMembers()) {
            memberIds.add(identity.getId());
        }

        groupType.setMembers(Sets.newHashSet(memberIds));

        linkRead(group, groupType, builderFactory);
        linkUpdate(group, groupType, builderFactory);
        linkDelete(group, groupType, builderFactory);

        return groupType;
    }

    @Secured
    @PUT
    @Path("group/{id}/form")
    @Name("Group Form Update")
    @Transactional
    public GroupForm update(@PathParam("id") UUID id, GroupForm groupForm) {
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
    public GroupForm save(GroupForm groupForm) {
        Group group = new Group();
        group.setName(groupForm.getName());
        service.save(group);

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
    @DELETE
    @Path("group/{id}/form")
    @Name("Group Form Delete")
    @Transactional
    public Response delete(@PathParam("id") UUID id) {
        final Group group = service.findGroup(id);
        service.delete(group);
        return Response.ok().build();
    }


    @Secured
    @POST
    @Path("group/form/validate")
    @Transactional
    @Name("Group Form Name Validate")
    public boolean validate(GroupNameValidation validation) {
        return service.validateName(validation);
    }


    public static void linkUpdate(Group group, LinksContainer container, URLBuilderFactory builderFactory) {
        builderFactory
                .from(GroupFormController.class)
                .record(method -> method.update(group.getId(), new GroupForm()))
                .rel("update")
                .buildSecured(container::addLink);
    }

    public static void linkRead(Group group, LinksContainer container, URLBuilderFactory builderFactory) {
        builderFactory
                .from(GroupFormController.class)
                .record(method -> method.read(group.getId()))
                .rel("read")
                .buildSecured(container::addLink);
    }

    public static void linkDelete(Group group, LinksContainer container, URLBuilderFactory builderFactory) {
        builderFactory
                .from(GroupFormController.class)
                .record(method -> method.delete(group.getId()))
                .rel("delete")
                .buildSecured(container::addLink);
    }


    public static void linkSave(LinksContainer container, URLBuilderFactory builderFactory) {
        builderFactory
                .from(GroupFormController.class)
                .record(method -> method.save(null))
                .rel("save")
                .buildSecured(container::addLink);

    }
}
