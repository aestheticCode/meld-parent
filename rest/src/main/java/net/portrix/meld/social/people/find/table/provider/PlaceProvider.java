package net.portrix.meld.social.people.find.table.provider;

import net.portrix.generic.rest.api.jaxrs.AbstractRestPredicateProvider;
import net.portrix.meld.social.profile.*;
import net.portrix.meld.usercontrol.User;
import org.picketlink.Identity;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.UUID;

public class PlaceProvider extends AbstractRestPredicateProvider<UUID, User> {
    @Override
    public Predicate build(UUID value, Identity identity, EntityManager entityManager, CriteriaBuilder builder, Root<User> root, CriteriaQuery<?> query) {
        Address address = entityManager.createNamedQuery("findAddress", Address.class)
                .setParameter("id", value)
                .getSingleResult();

        Subquery<User> subquery = query.subquery(User.class);
        Root<Address> subRoot = subquery.from(Address.class);
        Join<Address, Places> join = subRoot.join(Address_.places);
        subquery.select(join.get(Places_.user)).where(builder.and(
                builder.greaterThan(subRoot.get(Address_.place).get(Place_.lng), address.getPlace().getLng() - 10),
                builder.greaterThan(subRoot.get(Address_.place).get(Place_.lat), address.getPlace().getLat() - 10),
                builder.lessThan(subRoot.get(Address_.place).get(Place_.lng), address.getPlace().getLng() + 10),
                builder.lessThan(subRoot.get(Address_.place).get(Place_.lat), address.getPlace().getLat() + 10)
        ));
        return root.in(subquery);
    }

}
