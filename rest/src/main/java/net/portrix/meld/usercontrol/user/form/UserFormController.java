package net.portrix.meld.usercontrol.user.form;

import com.google.common.collect.Sets;
import net.portrix.generic.image.ImageUtils;
import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.api.LinksContainer;
import net.portrix.generic.rest.api.Blob;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.meld.usercontrol.*;
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
import java.util.*;

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

        final InputStream inputStream = Thread
                .currentThread()
                .getContextClassLoader()
                .getResourceAsStream("/META-INF/images/user.png");

        byte[] bytes = null;
        try {
            bytes = IOUtils.toByteArray(inputStream);

            final Blob image = new Blob();
            final String fileName = "user.png";
            image.setName(fileName);
            image.setData(bytes);

            response.setImage(image);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return response;
    }

    @Secured
    @GET
    @Path("user/{id}/form")
    @Name("User Form Read")
    @Transactional
    public UserForm read(@PathParam("id") UUID id) {

        final User user = service.findUser(id);
        final UserImage userImage = service.findUserImage(user);

        UserForm response = new UserForm();
        response.setId(user.getId());
        response.setEmail(user.getName());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setBirthday(user.getBirthdate());
        final Blob image = new Blob();
        image.setData(userImage.getImage());
        image.setName(userImage.getFileName());
        response.setImage(image);


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

        linkRead(user, response, builderFactory);
        linkUpdate(user, response, builderFactory);
        linkDelete(user, response, builderFactory);

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

        final Blob image = form.getImage();
        final UserImage userImage = service.findUserImage(user);

        userImage.setFileName(image.getName());
        userImage.setImage(image.getData());
        userImage.setLastModified(image.getLastModified());
        userImage.setThumbnail(ImageUtils.thumnail(image.getName(), image.getData()));

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
        service.save(user);
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

    public static void linkRead(User user, LinksContainer container, URLBuilderFactory builderFactory) {
        builderFactory
                .from(UserFormController.class)
                .record(method -> method.read(user.getId()))
                .rel("read")
                .buildSecured(container::addLink);
    }

    public static void linkUpdate(User user, LinksContainer container, URLBuilderFactory builderFactory) {
        builderFactory
                .from(UserFormController.class)
                .record(method -> method.update(user.getId(),null))
                .rel("update")
                .buildSecured(container::addLink);
    }

    public static void linkSave(LinksContainer container, URLBuilderFactory builderFactory) {
        builderFactory
                .from(UserFormController.class)
                .record(method -> method.save(null))
                .rel("save")
                .buildSecured(container::addLink);
    }

    public static void linkDelete(User user, LinksContainer container, URLBuilderFactory builderFactory) {
        builderFactory
                .from(UserFormController.class)
                .record(method -> method.delete(user.getId()))
                .rel("delete")
                .buildSecured(container::addLink);
    }

}
