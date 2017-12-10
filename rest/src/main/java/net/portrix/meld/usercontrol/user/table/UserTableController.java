package net.portrix.meld.usercontrol.user.table;

import net.portrix.generic.rest.URLBuilder;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.api.Container;
import net.portrix.generic.rest.api.meta.MetaResponseType;
import net.portrix.generic.rest.api.meta.Property;
import net.portrix.generic.rest.api.query.Query;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.generic.rest.Secured;
import net.portrix.meld.usercontrol.Gender;
import net.portrix.meld.usercontrol.user.form.UserFormController;
import net.portrix.meld.usercontrol.User;
import net.portrix.meld.usercontrol.user.image.UserImageController;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrick Bittner on 30.05.2015.
 */
@Path("usercontrol")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Name("User Control")
public class UserTableController {

    private final UserTableService service;

    private final URLBuilderFactory builderFactory;
    
    private final EntityManager entityManager;

    @Inject
    public UserTableController(UserTableService service, URLBuilderFactory builderFactory, EntityManager entityManager) {
        this.service = service;
        this.entityManager = entityManager;
        this.builderFactory = builderFactory;
    }

    public UserTableController() {
        this(null, null, null);
    }

    @POST
    @Path("user/table")
    @Name("User Table")
    @Secured
    @Transactional
    public Container<UserRowResponse> list(Query search) {
        List<User> users;
        long count = 0;
        if(search.getLimit() == 0) {
            users = new ArrayList<>();
        } else {
            users = service.findUsers(search);
            count = service.countUsers(search);
        }

        final List<UserRowResponse> selects = new ArrayList<>();

        for (User user : users) {
            UserRowResponse response = new UserRowResponse();
            response.setId(user.getId());

            if (user.getGender() == Gender.MALE) {
                response.setGender(GenderResponse.Male);
            }

            if (user.getGender() == Gender.FEMALE) {
                response.setGender(GenderResponse.Female);
            }

            response.setEmail(user.getName());
            response.setFirstName(user.getFirstName());
            response.setLastName(user.getLastName());
            response.setBirthday(user.getBirthdate());

            UserFormController.linkRead(user, builderFactory)
                    .buildSecured(response::addLink);
            UserImageController.linkThumbnail(user, builderFactory)
                    .buildSecured(response::addLink);

            selects.add(response);
        }

        final Container<UserRowResponse> container = new Container<>(selects, (int) count);

        UserFormController.linkSave(builderFactory)
                .buildSecured(container::addLink);

        return container;
    }

    @GET
    @Path("user/table/meta")
    @Name("User Table META")
    @Secured
    public MetaResponseType meta() {
        final MetaResponseType responseType = new MetaResponseType();

        responseType.getProperties().add(new Property("name", "Name", "text"));
        responseType.getProperties().add(new Property("firstName", "First Name", "text"));
        responseType.getProperties().add(new Property("lastName", "Last Name", "text"));
        responseType.getProperties().add(new Property("birthdate", "Birthday", "date"));

        return responseType;
    }

    public static URLBuilder<UserTableController> linkUsers(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(UserTableController.class)
                .record(method -> method.list(null))
                .rel("users");
    }

}
