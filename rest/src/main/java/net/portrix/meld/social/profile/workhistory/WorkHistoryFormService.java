package net.portrix.meld.social.profile.workhistory;

import net.portrix.meld.social.profile.Company;
import net.portrix.meld.social.profile.WorkHistory;
import net.portrix.meld.usercontrol.User;
import net.portrix.meld.usercontrol.UserManager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class WorkHistoryFormService {

    private final UserManager userManager;

    private final EntityManager entityManager;

    @Inject
    public WorkHistoryFormService(UserManager userManager, EntityManager entityManager) {
        this.userManager = userManager;
        this.entityManager = entityManager;
    }

    public WorkHistoryFormService() {
        this(null, null);
    }

    public User findUser(UUID id) {
        return entityManager.find(User.class, id);
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

    public void saveWorkHistory(WorkHistory workHistory) {
        entityManager.persist(workHistory);
    }

    public User currentUser() {
        return userManager.current();
    }

    public void deleteWorkHistory(WorkHistory workHistory) {
        entityManager.remove(workHistory);
    }

    public List<String> findCompanyNames(String name) {
        return entityManager.createNamedQuery("findCompanyNameByName", String.class)
                .setParameter("name", "%" + name.toLowerCase() + "%")
                .setMaxResults(5)
                .getResultList();
    }
}
