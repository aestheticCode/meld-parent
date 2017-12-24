package net.portrix.meld.social.profile.places;

import net.portrix.meld.social.profile.Places;
import net.portrix.meld.usercontrol.User;
import net.portrix.meld.usercontrol.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.UUID;

@ApplicationScoped
public class PlacesFormService {

    private final static Logger LOG = LoggerFactory.getLogger(PlacesFormService.class);

    private final UserManager userManager;

    private final EntityManager entityManager;

    @Inject
    public PlacesFormService(UserManager userManager, EntityManager entityManager) {
        this.userManager = userManager;
        this.entityManager = entityManager;
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

    public void deletePlaces(Places places) {
        entityManager.remove(places);
    }
}
