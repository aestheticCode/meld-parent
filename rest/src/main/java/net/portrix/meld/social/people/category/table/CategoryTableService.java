package net.portrix.meld.social.people.category.table;

import com.google.common.collect.Maps;
import net.portrix.generic.ddd.AbstractQueryService;
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
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
public class CategoryTableService extends AbstractQueryService<Category> {

    private final UserManager userManager;

    @Inject
    public CategoryTableService(EntityManager entityManager, UserManager userManager) {
        super(entityManager);
        this.userManager = userManager;
    }

    public CategoryTableService() {
        this(null, null);
    }

    @Override
    public Class<Category> getEntityClass() {
        return Category.class;
    }

    @Override
    public Map<String, Class<?>> getTables() {
        return Maps.newHashMap();
    }
}
