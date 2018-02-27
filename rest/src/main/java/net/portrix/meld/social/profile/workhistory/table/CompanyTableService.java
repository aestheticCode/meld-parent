package net.portrix.meld.social.profile.workhistory.table;

import net.portrix.generic.ddd.AbstractSearchService;
import net.portrix.meld.social.profile.*;
import net.portrix.meld.usercontrol.User;
import org.picketlink.Identity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.*;

@ApplicationScoped
public class CompanyTableService extends AbstractSearchService<Company, CompanySearch> {

    @Inject
    public CompanyTableService(EntityManager entityManager, Identity identity) {
        super(entityManager, identity);
    }

    public CompanyTableService() {
        this(null, null);
    }

    @Override
    public Predicate filter(CriteriaBuilder builder, Root<Company> root, CriteriaQuery<?> query) {
        final User user = entityManager.createNamedQuery("findUserByExternal", User.class)
                .setParameter("id", identity.getAccount().getId())
                .getSingleResult();

        Root<Company> from = query.from(Company.class);
        Join<Company, WorkHistory> join = from.join(Company_.history);

        return builder.equal(join.get(WorkHistory_.user), user);

    }
}
