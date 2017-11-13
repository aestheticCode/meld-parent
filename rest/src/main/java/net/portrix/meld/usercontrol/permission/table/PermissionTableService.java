package net.portrix.meld.usercontrol.permission.table;

import net.portrix.generic.rest.api.query.Query;
import net.portrix.meld.usercontrol.Permission;
import net.portrix.meld.usercontrol.Permission_;

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
public class PermissionTableService {

    private final EntityManager entityManager;

    @Inject
    public PermissionTableService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public PermissionTableService() {
        this(null);
    }

    public List<Permission> find(Query search) {
        List<Permission> permissions;CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Permission> query = builder.createQuery(Permission.class);
        Root<Permission> root = query.from(Permission.class);
        Predicate predicate = search.getPredicate().accept(Query.visitorVisit(builder, root));
        query.select(root).where(predicate).orderBy(builder.asc(root.get(Permission_.name)));
        TypedQuery<Permission> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(search.getIndex());
        typedQuery.setMaxResults(search.getLimit());
        permissions = typedQuery.getResultList();
        return permissions;
    }

    public long count(Query search) {
        long count;CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Permission> root = query.from(Permission.class);
        Predicate predicate = search.getPredicate().accept( Query.visitorVisit(builder, root));
        query.select(builder.count(root)).where(predicate);
        TypedQuery<Long> typedQuery = entityManager.createQuery(query);
        count = typedQuery.getSingleResult();
        return count;
    }

}
