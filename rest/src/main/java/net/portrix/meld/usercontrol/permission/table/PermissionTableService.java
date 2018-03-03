package net.portrix.meld.usercontrol.permission.table;

import net.portrix.generic.ddd.AbstractSearchService;
import net.portrix.meld.usercontrol.Permission;
import org.picketlink.Identity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class PermissionTableService extends AbstractSearchService<Permission, PermissionSearch> {

    @Inject
    public PermissionTableService(EntityManager entityManager, Identity identity) {
        super(entityManager, identity);
    }

    public PermissionTableService() {
        this(null, null);
    }
}
