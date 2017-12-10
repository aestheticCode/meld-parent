package net.portrix.meld.channel.meld.form;

import net.portrix.generic.image.ImageUtils;
import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilder;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.api.Blob;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.meld.channel.*;
import net.portrix.meld.social.people.Category;
import net.portrix.meld.usercontrol.User;
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
    public AbstractPostForm read(@PathParam("id") UUID id) {
        final MeldPost post = service.findPost(id);

        return (AbstractPostForm) post.accept(new MeldPost.Visitor() {
            @Override
            public Object visit(MeldImagePost post) {
                MeldImagePostForm response = new MeldImagePostForm();
                response.setId(post.getId());
                response.setText(post.getText());
                final Blob file = new Blob();
                final MeldImage image = post.getImage();

                if (post.hasImage()) {
                    file.setName(image.getFileName());
                    file.setData(image.getImage());
                    response.setFile(file);
                }

                linkUpdate(post, builderFactory)
                        .buildSecured(response::addLink);

                return response;
            }

            @Override
            public Object visit(MeldTextPost post) {
                MeldTextPostForm response = new MeldTextPostForm();
                response.setId(post.getId());
                response.setText(post.getText());
                linkUpdate(post, builderFactory)
                        .buildSecured(response::addLink);

                return response;

            }

            @Override
            public Object visit(MeldYouTubePost post) {
                MeldYouTubePostForm form = new MeldYouTubePostForm();
                form.setId(post.getId());
                form.setVideoId(post.getVideoId());
                form.setText(post.getText());
                linkUpdate(post, builderFactory)
                        .buildSecured(form::addLink);

                return form;
            }
        });

    }

    @Secured
    @PUT
    @Path("meld/{id}")
    @Name("Meld Post Update")
    @Transactional
    public AbstractPostForm update(@PathParam("id") UUID id, AbstractPostForm edit) {
        return edit.visit(new AbstractPostForm.Visitor() {
            @Override
            public AbstractPostForm visit(MeldImagePostForm form) {
                final MeldImagePost post = (MeldImagePost) service.findPost(id);
                final User user = service.currentUser();
                Category category = service.findCategory(edit.getCategory());
                post.setCategory(category);
                post.setUser(user);
                post.setText(form.getText());

                final MeldImage image = post.getImage();

                final Blob file = form.getFile();
                image.setFileName(file.getName());
                image.setImage(file.getData());
                image.setThumbnail(ImageUtils.thumnail(file.getName(), file.getData()));

                return read(post.getId());
            }

            @Override
            public AbstractPostForm visit(MeldTextPostForm form) {
                final MeldImagePost post = (MeldImagePost) service.findPost(id);
                final User user = service.currentUser();
                Category category = service.findCategory(edit.getCategory());
                post.setCategory(category);
                post.setUser(user);
                post.setText(form.getText());
                return read(post.getId());
            }

            @Override
            public AbstractPostForm visit(MeldYouTubePostForm form) {
                final MeldYouTubePost post = (MeldYouTubePost) service.findPost(id);
                final User user = service.currentUser();
                Category category = service.findCategory(edit.getCategory());
                post.setCategory(category);
                post.setUser(user);
                post.setVideoId(form.getVideoId());
                post.setText(form.getText());
                return read(id);
            }
        });

    }

    @Secured
    @POST
    @Path("meld")
    @Name("Meld Post Save")
    @Transactional
    public AbstractPostForm save(AbstractPostForm edit) {
        return edit.visit(new AbstractPostForm.Visitor() {
            @Override
            public AbstractPostForm visit(MeldImagePostForm form) {
                final MeldImagePost post = new MeldImagePost();
                final User user = service.currentUser();

                post.setUser(user);
                post.setCreated(Instant.now());
                post.setText(edit.getText());

                final MeldImage image = new MeldImage();
                final Blob file = form.getFile();
                if (file != null) {
                    image.setFileName(file.getName());
                    image.setImage(file.getData());
                    image.setThumbnail(ImageUtils.thumnail(file.getName(), file.getData()));
                }

                post.setImage(image);
                service.savePost(post);
                return read(post.getId());
            }

            @Override
            public AbstractPostForm visit(MeldTextPostForm form) {
                final MeldTextPost post = new MeldTextPost();
                final User user = service.currentUser();
                post.setUser(user);
                post.setCreated(Instant.now());
                post.setText(edit.getText());
                service.savePost(post);
                return read(post.getId());

            }

            @Override
            public AbstractPostForm visit(MeldYouTubePostForm form) {
                final MeldYouTubePost post = new MeldYouTubePost();
                final User user = service.currentUser();
                post.setUser(user);
                post.setCreated(Instant.now());
                post.setVideoId(form.getVideoId());
                service.savePost(post);
                return read(post.getId());
            }
        });
    }


    public static URLBuilder<MeldPostFormController> linkRead(MeldPost post, URLBuilderFactory builderFactory) {
        return builderFactory
                .from(MeldPostFormController.class)
                .record(method -> method.read(post.getId()))
                .rel("read");
    }

    private static URLBuilder<MeldPostFormController> linkUpdate(MeldPost post, URLBuilderFactory builderFactory) {
        return builderFactory
                .from(MeldPostFormController.class)
                .record(method -> method.update(post.getId(), null))
                .rel("update");
    }

    private static URLBuilder<MeldPostFormController> linkSave(MeldPost post, URLBuilderFactory builderFactory) {
        return builderFactory
                .from(MeldPostFormController.class)
                .record(method -> method.save(null))
                .rel("save");
    }


}
