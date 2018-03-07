package net.portrix.meld.social.profile.places.select.providers;

import net.portrix.generic.rest.api.jaxrs.AbstractRestPredicateProvider;
import net.portrix.meld.social.profile.Address;
import net.portrix.meld.social.profile.Address_;
import net.portrix.meld.social.profile.Place_;
import org.picketlink.Identity;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class NameProvider extends AbstractRestPredicateProvider<String, Address> {
    @Override
    public Predicate build(String value, Identity identity, EntityManager entityManager, CriteriaBuilder builder, Root<Address> root, CriteriaQuery<?> query) {
        return builder.like(builder.lower(root.get(Address_.place).get(Place_.name)), value.toLowerCase() + "%");
    }
}
