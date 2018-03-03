package net.portrix.meld.usercontrol.group.multiselect;

import net.portrix.generic.ddd.AbstractSearchService;
import net.portrix.meld.usercontrol.Group;
import org.picketlink.Identity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class GroupMultiSelectService extends AbstractSearchService<Group, GroupSearch> {

    @Inject
    public GroupMultiSelectService(EntityManager entityManager, Identity identity) {
        super(entityManager, identity);
    }

    public GroupMultiSelectService() {
        this(null, null);
    }
}
