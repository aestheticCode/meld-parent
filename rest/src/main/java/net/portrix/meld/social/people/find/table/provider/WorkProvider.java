package net.portrix.meld.social.people.find.table.provider;

import net.portrix.generic.rest.api.jaxrs.AbstractRestPredicateProvider;
import net.portrix.meld.social.profile.*;
import net.portrix.meld.usercontrol.User;
import org.picketlink.Identity;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.UUID;

public class WorkProvider extends AbstractRestPredicateProvider<UUID, User> {
    @Override
    public Predicate build(UUID value, Identity identity, EntityManager entityManager, CriteriaBuilder builder, Root<User> root, CriteriaQuery<?> query) {
        Subquery<User> subquery = query.subquery(User.class);
        Root<Company> subRoot = subquery.from(Company.class);
        Join<Company, WorkHistory> join = subRoot.join(Company_.history);

        subquery.select(join.get(WorkHistory_.user)).where(builder.and(
                subRoot.get(Company_.place).get(Place_.street).in(workStreetSubQuery(value, builder, query)),
                subRoot.get(Company_.place).get(Place_.streetNumber).in(workStreetNumberSubQuery(value, builder, query)),
                subRoot.get(Company_.place).get(Place_.zipCode).in(workZipCodeSubQuery(value, builder, query)),
                subRoot.get(Company_.place).get(Place_.state).in(workStateSubQuery(value, builder, query))
        ));

        return root.in(subquery);    }

    private Subquery<String> workStreetSubQuery(UUID id, CriteriaBuilder builder, CriteriaQuery<?> query) {
        Subquery<String> subquery = query.subquery(String.class);
        Root<Company> subRoot = subquery.from(Company.class);
        subquery.select(subRoot.get(Company_.place).get(Place_.street)).where(
                builder.equal(subRoot.get(Company_.id), id)
        );
        return subquery;
    }

    private Subquery<String> workStreetNumberSubQuery(UUID id, CriteriaBuilder builder, CriteriaQuery<?> query) {
        Subquery<String> subquery = query.subquery(String.class);
        Root<Company> subRoot = subquery.from(Company.class);
        subquery.select(subRoot.get(Company_.place).get(Place_.streetNumber)).where(
                builder.equal(subRoot.get(Company_.id), id)
        );
        return subquery;
    }

    private Subquery<String> workZipCodeSubQuery(UUID id, CriteriaBuilder builder, CriteriaQuery<?> query) {
        Subquery<String> subquery = query.subquery(String.class);
        Root<Company> subRoot = subquery.from(Company.class);
        subquery.select(subRoot.get(Company_.place).get(Place_.zipCode)).where(
                builder.equal(subRoot.get(Company_.id), id)
        );
        return subquery;
    }

    private Subquery<String> workStateSubQuery(UUID id, CriteriaBuilder builder, CriteriaQuery<?> query) {
        Subquery<String> subquery = query.subquery(String.class);
        Root<Company> subRoot = subquery.from(Company.class);
        subquery.select(subRoot.get(Company_.place).get(Place_.state)).where(
                builder.equal(subRoot.get(Company_.id), id)
        );
        return subquery;
    }

}
