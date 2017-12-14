package net.portrix.meld.social.profile;

import net.portrix.meld.media.photos.Photo;
import net.portrix.meld.usercontrol.User;
import net.portrix.meld.usercontrol.UserManager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.UUID;

@ApplicationScoped
public class ProfileService {

    private final UserManager userManager;

    private final EntityManager entityManager;

    @Inject
    public ProfileService(UserManager userManager, EntityManager entityManager) {
        this.userManager = userManager;
        this.entityManager = entityManager;
    }

    public ProfileService() {
        this(null, null);
    }

    public User currentUser() {
        return userManager.current();
    }

    public Profile find(User current) {
        try {
            return entityManager.createQuery("select p from Profile p where p.user = :user", Profile.class)
                    .setParameter("user", current)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Photo find(UUID photoId) {
        return entityManager.find(Photo.class, photoId);
    }

    public void save(Profile profile) {
        entityManager.persist(profile);
    }
}
