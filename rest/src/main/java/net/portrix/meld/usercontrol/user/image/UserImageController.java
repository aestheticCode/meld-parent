package net.portrix.meld.usercontrol.user.image;

import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.api.LinksContainer;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.meld.usercontrol.User;
import net.portrix.meld.usercontrol.UserImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Patrick Bittner on 07/10/16.
 */
@Path("usercontrol")
@SessionScoped
@Produces(MediaType.APPLICATION_JSON)
@Name("User Control")
public class UserImageController implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(UserImageController.class);

    private final UserImageService service;

    @Inject
    public UserImageController(UserImageService service) {
        this.service = service;
    }

    public UserImageController() {
        this(null);
    }

    @Secured
    @GET
    @Path("user/{id}/image")
    @Name("User Image")
    @Produces("img/jpeg")
    @Transactional
    public Response image(@PathParam("id") UUID id) {
        final User user = service.findUser(id);
        final UserImage image = service.findUserImage(user);
        Response.ResponseBuilder response = Response.ok(image.getImage());

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
    @Path("user/{id}/thumbnail")
    @Name("User Thumbnail")
    @Produces("img/jpeg")
    @Transactional
    public Response thumbNail(@PathParam("id") UUID id) {
        final User user = service.findUser(id);
        final UserImage image = service.findUserImage(user);
        Response.ResponseBuilder response = Response.ok(image.getThumbnail());

        CacheControl control = new CacheControl();
        control.setMustRevalidate(true);
        control.setMaxAge(0);
        control.setNoCache(true);
        control.setNoStore(true);
        response.cacheControl(control);


        return response.build();
    }

    public static void linkThumbnail(User user, LinksContainer container, URLBuilderFactory builderFactory) {
        builderFactory
                .from(UserImageController.class)
                .record(method -> method.thumbNail(user.getId()))
                .rel("thumbnail")
                .buildSecured(container::addLink);
    }

    public static void linkImage(User user, LinksContainer container, URLBuilderFactory builderFactory) {
        builderFactory
                .from(UserImageController.class)
                .record(method -> method.image(user.getId()))
                .rel("image")
                .buildSecured(container::addLink);
    }

}
