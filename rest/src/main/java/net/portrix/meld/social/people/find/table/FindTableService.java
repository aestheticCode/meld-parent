package net.portrix.meld.social.people.find.table;

import net.portrix.meld.social.people.Category;
import net.portrix.meld.social.people.RelationShip;
import net.portrix.meld.social.people.find.table.search.Search;
import net.portrix.meld.social.profile.Profile;
import net.portrix.meld.usercontrol.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class FindTableService {

    private final EntityManager entityManager;

    @Inject
    public FindTableService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public FindTableService() {
        this(null);
    }

    public List<User> find(Search search) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        Expression predicate = search.getExpression().accept(Search.visitorVisit(builder, query, root));
        query.select(root).where(predicate).orderBy(Search.sorting(search.getSorting(), builder, root));
        TypedQuery<User> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(search.getIndex());
        typedQuery.setMaxResults(search.getLimit());
        return typedQuery.getResultList();
    }

    public long count(Search search) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<User> root = query.from(User.class);
        Expression predicate = search.getExpression().accept(Search.visitorVisit(builder, query, root));
        query.select(builder.count(root)).where(predicate);
        TypedQuery<Long> typedQuery = entityManager.createQuery(query);
        return typedQuery.getSingleResult();
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

    public List<User> findUsers(FindTableSearch search) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root)
                .where(FindTables.predicate(search, builder, root, query))
                .orderBy(FindTables.sort(search, builder, root));
        TypedQuery<User> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(search.getIndex());
        typedQuery.setMaxResults(search.getLimit());
        return typedQuery.getResultList();
    }

    public long countUsers(FindTableSearch search) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<User> root = query.from(User.class);
        query.select(builder.count(root))
                .where(FindTables.predicate(search, builder, root, query));
        TypedQuery<Long> typedQuery = entityManager.createQuery(query);
        return typedQuery.getSingleResult();
    }

}
