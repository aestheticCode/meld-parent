package net.portrix.meld.usercontrol.group.multiselect;

import net.portrix.generic.rest.api.query.Query;
import net.portrix.meld.usercontrol.Group;
import net.portrix.meld.usercontrol.Group_;

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
public class GroupMultiSelectService {

    private final EntityManager entityManager;

    @Inject
    public GroupMultiSelectService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public GroupMultiSelectService() {
        this(null);
    }

    public List<Group> find(Query search) {
        List<Group> groups;CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Group> query = builder.createQuery(Group.class);
        Root<Group> root = query.from(Group.class);
        Predicate predicate = search.getPredicate().accept(Query.visitorVisit(builder, root));
        query.select(root).where(predicate).orderBy(builder.asc(root.get(Group_.name)));
        TypedQuery<Group> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(search.getIndex());
        typedQuery.setMaxResults(search.getLimit());
        groups = typedQuery.getResultList();
        return groups;
    }

    public long count(Query search) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Group> root = query.from(Group.class);
        Predicate predicate = search.getPredicate().accept( Query.visitorVisit(builder, root));
        query.select(builder.count(root)).where(predicate);
        TypedQuery<Long> typedQuery = entityManager.createQuery(query);
        return typedQuery.getSingleResult();
    }
}
