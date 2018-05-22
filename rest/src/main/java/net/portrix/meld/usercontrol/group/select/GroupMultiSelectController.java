package net.portrix.meld.usercontrol.group.select;

import com.google.common.collect.Sets;
import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilder;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.api.Container;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.meld.usercontrol.Group;
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
public class GroupMultiSelectController {

    private final URLBuilderFactory builderFactory;

    private final GroupMultiSelectService service;

    @Inject
    public GroupMultiSelectController(URLBuilderFactory builderFactory, GroupMultiSelectService service) {
        this.builderFactory = builderFactory;
        this.service = service;
    }

    public GroupMultiSelectController() {
        this(null, null);
    }

    @GET
    @Path("group/multiselect")
    @Name("Group MultiSelect")
    @Secured
    @Transactional
    public Container<GroupSelect> list(@BeanParam GroupSearch search) {
        List<Group> groups;
        long count = 0;
        if (search.getLimit() == 0) {
            groups = new ArrayList<>();
        } else {
            groups = service.find(search);
            count = service.count(search);
        }

        final List<GroupSelect> selects = new ArrayList<>();

        for (Group group : groups) {
            GroupSelect response = new GroupSelect(
                    group.getId(),
                    group.getName()
            );

            GroupFormController.linkRead(group, builderFactory)
                    .buildSecured(response::addLink);

            selects.add(response);
        }

        final Container<GroupSelect> container = new Container<>(selects, (int) count);

        GroupFormController.linkSave(builderFactory)
                .buildSecured(container::addLink);

        return container;
    }

    public static URLBuilder<GroupMultiSelectController> linkGroups(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(GroupMultiSelectController.class)
                .record(method -> method.list(null))
                .rel("roles");
    }

}
