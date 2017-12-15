package net.portrix.generic.rest.google;

import com.googlecode.placesapiclient.client.entity.PlacePrediction;
import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.URLBuilder;
import net.portrix.generic.rest.URLBuilderFactory;
import net.portrix.generic.rest.api.Container;
import net.portrix.generic.rest.jsr339.Name;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("generic/google")
@ApplicationScoped
@Name("Generic Google")
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

    @POST
    @Path("place")
    @Name("Places Geo coding")
    @Secured
    public Container<LocationForm> geoCoding(LocationForm address) {
        List<PlacePrediction> placePredictions = service.find(address.getValue());
        List<LocationForm> forms = new ArrayList<>();
        if (placePredictions == null) {
            return new Container<>(forms, forms.size());
        }

        for (PlacePrediction placePrediction : placePredictions) {
            LocationForm form = new LocationForm();
            form.setValue(placePrediction.getDescription());
            forms.add(form);
        }

        return new Container<>(forms, forms.size());
    }

    public static URLBuilder<PlacesController> linkGeoCoding(URLBuilderFactory builderFactory) {
        return builderFactory
                .from(PlacesController.class)
                .record((method) -> method.geoCoding(null))
                .rel("geoCoding");
    }

}
