package net.portrix.meld.social.profile.education.select;

import net.portrix.generic.ddd.AbstractSearchService;
import net.portrix.meld.social.profile.School;
import org.picketlink.Identity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class SchoolSelectService extends AbstractSearchService<School, SchoolSearch> {

    @Inject
    public SchoolSelectService(EntityManager entityManager, Identity identity) {
        super(entityManager, identity);
    }

    public SchoolSelectService() {
        this(null, null);
    }

}
