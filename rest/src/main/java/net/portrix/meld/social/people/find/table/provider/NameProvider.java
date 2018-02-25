package net.portrix.meld.social.people.find.table.provider;

import net.portrix.generic.rest.api.jaxrs.AbstractRestPredicateProvider;
import net.portrix.meld.usercontrol.User;
import net.portrix.meld.usercontrol.User_;
import org.picketlink.Identity;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class NameProvider extends AbstractRestPredicateProvider<String, User> {

    @Override
    public Predicate build(String value, Identity identity, EntityManager entityManager, CriteriaBuilder builder, Root<User> root, CriteriaQuery<?> query) {
        return builder.or(
                builder.like(builder.lower(root.get(User_.firstName)), value.toLowerCase() + "%"),
                builder.like(builder.lower(root.get(User_.lastName)), value.toLowerCase() + "%")
        );

    }
}
