package net.portrix.meld.social.people;

import net.portrix.meld.usercontrol.User;
import net.portrix.meld.usercontrol.UserManager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class CategoryService {

    private final EntityManager entityManager;

    private final UserManager userManager;

    @Inject
    public CategoryService(EntityManager entityManager, UserManager userManager) {
        this.entityManager = entityManager;
        this.userManager = userManager;
    }

    public CategoryService() {
        this(null, null);
    }

    public List<Category> findAll() {
        User user = userManager.current();

        return entityManager.createQuery("select c from Category c where c.user = :user", Category.class)
                .setParameter("user", user)
                .getResultList();
    }

}
