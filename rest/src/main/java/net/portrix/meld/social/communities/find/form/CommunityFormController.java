package net.portrix.meld.social.communities.find.form;

import com.google.common.collect.Sets;
import net.portrix.generic.ddd.FormController;
import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.jsr339.Name;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@Path("social/community")
@ApplicationScoped
@Name("Social")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CommunityFormController implements FormController<CommunityForm> {

    private final CommunityService service;

    private final URLBuilderFactory factory;

    @Inject
    public CommunityFormController(CommunityService service, URLBuilderFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    public CommunityFormController() {
        this(null, null);
    }

    @GET
    @Path("create")
    @Transactional
    @Secured
    public CommunityForm create() {
        return new CommunityForm(
                null,
                null,
                null
        );
    }

    @GET
    @Path("{id}")
    public CommunityForm read(@PathParam("id") UUID id) {
        return new CommunityForm(
                null,
                null,
                null
        );
    }

    @Override
    public CommunityForm save(CommunityForm form) {
        return new CommunityForm(
                null,
                null,
                null
        );
    }

    @Override
    public CommunityForm update(UUID id, CommunityForm form) {
        return new CommunityForm(
                null,
                null,
                null
        );    }

    @Override
    public void delete(UUID id) {

    }

}
