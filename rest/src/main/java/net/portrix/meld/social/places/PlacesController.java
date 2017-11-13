package net.portrix.meld.social.places;

import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.meld.social.Address;
import net.portrix.meld.social.Places;
import net.portrix.meld.usercontrol.User;

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
public class PlacesController {

    private final PlacesService service;

    @Inject
    public PlacesController(PlacesService service) {
        this.service = service;
    }

    public PlacesController() {
        this(null);
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
            return new PlacesForm();
        }

        PlacesForm placesForm = new PlacesForm();

        for (Address address : places.getAddresses()) {
            AddressForm responseType = new AddressForm();
            responseType.setCountry(address.getCountry());
            responseType.setId(address.getId());
            responseType.setState(address.getState());
            responseType.setCity(address.getCity());
            responseType.setStreet(address.getStreet());
            responseType.setZipCode(address.getZipCode());
            address.setStart(responseType.getStart());
            address.setEnd(responseType.getEnd());


            placesForm.addAddress(responseType);
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
            address.setCountry(addressType.getCountry());
            address.setState(addressType.getState());
            address.setCity(addressType.getCity());
            address.setStreet(addressType.getStreet());
            address.setZipCode(addressType.getZipCode());
            address.setStart(addressType.getStart());
            address.setEnd(addressType.getEnd());

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
            address.setCountry(addressType.getCountry());
            address.setState(addressType.getState());
            address.setCity(addressType.getCity());
            address.setStreet(addressType.getStreet());
            address.setZipCode(addressType.getZipCode());
            address.setStart(addressType.getStart());
            address.setEnd(addressType.getEnd());


            places.addAddress(address);
        }

        return read(user.getId());
    }

}
