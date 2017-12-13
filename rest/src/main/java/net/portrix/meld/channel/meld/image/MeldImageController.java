package net.portrix.meld.channel.meld.image;

import net.portrix.generic.image.ImageUtils;
import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.api.LinksContainer;
import net.portrix.generic.rest.api.UploadMeta;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.meld.channel.MeldImagePost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Patrick Bittner on 07/10/16.
 */
@Path("channel")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Name("Channel")
public class MeldImageController {


    private static final Logger log = LoggerFactory.getLogger(MeldImageController.class);

    private final MeldImageService imageService;

    private final MeldImageSession imageSession;


    @Inject
    public MeldImageController(MeldImageService imageService, MeldImageSession imageSession) {
        this.imageService = imageService;
        this.imageSession = imageSession;
    }

    public MeldImageController() {
        this(null, null);
    }


    @Secured
    @GET
    @Path("meld/{id}/image")
    @Name("Meld Image")
    @Produces("img/jpeg")
    @Transactional
    public byte[] image(@PathParam("id") UUID id) {
        final MeldImagePost post = imageService.find(id);
        return post.getImage().getImage();
    }

    @Secured
    @GET
    @Path("meld/{id}/thumbnail")
    @Name("Meld Thumbnail")
    @Produces("img/jpeg")
    @Transactional
    public byte[] thumbNail(@PathParam("id") UUID id) {
        final MeldImagePost post = imageService.find(id);
        return post.getImage().getThumbnail();
    }

    @Secured
    @POST
    @Path("meld/upload/image")
    @Name("Meld Image Upload")
    @Consumes("application/octet-stream")
    @Produces("application/json")
    @Transactional
    public Response upload(@HeaderParam("Content-Disposition") String content, byte[] data) {
        UploadMeta meta = UploadMeta.read(content);

        if (meta.getChunk() == 0) {
            int capacity = meta.getSize() / 1024 + 1;

            imageSession.initialize(capacity);
            imageSession.setFileName(meta.getFileName());
        }

        imageSession.set(meta.getChunk(), Optional.of(data));

        boolean completed = imageSession.isCompleted();

        if (completed) {
            imageSession.fillArray(meta);

            return Response.ok().build();
        } else {
            return Response.status(Response.Status.PARTIAL_CONTENT).build();
        }

    }


    @Secured
    @GET
    @Path("meld/upload/image")
    @Name("Meld Image")
    @Produces("img/jpeg")
    @Transactional
    public Response uploadedImage() {
        Response.ResponseBuilder response = Response.ok(imageSession.getFileContent());
        CacheControl control = new CacheControl();
        control.setMustRevalidate(true);
        control.setMaxAge(0);
        control.setNoCache(true);
        control.setNoStore(true);
        response.cacheControl(control);
        return response.build();
    }

    @Secured
    @GET
    @Path("meld/upload/thumbnail")
    @Name("Meld Thumbnail")
    @Produces("img/jpeg")
    @Transactional
    public Response uploadedThumbNail() throws IOException {
        Response.ResponseBuilder response = Response.ok( ImageUtils.thumnail(imageSession.getFileName(), imageSession.getFileContent(), 100));
        CacheControl control = new CacheControl();
        control.setMustRevalidate(true);
        control.setMaxAge(0);
        control.setNoCache(true);
        control.setNoStore(true);
        response.cacheControl(control);
        return response.build();
    }

    @Secured
    @DELETE
    @Path("meld/upload/image")
    public Response deleteUploaded() {
        imageSession.clear();
        return Response.ok().build();
    }


    public static void linkImage(MeldImagePost post, LinksContainer container, URLBuilderFactory builderFactory) {
        builderFactory
                .from(MeldImageController.class)
                .record(method -> method.image(post.getId()))
                .rel("image")
                .buildSecured(container::addLink);
    }

    public static void linkThumbnail(MeldImagePost post, LinksContainer container, URLBuilderFactory builderFactory) {
        builderFactory
                .from(MeldImageController.class)
                .record(method -> method.thumbNail(post.getId()))
                .rel("thumbnail")
                .buildSecured(container::addLink);
    }

    public static void linkUploadedImage(LinksContainer container, URLBuilderFactory builderFactory) {
        builderFactory
                .from(MeldImageController.class)
                .record(MeldImageController::uploadedImage)
                .rel("uploadedImage")
                .buildSecured(container::addLink);
    }
}
