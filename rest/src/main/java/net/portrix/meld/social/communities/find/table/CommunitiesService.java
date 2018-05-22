package net.portrix.meld.social.communities.find.table;

import net.portrix.generic.ddd.AbstractSearchService;
import net.portrix.meld.social.communities.Community;
import org.picketlink.Identity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class CommunitiesService extends AbstractSearchService<Community, CommunitySearch> {

    @Inject
    public CommunitiesService(EntityManager entityManager, Identity identity) {
        super(entityManager, identity);
    }

    public CommunitiesService() {
        this(null, null);
    }

}
