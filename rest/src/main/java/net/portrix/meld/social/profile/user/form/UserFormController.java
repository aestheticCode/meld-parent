package net.portrix.meld.social.profile.user.form;

import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilder;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.meld.social.profile.ProfileVisibilities;
import net.portrix.meld.social.profile.UserProfile;
import net.portrix.meld.usercontrol.User;
import org.picketlink.Identity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
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

    private final Identity identity;


    @Inject
    public UserFormController(UserFormService service, URLBuilderFactory builderFactory, Identity identity) {
        this.service = service;
        this.builderFactory = builderFactory;
        this.identity = identity;
    }

    public UserFormController() {
        this(null, null, null);
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

        final UserProfile user = service.findUser(id);

        UserForm response = new UserForm();
        response.setId(user.getUser().getId());

        ProfileVisibilities.setVisibilities(user, response);

        response.setFirstName(user.getUser().getFirstName());
        response.setLastName(user.getUser().getLastName());
        response.setBirthday(user.getUser().getBirthdate());
        response.setGender(user.getUser().getGender());

        User currentUser = service.currentUser();

        if (user.getUser().equals(currentUser)) {
            linkUpdate(builderFactory)
                    .buildSecured(response::addLink);
            linkDelete(builderFactory)
                    .buildSecured(response::addLink);
        }


        return response;

    }


    @Secured
    @PUT
    @Path("user/current/form")
    @Transactional
    @Name("User Form Update")
    public UserForm update(final UserForm form) {
        final UserProfile user = service.findUser(service.currentUser().getId());

        ProfileVisibilities.getVisibilities(form, user, service);

        user.getUser().setFirstName(form.getFirstName());
        user.getUser().setLastName(form.getLastName());
        user.getUser().setBirthdate(form.getBirthday());
        user.getUser().setGender(form.getGender());

        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("dMMMuuuu")
                .toFormatter();
        String birthday = form.getBirthday().format(formatter);
        String userId = form.getFirstName() + form.getLastName() + birthday;
        user.getUser().setName(userId);

        return read(user.getUser().getId());
    }

    @Secured
    @POST
    @Path("user/current/form")
    @Transactional
    @Name("User Form Save")
    public UserForm save(final UserForm form) {
        final UserProfile user = new UserProfile();

        ProfileVisibilities.getVisibilities(form, user, service);

        user.getUser().setFirstName(form.getFirstName());
        user.getUser().setLastName(form.getLastName());
        user.getUser().setBirthdate(form.getBirthday());
        user.getUser().setGender(form.getGender());

        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("dMMMuuuu")
                .toFormatter();
        String birthday = form.getBirthday().format(formatter);
        String userId = form.getFirstName() + form.getLastName() + birthday;
        user.getUser().setName(userId);

        service.save(user);

        return read(user.getUser().getId());
    }

    @Secured
    @DELETE
    @Path("user/current/form")
    @Name("User Form Delete")
    @Transactional
    public void delete() {
        User user = service.currentUser();
        service.deleteUser(user);
    }



    @Secured
    @POST
    @Path("user/current/form/validate")
    @Transactional
    @Name("User Form Name Validate")
    public boolean validate(UserNameValidation validation) {
        return service.validateUserName(validation);
    }

    public static URLBuilder<UserFormController> linkCurrent(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(UserFormController.class)
                .record(UserFormController::current)
                .rel("current");
    }

    public static URLBuilder<UserFormController> linkRead(User user, URLBuilderFactory builderFactory) {
        return builderFactory
                .from(UserFormController.class)
                .record(method -> method.read(user.getId()))
                .rel("read");
    }

    public static URLBuilder<UserFormController> linkUpdate(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(UserFormController.class)
                .record(method -> method.update(null))
                .rel("update");
    }

    public static URLBuilder<UserFormController> linkSave(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(UserFormController.class)
                .record(method -> method.save(null))
                .rel("save");
    }

    public static URLBuilder<UserFormController> linkDelete(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(UserFormController.class)
                .record(UserFormController::delete)
                .rel("delete");
    }

}
