package net.portrix.meld.social.profile;

import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilder;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.meld.media.photos.Photo;
import net.portrix.meld.media.photos.form.PhotoFormController;
import net.portrix.meld.social.people.Category;
import net.portrix.meld.social.people.RelationShip;
import net.portrix.meld.social.profile.contact.form.ContactFormController;
import net.portrix.meld.social.profile.education.form.EducationFormController;
import net.portrix.meld.social.profile.places.form.PlacesFormController;
import net.portrix.meld.social.profile.user.form.UserFormController;
import net.portrix.meld.social.profile.workhistory.form.WorkHistoryFormController;
import net.portrix.meld.usercontrol.User;
import org.picketlink.Identity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Set;
import java.util.UUID;

/**
 * @author Patrick Bittner on 25/11/2016.
 */
@Path("social")
@ApplicationScoped
@Name("Social")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProfileController {

    private final ProfileService service;

    private final URLBuilderFactory factory;

    private final Identity identity;


    @Inject
    public ProfileController(ProfileService service, URLBuilderFactory factory, Identity identity) {
        this.service = service;
        this.factory = factory;
        this.identity = identity;
    }

    public ProfileController() {
        this(null, null, null);
    }

    @GET
    @Path("user/current/profile")
    @Name("Profile Background Read")
    @Secured
    public ProfileResponse read() {
        final User user = service.currentUser();
        return read(user.getId());
    }

    @GET
    @Path("user/{id}/profile")
    @Name("Profile Background Read")
    @Secured
    @Transactional
    public ProfileResponse read(@PathParam("id") UUID id) {
        final ProfileResponse response = new ProfileResponse();

        User user = service.findUser(id);

        response.setName(user.getFirstName() + " " + user.getLastName());

        Profile profile = service.find(user);

        if (profile != null) {
            response.setImage(PhotoFormController.linkPhoto(profile.getUserPhoto(), factory)
                    .generateUri());

            response.setBackground(PhotoFormController.linkPhoto(profile.getBackgroundPhoto(), factory)
                    .generateUri());
            }

        User currentUser = service.currentUser();

        if (currentUser.equals(user)) {
            linkProfileBackgroundUpdate(factory)
                    .buildSecured(response::addLink);
            linkProfileUserUpdate(factory)
                    .buildSecured(response::addLink);

            UserFormController.linkCurrent(factory)
                    .buildSecured(response::addLink);

            ContactFormController.linkCurrent(factory)
                    .buildSecured(response::addLink);

            EducationFormController.linkCurrent(factory)
                    .buildSecured(response::addLink);

            PlacesFormController.linkCurrent(factory)
                    .buildSecured(response::addLink);

            WorkHistoryFormController.linkCurrent(factory)
                    .buildSecured(response::addLink);
        } else {

            RelationShip relationShip = service.findRelation(user, currentUser);

            UserProfile userProfile = service.findUserProfile(user);
            if (userProfile != null) {
                Set<Category> categories = userProfile.getCategories();
                if (categories.contains(relationShip.getCategory())) {
                    UserFormController.linkRead(user, factory)
                            .buildSecured(response::addLink);
                }
            }


            PersonalContact contact = service.findContact(user);
            if (contact != null) {
                Set<Category> categories = contact.getCategories();
                if (categories.contains(relationShip.getCategory())) {
                    ContactFormController.linkRead(contact, factory)
                            .buildSecured(response::addLink);
                }
            }

            Education education = service.findEducation(user);
            if (education != null) {
                Set<Category> categories = education.getCategories();
                if (categories.contains(relationShip.getCategory())) {
                    EducationFormController.linkRead(education, factory)
                            .buildSecured(response::addLink);
                }

            }

            Places places = service.findPlaces(user);
            if (places != null) {
                Set<Category> categories = places.getCategories();
                if (categories.contains(relationShip.getCategory())) {
                    PlacesFormController.linkRead(places, factory)
                            .buildSecured(response::addLink);
                }
            }

            WorkHistory workHistory = service.findWorkHistory(user);
            if (workHistory != null) {
                Set<Category> categories = workHistory.getCategories();
                if (categories.contains(relationShip.getCategory())) {
                    WorkHistoryFormController.linkRead(workHistory, factory)
                            .buildSecured(response::addLink);
                }
            }


        }

        return response;
    }


    @POST
    @Path("user/current/profile/background")
    @Name("Profile Background Read")
    @Secured
    @Transactional
    public ProfileResponse updateBackground(ProfileForm form) {

        final User user = service.currentUser();

        Profile profile = service.find(user);

        if (profile == null) {
            profile = new Profile();
            profile.setUser(user);
        }

        Photo photo = service.find(form.getPhotoId());
        profile.setBackgroundPhoto(photo);

        if (profile.getId() == null) {
            service.save(profile);
        }

        return read();
    }

    @POST
    @Path("user/current/profile/user")
    @Name("Profile User Update")
    @Secured
    @Transactional
    public ProfileResponse updateUser(ProfileForm form) {

        final User user = service.currentUser();

        Profile profile = service.find(user);

        if (profile == null) {
            profile = new Profile();
            profile.setUser(user);
        }

        Photo photo = service.find(form.getPhotoId());
        profile.setUserPhoto(photo);

        if (profile.getId() == null) {
            service.save(profile);
        }

        return read();
    }


    public static URLBuilder<ProfileController> linkProfile(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(ProfileController.class)
                .record(ProfileController::read)
                .rel("profile");
    }

    public static URLBuilder<ProfileController> linkProfileUserUpdate(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(ProfileController.class)
                .record((method) -> method.updateUser(null))
                .rel("update");
    }

    public static URLBuilder<ProfileController> linkProfileBackgroundUpdate(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(ProfileController.class)
                .record((method) -> method.updateBackground(null))
                .rel("update");
    }

}
