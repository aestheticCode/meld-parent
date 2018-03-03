package net.portrix.meld.usercontrol.group.table;

import net.portrix.generic.ddd.AbstractSearchService;
import net.portrix.meld.usercontrol.Group;
import org.picketlink.Identity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class GroupTableService extends AbstractSearchService<Group, GroupSearch> {

    @Inject
    public GroupTableService(EntityManager entityManager, Identity identity) {
        super(entityManager, identity);
    }

    public GroupTableService() {
        this(null, null);
    }

}
