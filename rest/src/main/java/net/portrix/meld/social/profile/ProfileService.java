package net.portrix.meld.social.profile;

import net.portrix.meld.media.photos.Photo;
import net.portrix.meld.social.people.RelationShip;
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

    public User findUser(UUID id) {
        return userManager.find(id);
    }

    public PersonalContact findContact(User user) {
        try {
            return entityManager.createNamedQuery("findPersonalContact", PersonalContact.class)
                    .setParameter("user", user)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Education findEducation(User user) {
        try {
            return entityManager.createNamedQuery("findEducation", Education.class)
                    .setParameter("user", user)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
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

    public WorkHistory findWorkHistory(User user) {
        try {
            return entityManager.createNamedQuery("findWorkHistory", WorkHistory.class)
                    .setParameter("user", user)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public RelationShip findRelation(User currentUser, User user) {
        return entityManager.createNamedQuery("findRelationByFromUserAndToUser", RelationShip.class)
                .setParameter("from", currentUser)
                .setParameter("to", user)
                .getSingleResult();
    }
}
