package net.portrix.meld.media.photos.form;

import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.api.Blob;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.meld.media.photos.Photo;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@Path("media/photos")
@ApplicationScoped
@Name("Photos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PhotoFormController {

    private final PhotoFormService service;

    @Inject
    public PhotoFormController(PhotoFormService service) {
        this.service = service;
    }

    public PhotoFormController() {
        this(null);
    }

    @GET
    @Path("read/{id}")
    @Name("Photo Read")
    @Secured
    @Transactional
    public PhotoForm read(@PathParam("id") UUID id) {

        Photo photo = service.find(id);

        PhotoForm form = new PhotoForm();
        form.setId(photo.getId());

        Blob blob = new Blob();
        blob.setName(photo.getFileName());
        blob.setData(photo.getImage());
        blob.setLastModified(photo.getLastModified());

        form.setImage(blob);

        return form;
    }

}
