package net.portrix.meld.media.photos.grid;

import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilder;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.api.Blob;
import net.portrix.generic.rest.api.Container;
import net.portrix.generic.rest.api.query.Query;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.meld.media.photos.Photo;

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

@Path("media/photos")
@ApplicationScoped
@Name("Photos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PhotoGridController {

    private final PhotoGridService service;

    @Inject
    public PhotoGridController(PhotoGridService service) {
        this.service = service;
    }

    public PhotoGridController() {
        this(null);
    }

    @POST
    @Path("grid")
    @Name("Photos Read")
    @Secured
    @Transactional
    public Container<PhotoItem> list(Query query) {

        List<Photo> photos = service.findAll(query);
        long count = service.count(query);

        List<PhotoItem> items = photos.stream()
                .map((photo) -> {
                    PhotoItem item = new PhotoItem();

                    Blob blob = new Blob();
                    blob.setLastModified(photo.getLastModified());
                    blob.setData(photo.getThumbnail());
                    blob.setName(photo.getFileName());

                    item.setImage(blob);
                    item.setId(photo.getId());

                    return item;
                })
                .collect(Collectors.toList());

        return new Container<>(items, (int)count);
    }

    public static URLBuilder<PhotoGridController> linkList(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(PhotoGridController.class)
                .record((method) -> method.list(null))
                .rel("photos");
    }

}
