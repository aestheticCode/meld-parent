package net.portrix.generic.rest.google;

import net.portrix.generic.rest.google.client.GooglePlacesAutocomplete;
import net.portrix.generic.rest.google.client.GooglePlacesDetails;
import net.portrix.generic.rest.google.client.PlaceDetails;
import net.portrix.generic.rest.google.client.PlacePredictions;
import org.apache.commons.lang.StringUtils;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

@ApplicationScoped
public class PlacesService {

    private final static Logger log = LoggerFactory.getLogger(PlacesService.class);


    public PlacePredictions find(String location) {
        if (StringUtils.isEmpty(location)) {
            return null;
        }
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("https://maps.googleapis.com/maps/api/place/queryautocomplete");
        ResteasyWebTarget webTarget = (ResteasyWebTarget) target;

        GooglePlacesAutocomplete service = webTarget.proxy(GooglePlacesAutocomplete.class);

        return service.execute(GoogleResource.API_KEY, location);
    }

    public PlaceDetails findDetails(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("https://maps.googleapis.com/maps/api/place/details");
        ResteasyWebTarget rtarget = (ResteasyWebTarget) target;

        GooglePlacesDetails service = rtarget.proxy(GooglePlacesDetails.class);
        return service.execute(GoogleResource.API_KEY, id).getResult();
    }
}
