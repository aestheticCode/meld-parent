package net.portrix.meld.channel.meld.list.provider;

import net.portrix.generic.rest.api.jaxrs.AbstractRestPredicateProvider;
import net.portrix.meld.channel.MeldPost;
import net.portrix.meld.channel.MeldPost_;
import net.portrix.meld.usercontrol.User;
import net.portrix.meld.usercontrol.User_;
import org.picketlink.Identity;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.UUID;

public class ProfileProvider extends AbstractRestPredicateProvider<UUID, MeldPost> {

    @Override
    public Predicate build(UUID value, Identity identity, EntityManager entityManager, CriteriaBuilder builder, Root<MeldPost> root, CriteriaQuery<?> query) {
        if (value != null) {
            return builder.equal(root.get(MeldPost_.user).get(User_.id), value);

        } else {
            return builder.conjunction();
        }
    }
}
