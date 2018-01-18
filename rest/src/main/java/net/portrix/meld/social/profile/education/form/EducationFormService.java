package net.portrix.meld.social.profile.education.form;

import net.portrix.meld.social.profile.Education;
import net.portrix.meld.social.profile.School;
import net.portrix.meld.usercontrol.User;
import net.portrix.meld.usercontrol.UserManager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class EducationFormService {

    private final EntityManager entityManager;

    private final UserManager userManager;

    @Inject
    public EducationFormService(EntityManager entityManager, UserManager userManager) {
        this.entityManager = entityManager;
        this.userManager = userManager;
    }

    public EducationFormService() {
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
        education.setUser(userManager.current());
        entityManager.persist(education);
    }

    public User findUser(UUID id) {
        return entityManager.find(User.class, id);
    }

    public User currentUser() {
        return userManager.current();
    }

    public void deleteEducation(Education education) {
        entityManager.remove(education);
    }

    public List<String> findSchoolNames(String name) {
        return entityManager.createNamedQuery("findSchoolNames", String.class)
                .setParameter("name", "%" + name.toLowerCase() + "%")
                .setMaxResults(5)
                .getResultList();
    }

    public void removeSchool(School school) {
        entityManager.remove(school);
    }
}
