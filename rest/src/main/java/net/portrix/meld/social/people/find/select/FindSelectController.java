package net.portrix.meld.social.people.find.select;

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

@Path("social/people")
@ApplicationScoped
@Name("Social")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FindSelectController {

    private final UserManager userManager;

    private final FindSelectService service;

    @Inject
    public FindSelectController(UserManager userManager, FindSelectService service) {
        this.userManager = userManager;
        this.service = service;
    }

    public FindSelectController() {
        this(null, null);
    }

    @PUT
    @Path("find")
    @Name("Find User Read")
    @Secured
    @Transactional
    public FindSelect update(FindSelect form) {

        User current = userManager.current();
        User user = userManager.find(form.getId());

        RelationShip relationShip = service.findRelationShip(current, user);

        if (form.getCategory() == null) {
            service.remove(relationShip);
            return form;
        }

        Category category = service.findCategory(form.getCategory());

        if (category == null) {
            throw new NotFoundException();
        }

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

    public static URLBuilder<FindSelectController> linkUpdate(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(FindSelectController.class)
                .record((method) -> method.update(null))
                .rel("find");
    }

}
