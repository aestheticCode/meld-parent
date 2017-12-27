package net.portrix.generic.rest.google.details;

import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilder;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.google.details.client.PlaceDetailForm;
import net.portrix.generic.rest.google.details.client.PlaceDetailsForm;
import net.portrix.generic.rest.jsr339.Name;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("generic/google")
@ApplicationScoped
@Name("Generic Google")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GooglePlacesController {

    private final GooglePlacesService service;

    @Inject
    public GooglePlacesController(GooglePlacesService service) {
        this.service = service;
    }

    public GooglePlacesController() {
        this(null);
    }


    @GET
    @Path("place/{id}/details")
    @Name("Places Details")
    @Secured
    public LocationDetailForm details(@Context HttpServletRequest request, @PathParam("id") String id) {
        PlaceDetailsForm details = service.findDetails(id, request.getLocale());

        String street_number = findWithType("street_number", details.getAddressComponents());
        String street = findWithType("route", details.getAddressComponents());
        String zipCode = findWithType("postal_code", details.getAddressComponents());
        String state = findWithType("locality", details.getAddressComponents());
        String country = findWithType("country", details.getAddressComponents());

        LocationDetailForm form = new LocationDetailForm();

        form.setId(details.getPlaceId());
        form.setName(details.getName());
        form.setStreet(street);
        form.setStreetNumber(street_number);
        form.setZipCode(zipCode);
        form.setState(state);
        form.setCountry(country);

        form.setLat(details.getGeometry().getLocation().getLat());
        form.setLng(details.getGeometry().getLocation().getLng());

        return form;
    }

    private String findWithType(String type, List<PlaceDetailForm> details) {
        for (PlaceDetailForm form : details) {
            if (form.getTypes().contains(type)) {
                return form.getLong_name();
            }
        }
        return null;
    }


    public static URLBuilder<GooglePlacesController> linkGeoCoding(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(GooglePlacesController.class)
                .record((method) -> method.details(null, null))
                .rel("details");
    }

}
