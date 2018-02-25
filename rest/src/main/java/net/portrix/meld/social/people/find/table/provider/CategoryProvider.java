package net.portrix.meld.social.people.find.table.provider;

import net.portrix.generic.rest.api.jaxrs.AbstractRestPredicateProvider;
import net.portrix.meld.social.people.Category_;
import net.portrix.meld.social.people.RelationShip;
import net.portrix.meld.social.people.RelationShip_;
import net.portrix.meld.usercontrol.User;
import org.picketlink.Identity;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.UUID;

public class CategoryProvider extends AbstractRestPredicateProvider<UUID, User> {
    @Override
    public Predicate build(UUID value, Identity identity, EntityManager entityManager, CriteriaBuilder builder, Root<User> root, CriteriaQuery<?> query) {
        Subquery<User> subquery = query.subquery(User.class);
        Root<RelationShip> subRoot = subquery.from(RelationShip.class);
        subquery.select(subRoot.get(RelationShip_.to)).where(builder.equal(subRoot.get(RelationShip_.category).get(Category_.id), value));
        return root.in(subquery);

    }
}
