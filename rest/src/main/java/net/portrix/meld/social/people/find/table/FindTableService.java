package net.portrix.meld.social.people.find.table;

import net.portrix.generic.ddd.AbstractSearchService;
import net.portrix.meld.social.people.Category;
import net.portrix.meld.social.people.RelationShip;
import net.portrix.meld.social.profile.*;
import net.portrix.meld.usercontrol.User;
import org.picketlink.Identity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.*;
import java.util.UUID;

@ApplicationScoped
public class FindTableService extends AbstractSearchService<User, FindTableSearch> {

    @Inject
    public FindTableService(EntityManager entityManager, Identity identity) {
        super(entityManager, identity);
    }

    public FindTableService() {
        this(null, null);
    }


    public Profile findProfile(User user) {
        try {
            return entityManager.createQuery("select i from Profile i where i.user = :user", Profile.class)
                    .setParameter("user", user)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
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

    public Category findCategory(UUID id) {
        return entityManager.find(Category.class, id);
    }

    public void save(RelationShip relationShip) {
        entityManager.persist(relationShip);
    }

    public void remove(RelationShip relationShip) {
        entityManager.remove(relationShip);
    }

    @Override
    public Predicate filter(CriteriaBuilder builder, Root<User> root, CriteriaQuery<?> query) {
        final User user = entityManager.createNamedQuery("findUserByExternal", User.class)
                .setParameter("id", identity.getAccount().getId())
                .getSingleResult();

        Root<School> from = query.from(School.class);
        Join<School, Education> join = from.join(School_.education);

        return builder.equal(join.get(Education_.user), user);
    }


}
