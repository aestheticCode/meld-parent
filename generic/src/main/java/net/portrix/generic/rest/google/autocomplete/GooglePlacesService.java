package net.portrix.generic.rest.google.autocomplete;

import net.portrix.generic.rest.google.GoogleResource;
import net.portrix.generic.rest.google.autocomplete.client.GooglePlacesAutocomplete;
import net.portrix.generic.rest.google.autocomplete.client.PlacePredictions;
import org.apache.commons.lang.StringUtils;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.util.Locale;

@ApplicationScoped
public class GooglePlacesService {

    private final static Logger log = LoggerFactory.getLogger(GooglePlacesService.class);

    public PlacePredictions find(String location, Locale locale) {
        if (StringUtils.isEmpty(location)) {
            return null;
        }
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("https://maps.googleapis.com/maps/api/place/queryautocomplete");
        ResteasyWebTarget webTarget = (ResteasyWebTarget) target;

        GooglePlacesAutocomplete service = webTarget.proxy(GooglePlacesAutocomplete.class);

        return service.execute(GoogleResource.API_KEY, location, locale.getLanguage());
    }

}
