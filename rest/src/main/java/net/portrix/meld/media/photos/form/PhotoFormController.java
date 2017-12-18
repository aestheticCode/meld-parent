package net.portrix.meld.media.photos.form;

import net.portrix.generic.image.ImageUtils;
import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilder;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.api.Blob;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.meld.media.photos.Photo;
import net.portrix.meld.usercontrol.UserManager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Set;
import java.util.UUID;

@Path("media")
@ApplicationScoped
@Name("Photos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PhotoFormController {

    private final PhotoFormService service;

    private final UserManager userManager;

    @Inject
    public PhotoFormController(PhotoFormService service, UserManager userManager) {
        this.service = service;
        this.userManager = userManager;
    }

    public PhotoFormController() {
        this(null, null);
    }

    @GET
    @Path("photo/create")
    @Name("Photo Read")
    @Secured
    @Transactional
    public PhotoForm create() {
        return new PhotoForm();
    }


    @GET
    @Path("photo/{id}")
    @Name("Photo Read")
    @Secured
    @Transactional
    public PhotoForm read(@PathParam("id") final UUID id) {

        final Photo photo = service.find(id);

        PhotoForm form = new PhotoForm();
        form.setId(photo.getId());

        Blob blob = new Blob();
        blob.setName(photo.getFileName());
        blob.setData(photo.getImage());
        blob.setLastModified(photo.getLastModified());

        form.setImage(blob);

        return form;
    }

    @PUT
    @Path("photo/{id}")
    @Name("Photo Update")
    @Secured
    @Transactional
    public PhotoForm update(@PathParam("id") final UUID id,final PhotoForm form) {

        final Photo photo = service.find(id);
        photo.setUser(userManager.current());

        photo.setFileName(form.getImage().getName());
        photo.setImage(form.getImage().getData());
        photo.setLastModified(form.getImage().getLastModified());
        photo.setThumbnail(ImageUtils.thumnail(photo.getFileName(), photo.getImage(), 200));

        return read(photo.getId());
    }

    @POST
    @Path("photo")
    @Name("Photo Save")
    @Secured
    @Transactional
    public PhotoForm save(final PhotoForm form) {

        Photo photo = new Photo();
        photo.setUser(userManager.current());

        photo.setFileName(form.getImage().getName());
        photo.setImage(form.getImage().getData());
        photo.setLastModified(form.getImage().getLastModified());

        photo.setThumbnail(ImageUtils.thumnail(photo.getFileName(), photo.getImage(), 200));

        service.save(photo);

        return read(photo.getId());
    }

    @DELETE
    @Path("photo")
    @Name("Photo Delete")
    @Secured
    @Transactional
    public void delete(@QueryParam("id") final Set<UUID> ids) {
        for (UUID id : ids) {
            final Photo photo = service.find(id);
            service.remove(photo);
        }
    }

    public static URLBuilder<PhotoFormController> linkCreate(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(PhotoFormController.class)
                .record(PhotoFormController::create)
                .rel("create");
    }

    public static URLBuilder<PhotoFormController> linkRead(UUID id, URLBuilderFactory builderFactory) {
        return builderFactory
                .from(PhotoFormController.class)
                .record((method) -> method.read(id))
                .rel("read");
    }

    public static URLBuilder<PhotoFormController> linkUpdate(UUID id, URLBuilderFactory builderFactory) {
        return builderFactory
                .from(PhotoFormController.class)
                .record((method) -> method.update(id, null))
                .rel("update");
    }

    public static URLBuilder<PhotoFormController> linkSave(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(PhotoFormController.class)
                .record((method) -> method.save(null))
                .rel("save");
    }



}
