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
        Subquery<User> subquery = query.subquery(User.class);
        Root<Address> subRoot = subquery.from(Address.class);
        Join<Address, Places> join = subRoot.join(Address_.places);

        subquery.select(join.get(Places_.user)).where(builder.and(
                subRoot.get(Address_.place).get(Place_.street).in(placeStreetSubQuery(value, builder, query)),
                subRoot.get(Address_.place).get(Place_.streetNumber).in(placeStreetNumberSubQuery(value, builder, query)),
                subRoot.get(Address_.place).get(Place_.zipCode).in(placeZipCodeSubQuery(value, builder, query)),
                subRoot.get(Address_.place).get(Place_.state).in(placeStateSubQuery(value, builder, query))
        ));

        return root.in(subquery);
    }

    private Subquery<String> placeStreetSubQuery(UUID id, CriteriaBuilder builder, CriteriaQuery<?> query) {
        Subquery<String> subquery = query.subquery(String.class);
        Root<Address> subRoot = subquery.from(Address.class);
        subquery.select(subRoot.get(Address_.place).get(Place_.street)).where(
                builder.equal(subRoot.get(Address_.id), id)
        );
        return subquery;
    }

    private Subquery<String> placeStreetNumberSubQuery(UUID id, CriteriaBuilder builder, CriteriaQuery<?> query) {
        Subquery<String> subquery = query.subquery(String.class);
        Root<Address> subRoot = subquery.from(Address.class);
        subquery.select(subRoot.get(Address_.place).get(Place_.streetNumber)).where(
                builder.equal(subRoot.get(Address_.id), id)
        );
        return subquery;
    }

    private Subquery<String> placeZipCodeSubQuery(UUID id, CriteriaBuilder builder, CriteriaQuery<?> query) {
        Subquery<String> subquery = query.subquery(String.class);
        Root<Address> subRoot = subquery.from(Address.class);
        subquery.select(subRoot.get(Address_.place).get(Place_.zipCode)).where(
                builder.equal(subRoot.get(Address_.id), id)
        );
        return subquery;
    }

    private Subquery<String> placeStateSubQuery(UUID id, CriteriaBuilder builder, CriteriaQuery<?> query) {
        Subquery<String> subquery = query.subquery(String.class);
        Root<Address> subRoot = subquery.from(Address.class);
        subquery.select(subRoot.get(Address_.place).get(Place_.state)).where(
                builder.equal(subRoot.get(Address_.id), id)
        );
        return subquery;
    }

}
