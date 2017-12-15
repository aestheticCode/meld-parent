package net.portrix.generic.rest.google;

import com.google.maps.GeoApiContext;
import com.googlecode.placesapiclient.client.argument.ArgumentMap;
import com.googlecode.placesapiclient.client.entity.PlacePrediction;
import com.googlecode.placesapiclient.client.exception.ErrorCodeException;
import com.googlecode.placesapiclient.client.service.impl.PlacesServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class PlacesService {

    private final static Logger log = LoggerFactory.getLogger(PlacesService.class);

    private GeoApiContext geoApiContext;
    private PlacesServiceImpl placesService;

    @PostConstruct
    public void init() {
        geoApiContext = new GeoApiContext.Builder()
                .apiKey(GoogleResource.API_KEY)
                //  .enterpriseCredentials(CLIENT_ID, CLIENT_SECRET)
                .build();

        placesService = new PlacesServiceImpl(GoogleResource.API_KEY);
        placesService.init();
    }


    public List<PlacePrediction> find(String location) {
        if (StringUtils.isEmpty(location)) {
            return null;
        }
        ArgumentMap argumentMap = new ArgumentMap(GoogleResource.API_KEY);
        argumentMap.putInput(location.replace(" ", "%20"));
        try {
            return placesService.placeAutocompleteRequest(argumentMap);
        } catch (ErrorCodeException e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

}
