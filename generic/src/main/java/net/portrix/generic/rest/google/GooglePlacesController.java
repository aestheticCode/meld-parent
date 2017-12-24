package net.portrix.generic.rest.google;

import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilder;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.api.Container;
import net.portrix.generic.rest.google.client.PlaceDetails;
import net.portrix.generic.rest.google.client.PlacePrediction;
import net.portrix.generic.rest.google.client.PlacePredictions;
import net.portrix.generic.rest.jsr339.Name;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("generic/google")
@ApplicationScoped
@Name("Generic Google")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GooglePlacesController {

    private final PlacesService service;

    @Inject
    public GooglePlacesController(PlacesService service) {
        this.service = service;
    }

    public GooglePlacesController() {
        this(null);
    }

    @POST
    @Path("place/autocomplete")
    @Name("Places Geo coding")
    @Secured
    public Container<LocationForm> geoCoding(LocationForm address) {
        PlacePredictions placePredictions = service.find(address.getName());
        List<LocationForm> forms = new ArrayList<>();
        if (placePredictions == null) {
            return new Container<>(forms, forms.size());
        }

        for (PlacePrediction placePrediction : placePredictions.getPredictions()) {
            LocationForm form = new LocationForm();
            form.setName(placePrediction.getDescription());
            form.setId(placePrediction.getPlace_id());
            forms.add(form);
        }

        return new Container<>(forms, forms.size());
    }

    @GET
    @Path("place/{id}/details")
    @Name("Places Details")
    @Secured
    public PlaceDetails details(@PathParam("id") String id) {
        return service.findDetails(id);
       }


    public static URLBuilder<GooglePlacesController> linkGeoCoding(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(GooglePlacesController.class)
                .record((method) -> method.geoCoding(null))
                .rel("geoCoding");
    }

}