package net.portrix.meld.social.people;


import net.portrix.generic.rest.URLBuilder;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.meld.social.people.category.form.CategoryFormController;
import net.portrix.meld.social.people.category.table.CategoryTableController;
import net.portrix.meld.social.people.find.table.FindTableController;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("social/people")
@ApplicationScoped
@Name("Social")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PeopleController {

    private final URLBuilderFactory factory;

    @Inject
    public PeopleController(URLBuilderFactory factory) {
        this.factory = factory;
    }

    public PeopleController() {
        this(null);
    }

    @GET
    @Path("/")
    public PeopleResponse people() {

        PeopleResponse response = new PeopleResponse();

        FindTableController.linkList(factory)
                .buildSecured(response::addLink);

        CategoryTableController.linkProfile(factory)
                .buildSecured(response::addLink);

        CategoryFormController.linkCreate(factory)
                .buildSecured(response::addLink);

        return response;
    }

    public static URLBuilder<PeopleController> linkPeople(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(PeopleController.class)
                .record(PeopleController::people)
                .rel("people");
    }

}
