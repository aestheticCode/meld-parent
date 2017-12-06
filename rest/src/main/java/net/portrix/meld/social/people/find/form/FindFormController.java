package net.portrix.meld.social.people.find.form;

import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilder;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.meld.social.people.Category;
import net.portrix.meld.social.people.RelationShip;
import net.portrix.meld.usercontrol.User;
import net.portrix.meld.usercontrol.UserManager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@Path("social/people")
@ApplicationScoped
@Name("Social")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FindFormController {

    private final UserManager userManager;

    private final FindFormService service;

    @Inject
    public FindFormController(UserManager userManager, FindFormService service) {
        this.userManager = userManager;
        this.service = service;
    }

    public FindFormController() {
        this(null, null);
    }

    @PUT
    @Path("find/user/{id}")
    @Name("Find User Read")
    @Secured
    @Transactional
    public CategoryForm update(@PathParam("id") UUID id, CategoryForm categoryForm) {

        User current = userManager.current();
        User user = userManager.find(id);

        RelationShip relationShip = service.findRelationShip(current, user);

        if (categoryForm.getId() == null) {
            service.remove(relationShip);
            return categoryForm;
        }

        if (relationShip == null) {
            relationShip = new RelationShip();
        }

        Category category = service.findCategory(categoryForm.getId());

        relationShip.setFrom(current);
        relationShip.setTo(user);
        relationShip.setCategory(category);

        service.update(relationShip);

        return categoryForm;

    }

    public static URLBuilder<FindFormController> linkProfile(UUID id, URLBuilderFactory builderFactory) {
        return builderFactory
                .from(FindFormController.class)
                .record((method) -> method.update(id, null))
                .rel("find");
    }

}
