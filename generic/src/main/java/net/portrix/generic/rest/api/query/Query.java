package net.portrix.generic.rest.api.query;

import com.google.common.collect.Iterables;

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

    private RestPredicate predicate = new Noop();

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

    public RestPredicate getPredicate() {
        return predicate;
    }

    public void setPredicate(RestPredicate predicate) {
        this.predicate = predicate;
    }

    public static RestPredicate.Visitor visitorVisit(AbstractQuery<?> query, CriteriaBuilder builder, Path<?> root, Map<String, Class<?>> tables) {

        return new RestPredicate.Visitor() {
            @Override
            public Predicate visit(And and) {
                List<Predicate> predicates = new ArrayList<>();
                for (RestPredicate<?> restPredicate : and.getValue()) {
                    predicates.add(restPredicate.accept(this));
                }
                return builder.and(Iterables.toArray(predicates, Predicate.class));
            }

            @Override
            public Predicate visit(Or or) {
                List<Predicate> predicates = new ArrayList<>();
                for (RestPredicate<?> restPredicate : or.getValue()) {
                    predicates.add(restPredicate.accept(this));
                }
                return builder.or(Iterables.toArray(predicates, Predicate.class));
            }

            @Override
            public Predicate visit(Like like) {
                if (like.getValue() == null) {
                    return builder.conjunction();
                } else {
                    return builder.like(builder.lower(cursor(root, like)), like.getValue().toLowerCase() + "%");

                }
            }

            @Override
            public Predicate visit(In in) {
                if (in.getValue() == null || in.getValue().isEmpty()) {
                    return builder.disjunction();
                } else {
                    return cursor(root, in).in(in.getValue());
                }

            }

            @Override
            public Predicate visit(Noop noop) {
                return builder.conjunction();
            }

            @Override
            public Predicate visit(Date restDate) {
                List<Predicate> predicates = new ArrayList<>();

                if (restDate.getValue().getLt() != null) {
                    predicates.add(builder.lessThanOrEqualTo(cursor(root, restDate), restDate.getValue().getLt()));
                }

                if (restDate.getValue().getGt() != null) {
                    predicates.add(builder.greaterThanOrEqualTo(cursor(root, restDate), restDate.getValue().getGt()));
                }

                switch (predicates.size()) {
                    case 1:
                        return predicates.get(0);
                    case 2:
                        return builder.and(Iterables.toArray(predicates, Predicate.class));
                    default:
                        return builder.conjunction();
                }
            }

            @Override
            public Predicate visit(Join join) {
                return join.accept(visitorVisit(query, builder, ((Root<?>) root).join(join.getPath()), tables));
            }

            @Override
            public Predicate visit(SubQuery subQueryPredicate) {
                Class<?> selectClass = tables.get(subQueryPredicate.getSelect());
                Subquery subquery = query.subquery(selectClass);
                Class<?> formClass = tables.get(subQueryPredicate.getFrom());
                Root from = subquery.from(formClass);
                subquery.select(cursor(from, subQueryPredicate)).where(subQueryPredicate.getValue().accept(visitorVisit(subquery, builder, from, tables)));
                return root.in(subquery);
            }

            @Override
            public Predicate visit(Equal equal) {
                return builder.equal(cursor(root, equal), equal.getValue());
            }

        };

    }

    private static Path cursor(Path<?> path, RestPath equal) {
        String[] paths = equal.getPath().split("\\.");
        Path<?> cursor = path;
        for (String segment : paths) {
            cursor = cursor.get(segment);
        }
        return cursor;
    }

}
