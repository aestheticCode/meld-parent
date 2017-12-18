package net.portrix.meld.usercontrol.group.table;

import net.portrix.generic.ddd.AbstractQueryService;
import net.portrix.meld.usercontrol.Group;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.Map;

@ApplicationScoped
public class GroupTableService extends AbstractQueryService<Group> {

    @Inject
    public GroupTableService(EntityManager entityManager) {
        super(entityManager);
    }

    public GroupTableService() {
        this(null);
    }

    @Override
    public Class<Group> getEntityClass() {
        return Group.class;
    }

    @Override
    public Map<String, Class<?>> getTables() {
        return Collections.emptyMap();
    }

}
