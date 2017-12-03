package net.portrix.meld.social.people.find.table;

import net.portrix.meld.social.people.RelationShip;
import net.portrix.meld.social.people.find.table.query.Query;
import net.portrix.meld.usercontrol.User;
import net.portrix.meld.usercontrol.UserImage;
import net.portrix.meld.usercontrol.User_;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

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

    public List<User> findUsers(Query search) {
        List<User> Users;CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        Predicate predicate = search.getPredicate().accept(Query.visitorVisit(query, builder, root));
        query.select(root).where(predicate).orderBy(builder.asc(root.get(User_.name)));
        TypedQuery<User> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(search.getIndex());
        typedQuery.setMaxResults(search.getLimit());
        Users = typedQuery.getResultList();
        return Users;
    }

    public long countUsers(Query search) {
        long count;CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<User> root = query.from(User.class);
        Predicate predicate = search.getPredicate().accept( Query.visitorVisit(query, builder, root));
        query.select(builder.count(root)).where(predicate);
        TypedQuery<Long> typedQuery = entityManager.createQuery(query);
        count = typedQuery.getSingleResult();
        return count;
    }

    public UserImage findImage(User user) {
        return entityManager.createQuery("select i from UserImage i where i.user = :user", UserImage.class)
                .setParameter("user", user)
                .getSingleResult();
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
}
