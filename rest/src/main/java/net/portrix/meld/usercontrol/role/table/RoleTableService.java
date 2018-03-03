package net.portrix.meld.usercontrol.role.table;

import net.portrix.generic.ddd.AbstractSearchService;
import net.portrix.meld.usercontrol.Role;
import org.picketlink.Identity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class RoleTableService extends AbstractSearchService<Role, RoleSearch> {

    @Inject
    public RoleTableService(EntityManager entityManager, Identity identity) {
        super(entityManager, identity);
    }

    public RoleTableService() {
        this(null, null);
    }

}
