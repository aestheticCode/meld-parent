package net.portrix.meld.usercontrol.role.multiselect;

import net.portrix.generic.ddd.AbstractQueryService;
import net.portrix.meld.usercontrol.Role;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.Map;

@ApplicationScoped
public class RoleMultiSelectService extends AbstractQueryService<Role> {

    @Inject
    public RoleMultiSelectService(EntityManager entityManager) {
        super(entityManager);
    }

    public RoleMultiSelectService() {
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
