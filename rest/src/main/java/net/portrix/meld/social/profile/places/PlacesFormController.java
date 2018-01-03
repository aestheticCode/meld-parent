package net.portrix.meld.social.profile.places;

import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilder;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.google.autocomplete.LocationForm;
import net.portrix.generic.rest.google.details.LocationDetailForm;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.meld.social.profile.Address;
import net.portrix.meld.social.profile.Place;
import net.portrix.meld.social.profile.Places;
import net.portrix.meld.usercontrol.User;
import org.picketlink.Identity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

/**
 * @author Patrick Bittner on 22/12/2016.
 */
@Path("social")
@ApplicationScoped
@Name("Social")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PlacesFormController {

    private final PlacesFormService service;

    private final URLBuilderFactory factory;

    private final Identity identity;


    @Inject
    public PlacesFormController(PlacesFormService service, URLBuilderFactory factory, Identity identity) {
        this.service = service;
        this.factory = factory;
        this.identity = identity;
    }

    public PlacesFormController() {
        this(null, null, null);
    }

    @GET
    @Path("user/current/places/create")
    @Name("Places Create")
    @Secured
    @Transactional
    public PlacesForm create() {
        PlacesForm form = new PlacesForm();

        linkSave(factory)
                .buildSecured(form::addLink);

        return form;
    }

    @GET
    @Path("user/current/places")
    @Name("Places Read")
    @Secured
    @Transactional
    public PlacesForm current() {
        User user = service.currentUser();
        return read(user.getId());
    }

    @GET
    @Path("user/{id: [0-9a-f]{8}-([0-9a-f]{4}-){3}[0-9a-f]{12}}/places")
    @Name("Places Read")
    @Secured
    @Transactional
    public PlacesForm read(@PathParam("id") final UUID id) {

        User user = service.findUser(id);

        Places places = service.findPlaces(user);

        if (places == null) {
            throw new NotFoundException();
        }

        PlacesForm placesForm = new PlacesForm();
        placesForm.setId(places.getId());

        for (Address address : places.getAddresses()) {
            AddressForm responseType = new AddressForm();

            LocationDetailForm locationForm = new LocationDetailForm();
            responseType.setLocation(locationForm);

            locationForm.setId(address.getPlace().getId());
            locationForm.setName(address.getPlace().getName());
            locationForm.setStreet(address.getPlace().getStreet());
            locationForm.setStreetNumber(address.getPlace().getStreetNumber());
            locationForm.setZipCode(address.getPlace().getZipCode());
            locationForm.setState(address.getPlace().getState());
            locationForm.setCountry(address.getPlace().getCountry());
            locationForm.setLat(address.getPlace().getLat());
            locationForm.setLng(address.getPlace().getLng());


            responseType.setId(address.getId());
            responseType.setStart(address.getStart());
            responseType.setEnd(address.getEnd());
            responseType.setTillNow(address.isTillNow());

            placesForm.addAddress(responseType);
        }

        User currentUser = service.currentUser();

        if (user == currentUser) {
            linkUpdate(factory)
                    .buildSecured(placesForm::addLink);

            linkDelete(factory)
                    .buildSecured(placesForm::addLink);
        }


        return placesForm;
    }

    @POST
    @Path("user/current/places")
    @Name("Places Save")
    @Secured
    @Transactional
    public PlacesForm save(final PlacesForm form) {

        User user = service.currentUser();

        Places places = new Places();
        places.setUser(user);

        for (AddressForm addressType : form.getAddresses()) {
            Address address = new Address();

            Place place = new Place();
            address.setPlace(place);

            place.setId(addressType.getLocation().getId());
            place.setName(addressType.getLocation().getName());
            place.setCountry(addressType.getLocation().getCountry());
            place.setState(addressType.getLocation().getState());
            place.setStreetNumber(addressType.getLocation().getStreetNumber());
            place.setStreet(addressType.getLocation().getStreet());
            place.setZipCode(addressType.getLocation().getZipCode());
            place.setLat(addressType.getLocation().getLat());
            place.setLng(addressType.getLocation().getLng());

            address.setStart(addressType.getStart());
            address.setEnd(addressType.getEnd());
            address.setTillNow(addressType.isTillNow());

            places.addAddress(address);

        }

        service.savePlaces(places);

        return read(user.getId());
    }

    @PUT
    @Path("user/current/places")
    @Name("Places Update")
    @Secured
    @Transactional
    public PlacesForm update(final PlacesForm form) {

        User user = service.currentUser();

        final Places places = service.findPlaces(user);
        places.clearAddresses();

        for (AddressForm addressType : form.getAddresses()) {
            Address address = new Address();

            Place place = new Place();
            address.setPlace(place);

            place.setId(addressType.getLocation().getId());
            place.setName(addressType.getLocation().getName());
            place.setCountry(addressType.getLocation().getCountry());
            place.setState(addressType.getLocation().getState());
            place.setStreetNumber(addressType.getLocation().getStreetNumber());
            place.setStreet(addressType.getLocation().getStreet());
            place.setZipCode(addressType.getLocation().getZipCode());
            place.setLat(addressType.getLocation().getLat());
            place.setLng(addressType.getLocation().getLng());

            address.setStart(addressType.getStart());
            address.setEnd(addressType.getEnd());
            address.setTillNow(addressType.isTillNow());


            places.addAddress(address);
        }

        return read(user.getId());
    }

    @DELETE
    @Path("user/current/places")
    @Name("Places Update")
    @Secured
    @Transactional
    public void delete() {
        User user = service.currentUser();

        Places places = service.findPlaces(user);

        service.deletePlaces(places);
    }


    public static URLBuilder<PlacesFormController> linkCreate(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(PlacesFormController.class)
                .record(PlacesFormController::create)
                .rel("create");
    }

    public static URLBuilder<PlacesFormController> linkCurrent(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(PlacesFormController.class)
                .record(PlacesFormController::current)
                .rel("current");
    }

    public static URLBuilder<PlacesFormController> linkRead(Places places, URLBuilderFactory builderFactory) {
        return builderFactory
                .from(PlacesFormController.class)
                .record((method) -> method.read(places.getId()))
                .rel("places");
    }

    public static URLBuilder<PlacesFormController> linkSave(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(PlacesFormController.class)
                .record((method) -> method.save(null))
                .rel("save");
    }

    public static URLBuilder<PlacesFormController> linkUpdate(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(PlacesFormController.class)
                .record((method) -> method.update(null))
                .rel("update");
    }

    public static URLBuilder<PlacesFormController> linkDelete(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(PlacesFormController.class)
                .record(PlacesFormController::delete)
                .rel("delete");
    }

}
