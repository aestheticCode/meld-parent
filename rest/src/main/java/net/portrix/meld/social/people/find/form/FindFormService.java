package net.portrix.meld.social.people.find.form;

import net.portrix.meld.social.people.Category;
import net.portrix.meld.social.people.RelationShip;
import net.portrix.meld.usercontrol.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.UUID;

@ApplicationScoped
public class FindFormService {

    private final EntityManager entityManager;

    @Inject
    public FindFormService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public FindFormService() {
        this(null);
    }

    public Category findCategory(UUID id) {
        return entityManager.find(Category.class, id);
    }

    public void save(RelationShip relationShip) {
        entityManager.persist(relationShip);
    }

    public RelationShip findRelationShip(User current, User user) {
        try {
            return entityManager.createQuery("select r from RelationShip r where r.from = :user1 and r.to = :user2", RelationShip.class)
                    .setParameter("user1", current)
                    .setParameter("user2", user)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void remove(RelationShip relationShip) {
        entityManager.remove(relationShip);
    }
}
