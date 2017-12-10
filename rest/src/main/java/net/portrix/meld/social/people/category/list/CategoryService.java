package net.portrix.meld.social.people.category.list;

import net.portrix.meld.social.people.Category;
import net.portrix.meld.social.people.Category_;
import net.portrix.meld.social.people.RelationShip;
import net.portrix.meld.social.people.category.list.query.Query;
import net.portrix.meld.usercontrol.User;
import net.portrix.meld.usercontrol.UserManager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CategoryService {

    private final EntityManager entityManager;

    private final UserManager userManager;

    @Inject
    public CategoryService(EntityManager entityManager, UserManager userManager) {
        this.entityManager = entityManager;
        this.userManager = userManager;
    }

    public CategoryService() {
        this(null, null);
    }

    public List<Category> findAll(Query search) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Category> query = builder.createQuery(Category.class);
        Root<Category> root = query.from(Category.class);
        Predicate predicate = search.getPredicate().accept(Query.visitorVisit(query, builder, root));
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
        Predicate predicate = search.getPredicate().accept(Query.visitorVisit(query, builder, root));
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
