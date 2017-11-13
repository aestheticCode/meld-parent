package net.portrix.meld.usercontrol.group.multiselect;

import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.api.Container;
import net.portrix.generic.rest.api.query.Query;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.meld.usercontrol.group.form.GroupFormController;
import net.portrix.meld.usercontrol.Group;

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
        this(null,  null);
    }

    @POST
    @Path("group/multiselect")
    @Name("Group MultiSelect")
    @Secured
    @Transactional
    public Container<GroupSelectResponse> list(Query search) {
        List<Group> groups;
        long count = 0;
        if(search.getLimit() == 0) {
            groups = new ArrayList<>();
        } else {
            groups = service.find(search);
            count = service.count(search);
        }

        final List<GroupSelectResponse> selects = new ArrayList<>();

        for (Group group : groups) {
            GroupSelectResponse response = new GroupSelectResponse();

            response.setId(group.getId());
            response.setName(group.getName());

            GroupFormController.linkRead(group, response, builderFactory);

            selects.add(response);
        }

        final Container<GroupSelectResponse> container = new Container<>(selects, (int) count);

        GroupFormController.linkSave(container, builderFactory);

        return container;
    }

}
