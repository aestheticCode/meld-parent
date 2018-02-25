package net.portrix.meld.social.people.find.table.provider;

import net.portrix.generic.rest.api.jaxrs.AbstractRestPredicateProvider;
import net.portrix.meld.social.people.RelationShip;
import net.portrix.meld.social.people.RelationShip_;
import net.portrix.meld.usercontrol.User;
import org.picketlink.Identity;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;

public class FollowProvider extends AbstractRestPredicateProvider<Boolean, User> {
    @Override
    public Predicate build(Boolean value, Identity identity, EntityManager entityManager, CriteriaBuilder builder, Root<User> root, CriteriaQuery<?> query) {
        if (value) {
            User user = entityManager.createNamedQuery("findUserByExternal", User.class)
                    .setParameter("id", identity.getAccount().getId())
                    .getSingleResult();

            return builder.and(
                    builder.not(
                            root.in(followCurrentUser(user, builder, query))
                    ),
                    root.in(followFollowers(user, builder, query))
            );
        } else {
            return builder.conjunction();
        }
    }

    private Subquery<User> followCurrentUser(User user, CriteriaBuilder builder, CriteriaQuery<?> query) {
        Subquery<User> subquery = query.subquery(User.class);
        Root<RelationShip> subRoot = subquery.from(RelationShip.class);
        return subquery.select(subRoot.get(RelationShip_.to)).where(builder.equal(subRoot.get(RelationShip_.from), user));
    }

    private Subquery<User> followFollowers(User user, CriteriaBuilder builder, CriteriaQuery<?> query) {
        Subquery<User> subquery = query.subquery(User.class);
        Root<RelationShip> subRoot = subquery.from(RelationShip.class);
        return subquery.select(subRoot.get(RelationShip_.from)).where(builder.equal(subRoot.get(RelationShip_.to), user));
    }


}
