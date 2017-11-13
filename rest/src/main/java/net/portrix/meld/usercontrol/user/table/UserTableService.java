package net.portrix.meld.usercontrol.user.table;

import net.portrix.meld.usercontrol.User;
import net.portrix.meld.usercontrol.User_;
import net.portrix.meld.usercontrol.user.table.query.Query;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@ApplicationScoped
public class UserTableService {

    private final EntityManager entityManager;

    @Inject
    public UserTableService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public UserTableService() {
        this(null);
    }


    public List<User> findUsers(Query search) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        Predicate predicate = search.getPredicate().accept(Query.visitorVisit(builder, root));
        query.select(root).where(predicate).orderBy(builder.asc(root.get(User_.name)));
        TypedQuery<User> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(search.getIndex());
        typedQuery.setMaxResults(search.getLimit());
        return typedQuery.getResultList();
    }

    public long countUsers(Query search) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<User> root = query.from(User.class);
        Predicate predicate = search.getPredicate().accept( Query.visitorVisit(builder, root));
        query.select(builder.count(root)).where(predicate);
        TypedQuery<Long> typedQuery = entityManager.createQuery(query);
        return typedQuery.getSingleResult();

    }

}
