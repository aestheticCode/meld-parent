package net.portrix.meld.social.user;

import net.portrix.generic.image.ImageUtils;
import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.api.Blob;
import net.portrix.generic.rest.api.LinksContainer;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.meld.usercontrol.Group;
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
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

/**
 * @author Patrick Bittner on 21.06.2015.
 */
@Name("Social")
@Path("social")
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
    @Path("user/current/form")
    @Name("User Form Read")
    @Transactional
    public UserForm current() {
        User user = service.currentUser();
        return read(user.getId());
    }

    @Secured
    @GET
    @Path("user/{id: [0-9a-f]{8}-([0-9a-f]{4}-){3}[0-9a-f]{12}}/form")
    @Name("User Form Read")
    @Transactional
    public UserForm read(@PathParam("id") UUID id) {

        final User user = service.findUser(id);
        final UserImage userImage = service.findUserImage(user);

        UserForm response = new UserForm();
        response.setId(user.getId());
        response.setEmail(user.getName());

        switch (user.getGender()) {
            case MALE:
                response.setGender(Gender.Male);
                break;
            case FEMALE:
                response.setGender(Gender.Female);
                break;
        }

        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setBirthday(user.getBirthdate());
        final Blob image = new Blob();
        image.setData(userImage.getImage());
        image.setName(userImage.getFileName());
        response.setImage(image);

        linkRead(user, response, builderFactory);
        linkUpdate(response, builderFactory);

        return response;

    }


    @Secured
    @PUT
    @Path("user/current/form")
    @Transactional
    @Name("User Form Update")
    public UserForm update(final UserForm form) {
        final User user = service.currentUser();
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

        return read(user.getId());
    }

    @Secured
    @POST
    @Path("user/current/form")
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

        return read(user.getId());
    }


    @Secured
    @POST
    @Path("user/current/form/validate")
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

    public static void linkUpdate(LinksContainer container, URLBuilderFactory builderFactory) {
        builderFactory
                .from(UserFormController.class)
                .record(method -> method.update(null))
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

    public static void linkCurrent(LinksContainer linksContainer, URLBuilderFactory builderFactory) {
        builderFactory
                .from(UserFormController.class)
                .record(UserFormController::current)
                .rel("current")
                .buildSecured(linksContainer::addLink);
    }
}
