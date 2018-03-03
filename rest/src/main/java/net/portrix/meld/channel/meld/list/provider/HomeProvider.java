package net.portrix.meld.channel.meld.list.provider;

import net.portrix.generic.rest.api.jaxrs.AbstractRestPredicateProvider;
import net.portrix.meld.channel.MeldPost;
import net.portrix.meld.channel.MeldPost_;
import net.portrix.meld.social.people.Category;
import net.portrix.meld.social.people.RelationShip;
import net.portrix.meld.social.people.RelationShip_;
import net.portrix.meld.usercontrol.User;
import org.picketlink.Identity;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;

public class HomeProvider extends AbstractRestPredicateProvider<Boolean, MeldPost> {
    @Override
    public Predicate build(Boolean value, Identity identity, EntityManager entityManager, CriteriaBuilder builder, Root<MeldPost> root, CriteriaQuery<?> query) {

        if (value) {
            User singleResult = entityManager.createNamedQuery("findUserByExternal", User.class)
                    .setParameter("id", identity.getAccount().getId())
                    .getSingleResult();

            return builder.or(
                    builder.equal(root.get(MeldPost_.user), singleResult),
                    builder.and(
                            builder.or(
                                    builder.isNull(root.get(MeldPost_.category)),
                                    root.get(MeldPost_.category).in(privatePost(singleResult, builder, query))
                            ),
                            root.get(MeldPost_.user).in(publicPosts(singleResult, builder, query))
                    )
            );

        } else {
            return builder.conjunction();
        }

    }

    private Subquery<Category> privatePost(User user, CriteriaBuilder builder, CriteriaQuery<?> query) {
        Subquery<Category> subquery = query.subquery(Category.class);
        Root<RelationShip> from = subquery.from(RelationShip.class);
        return subquery.select(from.get(RelationShip_.category))
                .where(builder.equal(from.get(RelationShip_.to), user));
    }

    private Subquery<User> publicPosts(User user, CriteriaBuilder builder, CriteriaQuery<?> query) {
        Subquery<User> subquery = query.subquery(User.class);
        Root<RelationShip> from = subquery.from(RelationShip.class);
        return subquery.select(from.get(RelationShip_.to))
                .where(builder.equal(from.get(RelationShip_.from), user));
    }




}
