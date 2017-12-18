package net.portrix.meld.usercontrol.group.multiselect;

import com.google.common.collect.Maps;
import net.portrix.generic.ddd.AbstractQueryService;
import net.portrix.generic.rest.api.query.Query;
import net.portrix.meld.usercontrol.Group;
import net.portrix.meld.usercontrol.Group_;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class GroupMultiSelectService extends AbstractQueryService<Group> {

    @Inject
    public GroupMultiSelectService(EntityManager entityManager) {
        super(entityManager);
    }

    public GroupMultiSelectService() {
        this(null);
    }

    @Override
    public Class<Group> getEntityClass() {
        return Group.class;
    }

    @Override
    public Map<String, Class<?>> getTables() {
        return Maps.newHashMap();
    }
}
