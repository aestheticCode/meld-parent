package net.portrix.meld.social.workhistory;

import net.portrix.meld.social.WorkHistory;
import net.portrix.meld.usercontrol.User;
import net.portrix.meld.usercontrol.UserManager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.UUID;

@ApplicationScoped
public class WorkHistoryService {

    private final UserManager userManager;

    private final EntityManager entityManager;

    @Inject
    public WorkHistoryService(UserManager userManager, EntityManager entityManager) {
        this.userManager = userManager;
        this.entityManager = entityManager;
    }

    public WorkHistoryService() {
        this(null, null);
    }

    public User findUser(UUID id) {
        return entityManager.find(User.class, id);
    }

    public WorkHistory findWorkHistory(User user) {
        return entityManager.createNamedQuery("findWorkHistory", WorkHistory.class)
                .setParameter("user", user)
                .getSingleResult();
    }

    public void saveWorkHistory(WorkHistory workHistory) {
        entityManager.persist(workHistory);
    }

    public User currentUser() {
        return userManager.current();
    }
}
