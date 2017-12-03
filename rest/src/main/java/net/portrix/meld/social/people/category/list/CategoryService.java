package net.portrix.meld.social.people.category.list;

import net.portrix.meld.social.people.Category;
import net.portrix.meld.social.people.RelationShip;
import net.portrix.meld.usercontrol.User;
import net.portrix.meld.usercontrol.UserManager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

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

    public User currentUser() {
        return userManager.current();
    }

    public void save(Category category) {
        entityManager.persist(category);
    }

    public Category find(UUID id) {
        return entityManager.find(Category.class, id);
    }

    public void remove(Category category) {
        entityManager.remove(category);
    }

    public List<RelationShip> find(Category category) {
        return entityManager.createQuery("select r from RelationShip r where r.category = :category", RelationShip.class)
                .setParameter("category", category)
                .getResultList();
    }

    public void remove(RelationShip relationShip) {
        entityManager.remove(relationShip);
    }
}
