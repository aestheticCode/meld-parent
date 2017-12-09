package net.portrix.meld.social.people.category.list.query;

import com.google.common.collect.Iterables;
import net.portrix.meld.social.people.Category_;
import net.portrix.meld.social.people.RelationShip;
import net.portrix.meld.social.people.RelationShip_;
import net.portrix.meld.usercontrol.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Patrick on 18.07.2017.
 */
public class Query {

    private int index;

    private int limit;

    private Predicate predicate = new Noop();

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public Predicate getPredicate() {
        return predicate;
    }

    public void setPredicate(Predicate predicate) {
        this.predicate = predicate;
    }

    public static Predicate.Visitor visitorVisit(CriteriaQuery<?> query, CriteriaBuilder builder, Root<?> root) {
        return new Predicate.Visitor() {
            @Override
            public javax.persistence.criteria.Predicate visit(And and) {
                List<javax.persistence.criteria.Predicate> predicates = new ArrayList<>();
                for (Predicate<?> predicate : and.getValue()) {
                    predicates.add(predicate.accept(this));
                }
                return builder.and(Iterables.toArray(predicates, javax.persistence.criteria.Predicate.class));
            }

            @Override
            public javax.persistence.criteria.Predicate visit(Or or) {
                List<javax.persistence.criteria.Predicate> predicates = new ArrayList<>();
                for (Predicate<?> predicate : or.getValue()) {
                    predicates.add(predicate.accept(this));
                }
                return builder.or(Iterables.toArray(predicates, javax.persistence.criteria.Predicate.class));
            }

            @Override
            public javax.persistence.criteria.Predicate visit(Like like) {
                if (like.getValue() == null) {
                    return builder.conjunction();
                } else {
                    return builder.like(builder.lower(root.get(like.getPath())), like.getValue().toLowerCase() + "%");

                }
            }

            @Override
            public javax.persistence.criteria.Predicate visit(In in) {
                if (in.getValue() == null || in.getValue().isEmpty()) {
                    return builder.disjunction();
                } else {
                    return root.get(in.getPath()).in(in.getValue());
                }

            }

            @Override
            public javax.persistence.criteria.Predicate visit(Noop noop) {
                return builder.conjunction();
            }

            @Override
            public javax.persistence.criteria.Predicate visit(Date restDate) {
                List<javax.persistence.criteria.Predicate> predicates = new ArrayList<>();

                if (restDate.getValue().getLt() != null) {
                    predicates.add(builder.lessThanOrEqualTo(root.get(restDate.getPath()), restDate.getValue().getLt()));
                }

                if (restDate.getValue().getGt() != null) {
                    predicates.add(builder.greaterThanOrEqualTo(root.get(restDate.getPath()), restDate.getValue().getGt()));
                }

                switch (predicates.size()) {
                    case 1:
                        return predicates.get(0);
                    case 2:
                        return builder.and(Iterables.toArray(predicates, javax.persistence.criteria.Predicate.class));
                    default:
                        return builder.conjunction();
                }
            }

            @Override
            public javax.persistence.criteria.Predicate visit(Equal equal) {
                Subquery<User> subquery = query.subquery(User.class);
                Root<RelationShip> from = subquery.from(RelationShip.class);
                javax.persistence.criteria.Predicate predicate = builder.equal(from.get(RelationShip_.category).get(Category_.id), equal.getValue());
                subquery.select(from.get(RelationShip_.to)).where(predicate);
                return root.in(subquery);
            }
        };

    }
}
