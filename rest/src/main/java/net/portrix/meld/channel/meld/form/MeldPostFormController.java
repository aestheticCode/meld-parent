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
import net.portrix.meld.social.people.find.table.CategorySelect;
import net.portrix.meld.usercontrol.User;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.Map;
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
            case "link": {
                MeldLinkPostForm form = new MeldLinkPostForm();
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

                processPostRead(post, response);

                final Blob file = new Blob();
                final MeldImage image = post.getImage();

                if (post.hasImage()) {
                    file.setName(image.getFileName());
                    file.setData(image.getImage());
                    response.setFile(file);
                }

                return response;
            }

            @Override
            public Object visit(MeldTextPost post) {
                MeldTextPostForm response = new MeldTextPostForm();

                processPostRead(post, response);

                return response;

            }

            @Override
            public Object visit(MeldYouTubePost post) {
                MeldYouTubePostForm form = new MeldYouTubePostForm();

                processPostRead(post, form);

                form.setVideoId(post.getVideoId());

                return form;
            }

            @Override
            public Object visit(MeldPhotoPost post) {
                MeldPhotoPostForm form = new MeldPhotoPostForm();

                processPostRead(post, form);

                form.setPhotoId(post.getPhoto().getId());

                return form;
            }

            @Override
            public Object visit(MeldLinkPost post) {
                MeldLinkPostForm form = new MeldLinkPostForm();

                processPostRead(post, form);

                form.setLink(post.getLink());

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

                processPostUpdate(form, post);

                final MeldImage image = post.getImage();
                final Blob file = form.getFile();
                image.setFileName(file.getName());
                image.setImage(file.getData());
                image.setThumbnail(ImageUtils.thumnail(file.getName(), file.getData(), 100));

                return read(post.getId());
            }

            @Override
            public AbstractPostForm visit(MeldTextPostForm form) {
                final MeldTextPost post = (MeldTextPost) service.findPost(id);
                processPostUpdate(form, post);
                return read(post.getId());
            }

            @Override
            public AbstractPostForm visit(MeldYouTubePostForm form) {
                final MeldYouTubePost post = (MeldYouTubePost) service.findPost(id);

                processPostUpdate(form, post);

                post.setVideoId(form.getVideoId());

                return read(id);
            }

            @Override
            public AbstractPostForm visit(MeldPhotoPostForm form) {
                final MeldPhotoPost post = (MeldPhotoPost) service.findPost(id);

                processPostUpdate(form, post);

                Photo photo = service.findPhoto(form.getPhotoId());
                post.setPhoto(photo);
                return read(post.getId());
            }

            @Override
            public AbstractPostForm visit(MeldLinkPostForm form) {
                final MeldLinkPost post = (MeldLinkPost) service.findPost(id);

                processPostUpdate(edit, post);

                post.setLink(form.getLink());

                return read(post.getId());
            }
        });

    }

    @Secured
    @POST
    @Path("meld")
    @Name("Meld Post Save")
    @Transactional
    public AbstractPostForm save(final AbstractPostForm edit) {
        return edit.visit(new AbstractPostForm.Visitor() {
            @Override
            public AbstractPostForm visit(MeldImagePostForm form) {
                final MeldImagePost post = new MeldImagePost();

                processPostSave(edit, post);

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

                processPostSave(edit, post);

                service.savePost(post);
                return read(post.getId());

            }

            @Override
            public AbstractPostForm visit(MeldYouTubePostForm form) {
                final MeldYouTubePost post = new MeldYouTubePost();

                processPostSave(edit, post);

                post.setVideoId(form.getVideoId());

                service.savePost(post);
                return read(post.getId());
            }

            @Override
            public AbstractPostForm visit(MeldPhotoPostForm form) {
                final MeldPhotoPost post = new MeldPhotoPost();

                processPostSave(edit, post);

                Photo photo = service.findPhoto(form.getPhotoId());
                post.setPhoto(photo);

                service.savePost(post);
                return read(post.getId());
            }

            @Override
            public AbstractPostForm visit(MeldLinkPostForm form) {
                MeldLinkPost post = new MeldLinkPost();

                processPostSave(edit, post);

                post.setLink(form.getLink());

                try {
                    Map<String, String> environment = System.getenv();

                    String phantomjsDirectory = environment.get("PHANTOMJS");

                    Runtime runtime = Runtime.getRuntime();

                    File phantomJSExecutable = new File(phantomjsDirectory + File.separator + "bin" + File.separator + "phantomjs");

                    String home = System.getProperty("user.home");
                    File meld = new File(home + File.separator + ".meld");
                    File screenCaptureWorkingDir = new File(meld.getCanonicalPath() + File.separator + UUID.randomUUID().toString());

                    FileUtils.forceMkdir(screenCaptureWorkingDir);

                    File scriptFile = new File(home + File.separator + ".meld" + File.separator + "screenCapture.js");

                    String command = phantomJSExecutable.getCanonicalPath() + " " + scriptFile.toString() + " " + form.getLink().toString();

                    Process exec = runtime.exec(command, new String[0], screenCaptureWorkingDir);

                    exec.waitFor();

                    File screenCaptureFile = new File(screenCaptureWorkingDir.getCanonicalFile() + File.separator + "screenShot.png");

                    byte[] bytes = FileUtils.readFileToByteArray(screenCaptureFile);

                    MeldImage image = new MeldImage();

                    image.setFileName(screenCaptureFile.getName());
                    image.setLastModified(LocalDateTime.now());
                    image.setImage(bytes);
                    image.setThumbnail(ImageUtils.thumnail(screenCaptureFile.getName(), bytes, 200));

                    post.setImage(image);

                    service.savePost(post);

                } catch (IOException | InterruptedException e) {
                    log.error(e.getMessage(), e);
                }


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

    private void processPostRead(MeldPost post, AbstractPostForm response) {
        response.setId(post.getId());
        response.setText(post.getText());
        Category category = post.getCategory();
        if (category != null) {
            CategorySelect categorySelect = new CategorySelect();
            categorySelect.setId(category.getId());
            categorySelect.setName(category.getName());
            response.setCategory(categorySelect);
        }

        linkUpdate(post, builderFactory)
                .buildSecured(response::addLink);
        linkDelete(post, builderFactory)
                .buildSecured(response::addLink);
    }

    private void processPostUpdate(AbstractPostForm form, MeldPost post) {
        final User user = service.currentUser();
        if (form.getCategory() != null) {
            Category category = service.findCategory(form.getCategory().getId());
            post.setCategory(category);
        }
        post.setUser(user);
        post.setText(form.getText());
    }

    private void processPostSave(AbstractPostForm form, MeldPost post) {
        final User user = service.currentUser();
        if (form.getCategory() != null) {
            Category category = service.findCategory(form.getCategory().getId());
            post.setCategory(category);
        }
        post.setUser(user);
        post.setText(form.getText());
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
