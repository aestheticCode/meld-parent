package net.portrix.meld.media.photos.grid;

import com.google.common.collect.Maps;
import net.portrix.generic.rest.api.query.Query;
import net.portrix.meld.media.photos.Photo;
import net.portrix.meld.media.photos.Photo_;
import net.portrix.meld.usercontrol.UserManager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@ApplicationScoped
public class PhotoGridService {

    private final EntityManager entityManager;

    private final UserManager userManager;

    @Inject
    public PhotoGridService(EntityManager entityManager, UserManager userManager) {
        this.entityManager = entityManager;
        this.userManager = userManager;
    }

    public PhotoGridService() {
        this(null, null);
    }

    public List<Photo> findAll(Query search) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Photo> query = builder.createQuery(Photo.class);
        Root<Photo> root = query.from(Photo.class);
        Expression<Boolean> predicate = search.getPredicate().accept(Query.visitorVisit(query, builder, entityManager, root, Maps.newHashMap()));
        Predicate userEqual = builder.equal(root.get(Photo_.user), userManager.current());
        Predicate and = builder.and(predicate, userEqual);
        query.select(root).where(and).orderBy(builder.asc(root.get(Photo_.lastModified)));
        TypedQuery<Photo> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(search.getIndex());
        typedQuery.setMaxResults(search.getLimit());
        return typedQuery.getResultList();
    }

    public long count(Query search) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Photo> root = query.from(Photo.class);
        Expression<Boolean> predicate = search.getPredicate().accept(Query.visitorVisit(query, builder, entityManager, root, Maps.newHashMap()));
        Predicate userEqual = builder.equal(root.get(Photo_.user), userManager.current());
        Predicate and = builder.and(predicate, userEqual);
        query.select(builder.count(root)).where(and);
        TypedQuery<Long> typedQuery = entityManager.createQuery(query);
        return typedQuery.getSingleResult();
    }

}
