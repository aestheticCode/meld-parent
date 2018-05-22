package net.portrix.meld.social.communities.find.table;

import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.api.Blob;
import net.portrix.generic.rest.api.Container;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.meld.social.communities.Community;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("social")
@ApplicationScoped
@Name("Social")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CommunitiesFindController {

    private final CommunitiesService service;

    public CommunitiesFindController(CommunitiesService service) {
        this.service = service;
    }

    public CommunitiesFindController() {
        this(null);
    }

    @GET
    @Transactional
    @Secured
    @Path("communities/grid")
    @Name("Communities Grid")
    public Container<CommunitityItem> find(@BeanParam CommunitySearch search) {

        List<Community> communities = service.find(search);
        long count = service.count(search);

        List<CommunitityItem> items = communities.stream()
                .map(community -> {
                    Blob blob = new Blob();

                    blob.setData(community.getThumbnail());
                    blob.setLastModified(community.getLastModified());
                    blob.setName(community.getFileName());

                    return  new CommunitityItem(
                            community.getId(),
                            blob,
                            community.getName()
                    );
                })
                .collect(Collectors.toList());

        return new Container<>(items, (int) count);
    }

}
