package net.portrix.meld.social.profile.places;

import com.google.maps.GeoApiContext;
import com.googlecode.placesapiclient.client.argument.ArgumentMap;
import com.googlecode.placesapiclient.client.entity.PlacePrediction;
import com.googlecode.placesapiclient.client.exception.ErrorCodeException;
import com.googlecode.placesapiclient.client.service.impl.PlacesServiceImpl;
import net.portrix.meld.GoogleResource;
import net.portrix.meld.social.profile.Places;
import net.portrix.meld.usercontrol.User;
import net.portrix.meld.usercontrol.UserManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class PlacesFormService {

    private final static Logger LOG = LoggerFactory.getLogger(PlacesFormService.class);

    private final UserManager userManager;

    private final EntityManager entityManager;

    private GeoApiContext geoApiContext;
    private PlacesServiceImpl placesService;

    @Inject
    public PlacesFormService(UserManager userManager, EntityManager entityManager) {
        this.userManager = userManager;
        this.entityManager = entityManager;
    }

    @PostConstruct
    public void init() {
        geoApiContext = new GeoApiContext.Builder()
                .apiKey(GoogleResource.API_KEY)
                //  .enterpriseCredentials(CLIENT_ID, CLIENT_SECRET)
                .build();

        placesService = new PlacesServiceImpl(GoogleResource.API_KEY);
        placesService.init();
    }

    public PlacesFormService() {
        this(null, null);
    }

    public User findUser(UUID id) {
        return entityManager.find(User.class, id);
    }

    public Places findPlaces(User user) {
        try {
            return entityManager.createNamedQuery("findPlaces", Places.class)
                    .setParameter("user", user)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void savePlaces(Places places) {
        entityManager.persist(places);
    }

    public User currentUser() {
        return userManager.current();
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
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }
}
