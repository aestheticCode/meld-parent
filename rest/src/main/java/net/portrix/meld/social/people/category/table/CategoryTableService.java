package net.portrix.meld.social.people.category.table;

import net.portrix.generic.ddd.AbstractSearchService;
import net.portrix.meld.social.people.Category;
import net.portrix.meld.social.people.Category_;
import net.portrix.meld.usercontrol.User;
import org.picketlink.Identity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@ApplicationScoped
public class CategoryTableService extends AbstractSearchService<Category, CategorySearch> {

    @Inject
    public CategoryTableService(EntityManager entityManager, Identity identity) {
        super(entityManager, identity);
    }

    public CategoryTableService() {
        this(null, null);
    }

    @Override
    public Predicate filter(CriteriaBuilder builder, Root<Category> root, CriteriaQuery<?> query) {
        User user = entityManager.createNamedQuery("findUserByExternal", User.class)
                .setParameter("id", identity.getAccount().getId())
                .getSingleResult();

        return builder.equal(root.get(Category_.user), user);
    }
}
