package net.portrix.meld.usercontrol.user.table;

import net.portrix.generic.ddd.AbstractQueryService;
import net.portrix.meld.usercontrol.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.Map;

@ApplicationScoped
public class UserTableService extends AbstractQueryService<User> {

    @Inject
    public UserTableService(EntityManager entityManager) {
        super(entityManager);
    }

    public UserTableService() {
        this(null);
    }

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    public Map<String, Class<?>> getTables() {
        return Collections.emptyMap();
    }

}
