package net.portrix.meld.social.people.find.table;

import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilder;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.api.Blob;
import net.portrix.generic.rest.api.Container;
import net.portrix.generic.rest.api.query.Query;
import net.portrix.generic.rest.api.search.Search;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.meld.ApplicationController;
import net.portrix.meld.social.people.RelationShip;
import net.portrix.meld.social.profile.Profile;
import net.portrix.meld.usercontrol.User;
import net.portrix.meld.usercontrol.UserImage;
import net.portrix.meld.usercontrol.UserManager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
    public Container<FindItem> list(Search query) {
        List<User> users = service.find(query);
        long countUsers = service.count(query);
        User current = userManager.current();

        List<FindItem> categoryFormList = users
                .stream()
                .map((user) -> {
                    FindItem row = new FindItem();
                    row.setId(user.getId());
                    row.setName(user.getName());
                    row.setFirstName(user.getFirstName());
                    row.setLastName(user.getLastName());
                    Profile profile = service.findImage(user);
                    Blob blob = new Blob();
                    if (profile == null) {
                        blob = ApplicationController.createUserPhotoBlob();
                    } else {
                        blob.setName(profile.getUserPhoto().getFileName());
                        blob.setLastModified(profile.getUserPhoto().getLastModified());
                        blob.setData(profile.getUserPhoto().getThumbnail());
                    }
                    row.setImage(blob);

                    RelationShip relationShip = service.findRelationShip(current, user);

                    if (relationShip != null) {
                        row.setCategory(relationShip.getCategory().getId());
                    }
                    return row;
                })
                .collect(Collectors.toList());

        return new Container<>(categoryFormList, (int) countUsers);
    }

    public static URLBuilder<FindTableController> linkList(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(FindTableController.class)
                .record((method) -> method.list(null))
                .rel("list");
    }


}
