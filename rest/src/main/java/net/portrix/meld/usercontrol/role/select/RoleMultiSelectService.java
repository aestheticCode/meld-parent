package net.portrix.meld.usercontrol.role.select;

import net.portrix.generic.ddd.AbstractSearchService;
import net.portrix.meld.usercontrol.Role;
import org.picketlink.Identity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class RoleMultiSelectService extends AbstractSearchService<Role, RoleSearch> {

    @Inject
    public RoleMultiSelectService(EntityManager entityManager, Identity identity) {
        super(entityManager, identity);
    }

    public RoleMultiSelectService() {
        this(null, null);
    }

}
