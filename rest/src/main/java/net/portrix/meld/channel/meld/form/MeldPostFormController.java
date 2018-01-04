package net.portrix.meld.channel.meld.form;

import net.portrix.generic.image.ImageUtils;
import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilder;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.api.Blob;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.meld.channel.*;
import net.portrix.meld.media.photos.Photo;
import net.portrix.meld.social.people.Category;
import net.portrix.meld.usercontrol.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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
    @Path("meld/create/{type}")
    @Name("Meld Post Create")
    @Transactional
    public AbstractPostForm create(@PathParam("type") String type) {
        switch (type) {
            case "image": {
                MeldImagePostForm form = new MeldImagePostForm();
                linkSave(builderFactory)
                        .buildSecured(form::addLink);
                return form;
            }
            case "text": {
                MeldTextPostForm form = new MeldTextPostForm();
                linkSave(builderFactory)
                        .buildSecured(form::addLink);
                return form;
            }
            case "photo": {
                MeldPhotoPostForm form = new MeldPhotoPostForm();
                linkSave(builderFactory)
                        .buildSecured(form::addLink);
                return form;
            }
            case "youtube": {
                MeldYouTubePostForm form = new MeldYouTubePostForm();
                linkSave(builderFactory)
                        .buildSecured(form::addLink);
                return form;
            }
            default:
                return null;
        }
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
                Category category = post.getCategory();
                if (category != null) {
                    response.setCategory(category.getId());
                }

                final Blob file = new Blob();
                final MeldImage image = post.getImage();

                if (post.hasImage()) {
                    file.setName(image.getFileName());
                    file.setData(image.getImage());
                    response.setFile(file);
                }

                linkUpdate(post, builderFactory)
                        .buildSecured(response::addLink);
                linkDelete(post, builderFactory)
                        .buildSecured(response::addLink);

                return response;
            }

            @Override
            public Object visit(MeldTextPost post) {
                MeldTextPostForm response = new MeldTextPostForm();
                response.setId(post.getId());
                response.setText(post.getText());
                Category category = post.getCategory();
                if (category != null) {
                    response.setCategory(category.getId());
                }

                linkUpdate(post, builderFactory)
                        .buildSecured(response::addLink);
                linkDelete(post, builderFactory)
                        .buildSecured(response::addLink);

                return response;

            }

            @Override
            public Object visit(MeldYouTubePost post) {
                MeldYouTubePostForm form = new MeldYouTubePostForm();
                form.setId(post.getId());
                form.setVideoId(post.getVideoId());
                form.setText(post.getText());
                Category category = post.getCategory();
                if (category != null) {
                    form.setCategory(category.getId());
                }

                linkUpdate(post, builderFactory)
                        .buildSecured(form::addLink);
                linkDelete(post, builderFactory)
                        .buildSecured(form::addLink);

                return form;
            }

            @Override
            public Object visit(MeldPhotoPost post) {
                MeldPhotoPostForm form = new MeldPhotoPostForm();
                form.setId(post.getId());
                form.setPhotoId(post.getPhoto().getId());
                form.setText(post.getText());
                Category category = post.getCategory();
                if (category != null) {
                    form.setCategory(category.getId());
                }

                linkUpdate(post, builderFactory)
                        .buildSecured(form::addLink);
                linkDelete(post, builderFactory)
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
                image.setThumbnail(ImageUtils.thumnail(file.getName(), file.getData(), 100));

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

            @Override
            public AbstractPostForm visit(MeldPhotoPostForm form) {
                final MeldPhotoPost post = (MeldPhotoPost) service.findPost(id);
                User user = service.currentUser();
                post.setUser(user);
                Category category = service.findCategory(edit.getCategory());
                post.setCategory(category);
                Photo photo = service.findPhoto(form.getPhotoId());
                post.setPhoto(photo);
                return read(post.getId());
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
                Category category = service.findCategory(edit.getCategory());
                post.setCategory(category);

                post.setUser(user);
                post.setText(edit.getText());

                final MeldImage image = new MeldImage();
                final Blob file = form.getFile();
                if (file != null) {
                    image.setFileName(file.getName());
                    image.setImage(file.getData());
                    image.setThumbnail(ImageUtils.thumnail(file.getName(), file.getData(), 100));
                }

                post.setImage(image);
                service.savePost(post);
                return read(post.getId());
            }

            @Override
            public AbstractPostForm visit(MeldTextPostForm form) {
                final MeldTextPost post = new MeldTextPost();
                final User user = service.currentUser();
                Category category = service.findCategory(edit.getCategory());
                post.setCategory(category);

                post.setUser(user);
                post.setText(edit.getText());
                service.savePost(post);
                return read(post.getId());

            }

            @Override
            public AbstractPostForm visit(MeldYouTubePostForm form) {
                final MeldYouTubePost post = new MeldYouTubePost();
                final User user = service.currentUser();
                Category category = service.findCategory(edit.getCategory());
                post.setCategory(category);

                post.setUser(user);
                post.setVideoId(form.getVideoId());
                service.savePost(post);
                return read(post.getId());
            }

            @Override
            public AbstractPostForm visit(MeldPhotoPostForm form) {
                final MeldPhotoPost post = new MeldPhotoPost();
                User user = service.currentUser();
                post.setUser(user);
                Category category = service.findCategory(edit.getCategory());
                post.setCategory(category);

                Photo photo = service.findPhoto(form.getPhotoId());
                post.setPhoto(photo);
                service.savePost(post);
                return read(post.getId());
            }
        });
    }

    @Secured
    @DELETE
    @Path("meld/{id}")
    @Name("Meld Post Delete")
    @Transactional
    public void delete(@PathParam("id") UUID id) {
        MeldPost post = service.findPost(id);

        User user = service.currentUser();

        if (post.getUser().equals(user)) {
            service.deletePost(post);
        }
    }


    public static URLBuilder<MeldPostFormController> linkCreate(String type, URLBuilderFactory builderFactory) {
        return builderFactory
                .from(MeldPostFormController.class)
                .record(method -> method.create(type))
                .rel("create");
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

    private static URLBuilder<MeldPostFormController> linkSave(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(MeldPostFormController.class)
                .record(method -> method.save(null))
                .rel("save");
    }

    private static URLBuilder<MeldPostFormController> linkDelete(MeldPost post, URLBuilderFactory builderFactory) {
        return builderFactory
                .from(MeldPostFormController.class)
                .record(method -> method.delete(post.getId()))
                .rel("delete");
    }

}
