package net.portrix.meld.usercontrol.role.multiselect;

import com.google.common.collect.Maps;
import net.portrix.generic.rest.api.query.Query;
import net.portrix.meld.usercontrol.Role;
import net.portrix.meld.usercontrol.Role_;

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
public class RoleMultiSelectService {

    private final EntityManager entityManager;

    @Inject
    public RoleMultiSelectService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public RoleMultiSelectService() {
        this(null);
    }


    public long countRoles(Query search) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Role> root = query.from(Role.class);
        Predicate predicate = search.getPredicate().accept( Query.visitorVisit(query, builder, root, Maps.newHashMap()));
        query.select(builder.count(root)).where(predicate);
        TypedQuery<Long> typedQuery = entityManager.createQuery(query);
        return typedQuery.getSingleResult();
    }

    public List<Role> findRoles(Query search) {
        List<Role> Roles;CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Role> query = builder.createQuery(Role.class);
        Root<Role> root = query.from(Role.class);
        Predicate predicate = search.getPredicate().accept(Query.visitorVisit(query, builder, root, Maps.newHashMap()));
        query.select(root).where(predicate).orderBy(builder.asc(root.get(Role_.name)));
        TypedQuery<Role> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(search.getIndex());
        typedQuery.setMaxResults(search.getLimit());
        Roles = typedQuery.getResultList();
        return Roles;
    }
}
