package net.portrix.meld.social.profile.education;

import net.portrix.meld.social.profile.Education;
import net.portrix.meld.usercontrol.User;
import net.portrix.meld.usercontrol.UserManager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.UUID;

@ApplicationScoped
public class EducationService {

    private final EntityManager entityManager;

    private final UserManager userManager;

    @Inject
    public EducationService(EntityManager entityManager, UserManager userManager) {
        this.entityManager = entityManager;
        this.userManager = userManager;
    }

    public EducationService() {
        this(null, null);
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

    public void saveEducation(final Education education) {
        entityManager.persist(education);
    }

    public User findUser(UUID id) {
        return entityManager.find(User.class, id);
    }

    public User currentUser() {
        return userManager.current();
    }
}
