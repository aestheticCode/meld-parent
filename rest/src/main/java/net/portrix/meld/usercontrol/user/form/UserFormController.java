package net.portrix.meld.usercontrol.user.form;

import com.google.common.collect.Sets;
import net.portrix.generic.image.ImageUtils;
import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilder;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.api.Blob;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.meld.media.photos.Photo;
import net.portrix.meld.social.profile.Profile;
import net.portrix.meld.usercontrol.Group;
import net.portrix.meld.usercontrol.Role;
import net.portrix.meld.usercontrol.User;
import net.portrix.meld.usercontrol.UserImage;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Patrick Bittner on 21.06.2015.
 */
@Name("User Control")
@Path("usercontrol")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserFormController {

    private static Logger log = LoggerFactory.getLogger(UserFormController.class);

    private final UserFormService service;

    private final URLBuilderFactory builderFactory;

    @Inject
    public UserFormController(UserFormService service, URLBuilderFactory builderFactory) {
        this.service = service;
        this.builderFactory = builderFactory;
    }

    public UserFormController() {
        this(null, null);
    }


    @Secured
    @GET
    @Path("user/create/form")
    @Name("User Form Read")
    @Transactional
    public UserForm create() {
        UserForm response = new UserForm();

        linkSave(builderFactory)
                .buildSecured(response::addLink);

        return response;
    }

    @Secured
    @GET
    @Path("user/{id}/form")
    @Name("User Form Read")
    @Transactional
    public UserForm read(@PathParam("id") UUID id) {

        final User user = service.findUser(id);

        UserForm response = new UserForm();
        response.setId(user.getId());
        response.setEmail(user.getName());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setBirthday(user.getBirthdate());
        response.setGender(user.getGender());

        final Profile profile = service.findProfile(user);
        if (profile != null && profile.getUserPhoto() != null) {
            final Blob image = new Blob();
            image.setData(profile.getUserPhoto().getImage());
            image.setName(profile.getUserPhoto().getFileName());
            response.setImage(image);
        }

        final List<Group> groups = service.findAllGroups(user);

        final List<UUID> groudIds = new ArrayList<>();

        for (Group group : groups) {
            groudIds.add(group.getId());
        }
        response.setGroups(Sets.newHashSet(groudIds));


        final List<Role> roles = service.findAllRoles(user);

        List<UUID> roleIds = new ArrayList<>();

        for (Role role : roles) {
            roleIds.add(role.getId());
        }
        response.setRoles(Sets.newHashSet(roleIds));

        linkRead(user, builderFactory)
                .buildSecured(response::addLink);
        linkUpdate(user, builderFactory)
                .buildSecured(response::addLink);
        linkDelete(user, builderFactory)
                .buildSecured(response::addLink);

        return response;

    }


    @Secured
    @PUT
    @Path("user/{id}/form")
    @Transactional
    @Name("User Form Update")
    public UserForm update(final @PathParam("id") UUID id, final UserForm form) {
        final User user = service.findUser(id);
        user.setName(form.getEmail());
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        user.setBirthdate(form.getBirthday());
        user.setGender(form.getGender());

        final Blob image = form.getImage();
        if (image != null) {
            final Profile profile = service.findProfile(user);

            profile.getUserPhoto().setFileName(image.getName());
            profile.getUserPhoto().setImage(image.getData());
            profile.getUserPhoto().setLastModified(image.getLastModified());
            profile.getUserPhoto().setThumbnail(ImageUtils.thumnail(image.getName(), image.getData(), 200));
        }

        final List<Group> groups = service.findAllGroups();
        for (Group group : groups) {
            if (form.getGroups().contains(group.getId())) {
                group.add(user);
            } else {
                group.remove(user);
            }
        }
        final List<Role> roles = service.findAllRoles();
        for (Role role : roles) {
            if (form.getRoles().contains(role.getId())) {
                role.addScope(user);
            } else {
                role.removeScope(user);
            }
        }
        return read(user.getId());
    }

    @Secured
    @POST
    @Path("user/form")
    @Transactional
    @Name("User Form Save")
    public UserForm save(final UserForm form) {
        final User user = new User();
        user.setName(form.getEmail());
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        user.setBirthdate(form.getBirthday());
        user.setGender(form.getGender());
        service.save(user);

        if (form.getImage() != null) {
            Photo photo = new Photo();
            photo.setUser(user);
            photo.setFileName(form.getImage().getName());
            photo.setLastModified(form.getImage().getLastModified());
            photo.setThumbnail(ImageUtils.thumnail(form.getImage().getName(), form.getImage().getData(), 200));
            service.savePhoto(photo);
        }

        final List<Group> groups = service.findAllGroups();
        for (Group group : groups) {
            if (form.getGroups().contains(group.getId())) {
                group.add(user);
            } else {
                group.remove(user);
            }
        }
        final List<Role> roles = service.findAllRoles();
        for (Role role : roles) {
            if (form.getRoles().contains(role.getId())) {
                role.addScope(user);
            } else {
                role.removeScope(user);
            }
        }
        return read(user.getId());
    }

    @Secured
    @DELETE
    @Path("user/{id}/form")
    @Name("User Form Delete")
    @Transactional
    public Response delete(@PathParam("id") UUID id) {

        final User user = service.findUser(id);
        service.deleteUser(user);

        return Response.ok().build();
    }


    @Secured
    @POST
    @Path("user/form/validate")
    @Transactional
    @Name("User Form Name Validate")
    public boolean validate(UserNameValidation validation) {
        return service.validateUserName(validation);
    }

    public static URLBuilder<UserFormController> linkCreate(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(UserFormController.class)
                .record(UserFormController::create)
                .rel("create");
    }

    public static URLBuilder<UserFormController> linkRead(User user, URLBuilderFactory builderFactory) {
        return builderFactory
                .from(UserFormController.class)
                .record(method -> method.read(user.getId()))
                .rel("read");
    }

    public static URLBuilder<UserFormController> linkUpdate(User user, URLBuilderFactory builderFactory) {
        return builderFactory
                .from(UserFormController.class)
                .record(method -> method.update(user.getId(), null))
                .rel("update");
    }

    public static URLBuilder<UserFormController> linkSave(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(UserFormController.class)
                .record(method -> method.save(null))
                .rel("save");
    }

    public static URLBuilder<UserFormController> linkDelete(User user, URLBuilderFactory builderFactory) {
        return builderFactory
                .from(UserFormController.class)
                .record(method -> method.delete(user.getId()))
                .rel("delete");
    }

}
