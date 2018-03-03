package net.portrix.generic.rest.api.jaxrs.provider;

import net.portrix.generic.rest.api.jaxrs.AbstractRestPredicateProvider;
import org.apache.commons.lang.StringUtils;
import org.picketlink.Identity;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class GenericLikeNameProvider<E> extends AbstractRestPredicateProvider<String, E> {
    @Override
    public Predicate build(String value, Identity identity, EntityManager entityManager, CriteriaBuilder builder, Root<E> root, CriteriaQuery<?> query) {
        if (StringUtils.isEmpty(value)) {
            return builder.conjunction();
        }
        return builder.like(builder.lower(root.get("name")), value.toLowerCase() + "%");
    }
}
