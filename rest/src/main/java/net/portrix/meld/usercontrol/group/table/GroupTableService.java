package net.portrix.meld.usercontrol.group.table;

import com.google.common.collect.Maps;
import net.portrix.generic.rest.api.query.Query;
import net.portrix.meld.usercontrol.Group;
import net.portrix.meld.usercontrol.Group_;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@ApplicationScoped
public class GroupTableService {

    private final EntityManager entityManager;

    @Inject
    public GroupTableService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public GroupTableService() {
        this(null);
    }


    List<Group> find(Query search) {
        List<Group> groups;
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Group> query = builder.createQuery(Group.class);
        Root<Group> root = query.from(Group.class);
        Expression predicate = search.getPredicate().accept(Query.visitorVisit(query, builder, entityManager, root, Maps.newHashMap()));
        query.select(root).where(predicate).orderBy(builder.asc(root.get(Group_.name)));
        TypedQuery<Group> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(search.getIndex());
        typedQuery.setMaxResults(search.getLimit());
        groups = typedQuery.getResultList();
        return groups;
    }

    long count(Query search) {
        long count;
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Group> root = query.from(Group.class);
        Expression predicate = search.getPredicate().accept(Query.visitorVisit(query, builder, entityManager, root, Maps.newHashMap()));
        query.select(builder.count(root)).where(predicate);
        TypedQuery<Long> typedQuery = entityManager.createQuery(query);
        count = typedQuery.getSingleResult();
        return count;
    }
}
