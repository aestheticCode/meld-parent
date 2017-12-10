package net.portrix.meld.social.people.find.table.query;

import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import net.portrix.meld.social.people.Category;
import net.portrix.meld.social.people.Category_;
import net.portrix.meld.social.people.RelationShip;
import net.portrix.meld.social.people.RelationShip_;
import net.portrix.meld.usercontrol.User;
import net.portrix.meld.usercontrol.User_;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public static Predicate.Visitor visitorVisit(AbstractQuery<?> query, CriteriaBuilder builder, Path<?> root) {

        final Map<String, Class<?>> tables = Maps.newHashMap();

        tables.put("relationShip", RelationShip.class);

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
            public javax.persistence.criteria.Predicate visit(Join join) {
                return join.accept(visitorVisit(query, builder, ((Root<?>)root).join(join.getPath())));
            }

            @Override
            public javax.persistence.criteria.Predicate visit(SubQuery subQueryPredicate) {
                Subquery<User> subquery = query.subquery(User.class);
                Class<?> formClass = tables.get(subQueryPredicate.getFrom());
                Root from = subquery.from(formClass);
                subquery.select(from.get(subQueryPredicate.getPath())).where(subQueryPredicate.getValue().accept(visitorVisit(subquery, builder, from)));
                return root.in(subquery);
            }

            @Override
            public javax.persistence.criteria.Predicate visit(Equal equal) {
                String[] path = equal.getPath().split("\\.");
                Path<?> cursor = root;
                for (String segment : path) {
                    cursor = cursor.get(segment);
                }
                return builder.equal(cursor, equal.getValue());
            }
        };

    }
}
