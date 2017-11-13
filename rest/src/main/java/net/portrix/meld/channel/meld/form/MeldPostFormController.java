package net.portrix.meld.channel.meld.form;

import net.portrix.generic.image.ImageUtils;
import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.api.LinksContainer;
import net.portrix.generic.rest.api.Blob;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.meld.channel.MeldImage;
import net.portrix.meld.channel.MeldPost;
import net.portrix.meld.channel.MeldPostService;
import net.portrix.meld.channel.meld.image.MeldImageSession;
import net.portrix.meld.usercontrol.User;
import net.portrix.meld.usercontrol.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.Instant;
import java.util.UUID;

/**
 * @author Patrick Bittner on 06/10/16.
 */
@Path("channel")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Name("Channel")
public class MeldPostFormController {

    private static final Logger log = LoggerFactory.getLogger(MeldPostFormController.class);

    private final MeldPostFormService service;

    private final URLBuilderFactory builderFactory;

    @Inject
    public MeldPostFormController(MeldPostFormService service, URLBuilderFactory builderFactory) {
        this.service = service;
        this.builderFactory = builderFactory;
    }

    public MeldPostFormController() {
        this(null, null);
    }

    @Secured
    @GET
    @Path("meld/{id}")
    @Name("Meld Post Read")
    @Transactional
    public MeldPostResponse read(@PathParam("id") UUID id) {
        final MeldPost post = service.findPost(id);
        MeldPostResponse response = new MeldPostResponse();
        response.setId(post.getId());
        response.setText(post.getText());
        final Blob file = new Blob();
        final MeldImage image = post.getImage();

        if (post.hasImage()) {
            file.setName(image.getFileName());
            file.setData(image.getImage());
            response.setFile(file);
        }

        linkUpdate(post, response, builderFactory);

        return response;
    }

    @Secured
    @PUT
    @Path("meld/{id}")
    @Name("Meld Post Update")
    @Transactional
    public MeldPostResponse update(@PathParam("id") UUID id, MeldPostForm edit) {
        final MeldPost post = service.findPost(id);
        final User user = service.currentUser();
        post.setUser(user);
        post.setText(edit.getText());

        if (post.hasImage()) {
            final MeldImage image = post.getImage();

            final Blob file = edit.getFile();
            if (file != null) {
                image.setFileName(file.getName());
                image.setImage(file.getData());
                image.setThumbnail(ImageUtils.thumnail(file.getName(), file.getData()));
            } else {

            }
        }



        return read(post.getId());
    }

    @Secured
    @POST
    @Path("meld")
    @Name("Meld Post Save")
    @Transactional
    public MeldPostResponse save(MeldPostForm edit)  {
        final MeldPost post = new MeldPost();
        final User user = service.currentUser();

        post.setUser(user);
        post.setCreated(Instant.now());
        post.setText(edit.getText());

        final MeldImage image = new MeldImage();
        final Blob file = edit.getFile();
        if (file != null) {
            image.setFileName(file.getName());
            image.setImage(file.getData());
            image.setThumbnail(ImageUtils.thumnail(file.getName(), file.getData()));
        }

        post.setImage(image);
        service.savePost(post);



        return read(post.getId());
    }


    public static void linkRead(MeldPost post, LinksContainer container, URLBuilderFactory builderFactory) {
        builderFactory
                .from(MeldPostFormController.class)
                .record(method -> method.read(post.getId()))
                .rel("read")
                .buildSecured(container::addLink);
    }

    private static void linkUpdate(MeldPost post, LinksContainer container, URLBuilderFactory builderFactory) {
        builderFactory
                .from(MeldPostFormController.class)
                .record(method -> method.update(post.getId(), null))
                .rel("update")
                .buildSecured(container::addLink);
    }

    private static void linkSave(MeldPost post, LinksContainer container, URLBuilderFactory builderFactory) {
        builderFactory
                .from(MeldPostFormController.class)
                .record(method -> method.save(null))
                .rel("save")
                .buildSecured(container::addLink);
    }


}
