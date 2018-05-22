package net.portrix.meld.social.communities.find.form;

import net.portrix.generic.ddd.AbstractFormService;
import net.portrix.meld.social.communities.Community;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class CommunityService extends AbstractFormService<Community> {

    @Inject
    public CommunityService(EntityManager entityManager) {
        super(entityManager);
    }

    public CommunityService() {
        this(null);
    }

}
