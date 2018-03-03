package net.portrix.meld.channel.meld.list.provider;

import net.portrix.generic.rest.api.jaxrs.AbstractRestPredicateProvider;
import net.portrix.meld.channel.MeldPost;
import net.portrix.meld.channel.MeldPost_;
import net.portrix.meld.usercontrol.User;
import org.picketlink.Identity;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ProfileProvider extends AbstractRestPredicateProvider<Boolean, MeldPost> {

    @Override
    public Predicate build(Boolean value, Identity identity, EntityManager entityManager, CriteriaBuilder builder, Root<MeldPost> root, CriteriaQuery<?> query) {
        if (value) {
            User singleResult = entityManager.createNamedQuery("findUserByExternal", User.class)
                    .setParameter("id", identity.getAccount().getId())
                    .getSingleResult();

            return builder.equal(root.get(MeldPost_.user), singleResult);

        } else {
            return builder.conjunction();
        }
    }
}
