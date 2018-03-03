package net.portrix.meld.usercontrol.user.table;

import net.portrix.generic.ddd.AbstractSearchService;
import net.portrix.meld.usercontrol.User;
import org.picketlink.Identity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class UserTableService extends AbstractSearchService<User, UserSearch> {

    @Inject
    public UserTableService(EntityManager entityManager, Identity identity) {
        super(entityManager, identity);
    }

    public UserTableService() {
        this(null, null);
    }
}
