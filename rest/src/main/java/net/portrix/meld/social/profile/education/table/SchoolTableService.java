package net.portrix.meld.social.profile.education.table;

import net.portrix.generic.ddd.AbstractSearchService;
import net.portrix.meld.social.profile.School;
import org.picketlink.Identity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class SchoolTableService extends AbstractSearchService<School, SchoolSearch> {

    @Inject
    public SchoolTableService(EntityManager entityManager, Identity identity) {
        super(entityManager, identity);
    }

    public SchoolTableService() {
        this(null, null);
    }

}
