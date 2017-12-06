package net.portrix.meld.social.people.find.table;

import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilder;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.api.Blob;
import net.portrix.generic.rest.api.Container;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.meld.social.people.RelationShip;
import net.portrix.meld.social.people.find.table.query.Query;
import net.portrix.meld.usercontrol.User;
import net.portrix.meld.usercontrol.UserImage;
import net.portrix.meld.usercontrol.UserManager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("social/people")
@ApplicationScoped
@Name("Social")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FindTableController {

    private final FindTableService service;

    private final UserManager userManager;

    @Inject
    public FindTableController(FindTableService service, UserManager userManager) {
        this.service = service;
        this.userManager = userManager;
    }

    public FindTableController() {
        this(null, null);
    }

    @POST
    @Path("find")
    @Name("Find User Read")
    @Secured
    @Transactional
    public Container<FindTableRow> list(Query query) {
        List<User> users = service.findUsers(query);
        long countUsers = service.countUsers(query);
        User current = userManager.current();

        List<FindTableRow> categoryFormList = users
                .stream()
                .map((user) -> {
                   FindTableRow row = new FindTableRow();
                   row.setId(user.getId());
                   row.setName(user.getName());
                   row.setFirstName(user.getFirstName());
                   row.setLastName(user.getLastName());
                    UserImage image = service.findImage(user);
                    Blob blob = new Blob();
                    blob.setName(image.getFileName());
                    blob.setLastModified(image.getLastModified());
                    blob.setData(image.getThumbnail());
                    row.setImage(blob);

                    RelationShip relationShip = service.findRelationShip(current, user);

                    if (relationShip != null) {
                        row.setCategory(relationShip.getCategory().getId());
                    }
                   return row;
                })
                .collect(Collectors.toList());

        return new Container<>(categoryFormList, (int)countUsers);
    }

    public static URLBuilder<FindTableController> linkList(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(FindTableController.class)
                .record((method) -> method.list(null))
                .rel("list");
    }


}
