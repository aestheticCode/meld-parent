package net.portrix.meld.social.profile.places.select;

import net.portrix.generic.ddd.AbstractSearchService;
import net.portrix.meld.social.profile.Address;
import net.portrix.meld.social.profile.Address_;
import net.portrix.meld.social.profile.Places;
import net.portrix.meld.social.profile.Places_;
import net.portrix.meld.usercontrol.User;
import org.picketlink.Identity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.*;

@ApplicationScoped
public class AddressSelectService extends AbstractSearchService<Address, AddressSearch> {

    @Inject
    public AddressSelectService(EntityManager entityManager, Identity identity) {
        super(entityManager, identity);
    }

    public AddressSelectService() {
        this(null, null);
    }

    @Override
    public Predicate filter(CriteriaBuilder builder, Root<Address> root, CriteriaQuery<?> query) {
        final User user = entityManager.createNamedQuery("findUserByExternal", User.class)
                .setParameter("id", identity.getAccount().getId())
                .getSingleResult();

        Root<Address> from = query.from(Address.class);
        Join<Address, Places> join = from.join(Address_.places);

        return builder.equal(join.get(Places_.user), user);
    }
}
