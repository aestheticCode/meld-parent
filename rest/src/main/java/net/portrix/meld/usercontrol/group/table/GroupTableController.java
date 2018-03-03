package net.portrix.meld.usercontrol.group.table;

import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilder;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.api.Container;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.meld.usercontrol.Group;
import net.portrix.meld.usercontrol.Identity;
import net.portrix.meld.usercontrol.group.form.GroupFormController;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrick Bittner on 04/10/16.
 */
@Path("usercontrol")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Name("User Control")
public class GroupTableController {

    private final URLBuilderFactory builderFactory;

    private final GroupTableService service;

    @Inject
    public GroupTableController(URLBuilderFactory builderFactory, GroupTableService service) {
        this.builderFactory = builderFactory;
        this.service = service;
    }

    public GroupTableController() {
        this(null, null);
    }

    @GET
    @Path("group/table")
    @Name("Group Table")
    @Secured
    @Transactional
    public Container<GroupItem> list(@BeanParam GroupSearch search) {
        List<Group> groups;
        long count = 0;
        if (search.getLimit() == 0) {
            groups = new ArrayList<>();
        } else {
            groups = service.find(search);
            count = service.count(search);
        }


        final List<GroupItem> selects = new ArrayList<>();

        for (Group group : groups) {
            GroupItem response = new GroupItem();

            response.setId(group.getId());
            response.setName(group.getName());

            for (Identity identity : group.getMembers()) {
                response.getRoles().add(identity.getName());
            }

            GroupFormController.linkRead(group, builderFactory)
                    .buildSecured(response::addLink);

            selects.add(response);
        }

        final Container<GroupItem> container = new Container<>(selects, (int) count);

        GroupFormController.linkSave(builderFactory)
                .buildSecured(container::addLink);

        return container;
    }


    public static URLBuilder<GroupTableController> linkGroups(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(GroupTableController.class)
                .record(method -> method.list(null))
                .rel("groups");
    }
}
