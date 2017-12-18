package net.portrix.generic.ddd;

import com.google.common.collect.Maps;
import net.portrix.generic.rest.api.query.Query;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

public abstract class AbstractQueryService<E> {

    protected final EntityManager entityManager;

    public AbstractQueryService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public abstract Class<E> getEntityClass();

    public abstract Map<String, Class<?>> getTables();

    public List<E> find(Query search) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> query = builder.createQuery(getEntityClass());
        Root<E> root = query.from(getEntityClass());
        Expression predicate = search.getPredicate().accept(Query.visitorVisit(query, builder, entityManager, root, getTables()));
        query.select(root).where(predicate).orderBy(Query.sorting(search.getSorting(), builder, root));
        TypedQuery<E> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(search.getIndex());
        typedQuery.setMaxResults(search.getLimit());
        return typedQuery.getResultList();
    }

    public long count(Query search) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<E> root = query.from(getEntityClass());
        Expression predicate = search.getPredicate().accept( Query.visitorVisit(query, builder, entityManager, root, getTables()));
        query.select(builder.count(root)).where(predicate);
        TypedQuery<Long> typedQuery = entityManager.createQuery(query);
        return typedQuery.getSingleResult();
    }


}
