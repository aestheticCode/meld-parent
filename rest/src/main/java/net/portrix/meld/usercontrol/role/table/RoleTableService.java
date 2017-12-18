package net.portrix.meld.usercontrol.role.table;

import net.portrix.generic.ddd.AbstractQueryService;
import net.portrix.meld.usercontrol.Role;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.Map;

@ApplicationScoped
public class RoleTableService extends AbstractQueryService<Role> {

    @Inject
    public RoleTableService(EntityManager entityManager) {
        super(entityManager);
    }

    public RoleTableService() {
        this(null);
    }

    @Override
    public Class<Role> getEntityClass() {
        return Role.class;
    }

    @Override
    public Map<String, Class<?>> getTables() {
        return Collections.emptyMap();
    }
}
