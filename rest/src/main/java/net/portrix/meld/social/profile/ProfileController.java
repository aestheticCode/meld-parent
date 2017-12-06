package net.portrix.meld.social.profile;

import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilder;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.api.Link;
import net.portrix.generic.rest.api.LinksContainer;
import net.portrix.generic.rest.jsr339.Name;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Set;

/**
 * @author Patrick Bittner on 25/11/2016.
 */
@Path("social")
@ApplicationScoped
@Name("Social")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProfileController {

    @GET
    @Path("profile")
    @Name("Profile Read")
    @Secured
    public LinksContainer read() {
        return new LinksContainer() {
            @Override
            public Set<Link> getLinks() {
                return null;
            }

            @Override
            public boolean addLink(Link link) {
                return false;
            }
        };
    }

    public static URLBuilder<ProfileController> linkProfile(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(ProfileController.class)
                .record(ProfileController::read)
                .rel("profile");
    }


}
