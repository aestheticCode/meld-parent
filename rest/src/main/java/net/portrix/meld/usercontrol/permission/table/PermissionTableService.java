package net.portrix.meld.usercontrol.permission.table;

import net.portrix.generic.ddd.AbstractQueryService;
import net.portrix.meld.usercontrol.Permission;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.Map;

@ApplicationScoped
public class PermissionTableService extends AbstractQueryService<Permission> {

    @Inject
    public PermissionTableService(EntityManager entityManager) {
        super(entityManager);
    }

    public PermissionTableService() {
        this(null);
    }


    @Override
    public Class<Permission> getEntityClass() {
        return Permission.class;
    }

    @Override
    public Map<String, Class<?>> getTables() {
        return Collections.emptyMap();
    }
}
