package net.portrix.meld.social.places;

import net.portrix.meld.social.Places;
import net.portrix.meld.usercontrol.User;
import net.portrix.meld.usercontrol.UserManager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.UUID;

@ApplicationScoped
public class PlacesService {

    private final UserManager userManager;

    private final EntityManager entityManager;

    @Inject
    public PlacesService(UserManager userManager, EntityManager entityManager) {
        this.userManager = userManager;
        this.entityManager = entityManager;
    }

    public PlacesService() {
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
}
