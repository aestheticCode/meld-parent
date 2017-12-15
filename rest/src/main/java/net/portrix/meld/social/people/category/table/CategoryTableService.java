package net.portrix.meld.social.people.category.table;

import com.google.common.collect.Maps;
import net.portrix.generic.rest.api.query.Query;
import net.portrix.meld.social.people.Category;
import net.portrix.meld.social.people.Category_;
import net.portrix.meld.social.people.RelationShip;
import net.portrix.meld.usercontrol.User;
import net.portrix.meld.usercontrol.UserManager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CategoryTableService {

    private final EntityManager entityManager;

    private final UserManager userManager;

    @Inject
    public CategoryTableService(EntityManager entityManager, UserManager userManager) {
        this.entityManager = entityManager;
        this.userManager = userManager;
    }

    public CategoryTableService() {
        this(null, null);
    }

    public List<Category> findAll(Query search) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Category> query = builder.createQuery(Category.class);
        Root<Category> root = query.from(Category.class);
        Expression predicate = search.getPredicate().accept(Query.visitorVisit(query, builder, entityManager, root, Maps.newHashMap()));
        Predicate userEqual = builder.equal(root.get(Category_.user), userManager.current());
        Predicate and = builder.and(predicate, userEqual);
        query.select(root).where(and).orderBy(builder.asc(root.get(Category_.name)));
        TypedQuery<Category> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(search.getIndex());
        typedQuery.setMaxResults(search.getLimit());
        return typedQuery.getResultList();
    }

    public long count(Query search) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Category> root = query.from(Category.class);
        Expression predicate = search.getPredicate().accept(Query.visitorVisit(query, builder, entityManager , root, Maps.newHashMap()));
        Predicate userEqual = builder.equal(root.get(Category_.user), userManager.current());
        Predicate and = builder.and(predicate, userEqual);
        query.select(builder.count(root)).where(and);
        TypedQuery<Long> typedQuery = entityManager.createQuery(query);
        return typedQuery.getSingleResult();
    }


    public User currentCategory() {
        return userManager.current();
    }

    public void save(Category category) {
        entityManager.persist(category);
    }

    public Category find(UUID id) {
        return entityManager.find(Category.class, id);
    }

    public void remove(Category category) {
        entityManager.remove(category);
    }

    public List<RelationShip> find(Category category) {
        return entityManager.createQuery("select r from RelationShip r where r.category = :category", RelationShip.class)
                .setParameter("category", category)
                .getResultList();
    }

    public void remove(RelationShip relationShip) {
        entityManager.remove(relationShip);
    }

}
