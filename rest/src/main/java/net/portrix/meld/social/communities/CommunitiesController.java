package net.portrix.meld.social.communities;

import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilder;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.jsr339.Name;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("social")
@ApplicationScoped
@Name("Social")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CommunitiesController {

    private final URLBuilderFactory factory;

    @Inject
    public CommunitiesController(URLBuilderFactory factory) {
        this.factory = factory;
    }

    public CommunitiesController() {
        this(null);
    }

    @GET
    @Path("communities")
    @Secured
    public CommunitiesResponse communitities() {

        CommunitiesResponse response = new CommunitiesResponse();

        return response;
    }

    public static URLBuilder<CommunitiesController> linkCommunities(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(CommunitiesController.class)
                .record(CommunitiesController::communitities)
                .rel("communities");
    }

}
