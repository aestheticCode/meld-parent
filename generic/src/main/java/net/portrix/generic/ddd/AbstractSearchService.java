package net.portrix.generic.ddd;

import com.google.common.reflect.TypeToken;
import net.portrix.generic.rest.api.jaxrs.AbstractRestSearch;
import net.portrix.generic.rest.api.jaxrs.RestSearch;
import org.picketlink.Identity;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public abstract class AbstractSearchService<E, S extends AbstractRestSearch> {

    protected final EntityManager entityManager;

    protected final Identity identity;

    public AbstractSearchService(EntityManager entityManager, Identity identity) {
        this.entityManager = entityManager;
        this.identity = identity;
    }

    public Class<E> getEntityClass() {
        return (Class<E>) TypeToken.of(getClass()).resolveType(AbstractSearchService.class.getTypeParameters()[0]).getRawType();
    }

    public Class<S> getSearchClass() {
        return (Class<S>) TypeToken.of(getClass()).resolveType(AbstractSearchService.class.getTypeParameters()[1]).getRawType();
    }

    public Predicate filter(CriteriaBuilder builder, Root<E> root, CriteriaQuery<?> query) {
        return entityManager.getCriteriaBuilder().conjunction();
    }

    public List<E> find(S search) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> query = builder.createQuery(getEntityClass());
        Root<E> root = query.from(getEntityClass());
        query.select(root)
                .where(builder.and(
                        filter(builder, root, query),
                        RestSearch.predicate(getSearchClass(), search, identity, entityManager, builder, root, query)
                ))
                .orderBy(RestSearch.sort(getSearchClass(), search, entityManager, builder, root));
        TypedQuery<E> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(search.getIndex());
        typedQuery.setMaxResults(search.getLimit());
        return typedQuery.getResultList();
    }

    public long count(S search) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<E> root = query.from(getEntityClass());
        query.select(builder.count(root))
                .where(builder.and(
                        filter(builder, root, query),
                        RestSearch.predicate(getSearchClass(), search, identity, entityManager, builder, root, query)
                ));
        TypedQuery<Long> typedQuery = entityManager.createQuery(query);
        return typedQuery.getSingleResult();
    }

}
