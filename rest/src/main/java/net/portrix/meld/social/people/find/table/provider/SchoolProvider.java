package net.portrix.meld.social.people.find.table.provider;

import net.portrix.generic.rest.api.jaxrs.AbstractRestPredicateProvider;
import net.portrix.meld.social.profile.*;
import net.portrix.meld.usercontrol.User;
import org.picketlink.Identity;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.UUID;

public class SchoolProvider extends AbstractRestPredicateProvider<UUID, User> {
    @Override
    public Predicate build(UUID value, Identity identity, EntityManager entityManager, CriteriaBuilder builder, Root<User> root, CriteriaQuery<?> query) {
        Subquery<User> subquery = query.subquery(User.class);
        Root<School> subRoot = subquery.from(School.class);
        Join<School, Education> subJoin = subRoot.join(School_.education);

        subquery.select(subJoin.get(Education_.user)).where(
                builder.and(
                        subRoot.get(School_.place).get(Place_.street).in(schoolStreetSubQuery(value, builder, query)),
                        subRoot.get(School_.place).get(Place_.streetNumber).in(schoolStreetNumberSubQuery(value, builder, query)),
                        subRoot.get(School_.place).get(Place_.zipCode).in(schoolZipCodeSubQuery(value, builder, query)),
                        subRoot.get(School_.place).get(Place_.state).in(schoolStateSubQuery(value, builder, query))
                )
        );

        return root.in(subquery);
    }

    private Subquery<String> schoolStreetSubQuery(UUID id, CriteriaBuilder builder, CriteriaQuery<?> query) {
        Subquery<String> subquery = query.subquery(String.class);
        Root<School> subRoot = subquery.from(School.class);
        subquery.select(subRoot.get(School_.place).get(Place_.street)).where(
                builder.equal(subRoot.get(School_.id), id)
        );
        return subquery;
    }

    private Subquery<String> schoolStreetNumberSubQuery(UUID id, CriteriaBuilder builder, CriteriaQuery<?> query) {
        Subquery<String> subquery = query.subquery(String.class);
        Root<School> subRoot = subquery.from(School.class);
        subquery.select(subRoot.get(School_.place).get(Place_.streetNumber)).where(
                builder.equal(subRoot.get(School_.id), id)
        );
        return subquery;
    }

    private Subquery<String> schoolZipCodeSubQuery(UUID id, CriteriaBuilder builder, CriteriaQuery<?> query) {
        Subquery<String> subquery = query.subquery(String.class);
        Root<School> subRoot = subquery.from(School.class);
        subquery.select(subRoot.get(School_.place).get(Place_.zipCode)).where(
                builder.equal(subRoot.get(School_.id), id)
        );
        return subquery;
    }

    private Subquery<String> schoolStateSubQuery(UUID id, CriteriaBuilder builder, CriteriaQuery<?> query) {
        Subquery<String> subquery = query.subquery(String.class);
        Root<School> subRoot = subquery.from(School.class);
        subquery.select(subRoot.get(School_.place).get(Place_.state)).where(
                builder.equal(subRoot.get(School_.id), id)
        );
        return subquery;
    }

}
