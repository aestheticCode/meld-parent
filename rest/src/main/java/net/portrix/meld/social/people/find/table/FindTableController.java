package net.portrix.meld.social.people.find.table;

import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilder;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.api.Container;
import net.portrix.generic.rest.api.search.Search;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.meld.media.photos.form.PhotoFormController;
import net.portrix.meld.social.people.Category;
import net.portrix.meld.social.people.RelationShip;
import net.portrix.meld.social.profile.Profile;
import net.portrix.meld.usercontrol.User;
import net.portrix.meld.usercontrol.UserManager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.net.URI;
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

    private final URLBuilderFactory factory;

    @Inject
    public FindTableController(FindTableService service, UserManager userManager, URLBuilderFactory factory) {
        this.service = service;
        this.userManager = userManager;
        this.factory = factory;
    }

    public FindTableController() {
        this(null, null, null);
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
                    Profile profile = service.findProfile(user);

                    if (profile != null) {
                        URI uri = PhotoFormController.linkThumbnail(profile.getUserPhoto(), factory)
                                .generateUri();
                        row.setImage(uri);
                    }

                    RelationShip relationShip = service.findRelationShip(current, user);

                    if (relationShip != null) {
                        CategorySelect category = new CategorySelect();
                        category.setId(relationShip.getCategory().getId());
                        category.setName(relationShip.getCategory().getName());
                        row.setCategory(category);
                    }
                    return row;
                })
                .collect(Collectors.toList());

        return new Container<>(categoryFormList, (int) countUsers);
    }

    @PUT
    @Path("find")
    @Name("Find User Read")
    @Secured
    @Transactional
    public FindItem update(FindItem form) {

        User current = userManager.current();
        User user = userManager.find(form.getId());

        RelationShip relationShip = service.findRelationShip(current, user);

        if (form.getCategory() == null) {
            service.remove(relationShip);
            return form;
        }

        Category category = service.findCategory(form.getCategory().getId());

        if (relationShip == null) {
            relationShip = new RelationShip();
            relationShip.setFrom(current);
            relationShip.setTo(user);
            relationShip.setCategory(category);

            service.save(relationShip);
        } else {
            relationShip.setFrom(current);
            relationShip.setTo(user);
            relationShip.setCategory(category);
        }

        return form;

    }


    public static URLBuilder<FindTableController> linkList(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(FindTableController.class)
                .record((method) -> method.list(null))
                .rel("list");
    }


}
