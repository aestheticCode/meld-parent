package net.portrix.generic.rest.api.query;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
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

    private List<Sorting> sorting = new ArrayList<>();

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

    public List<Sorting> getSorting() {
        return sorting;
    }

    public void setSorting(List<Sorting> sorting) {
        this.sorting = sorting;
    }

    public static List<Order> sorting(List<Sorting> sorting, CriteriaBuilder builder, Path<?> root) {
        List<Order> orders = new ArrayList<>();
        for (Sorting sort : sorting) {
            if (sort.getAsc()) {
                orders.add(builder.asc(cursor(root, sort.getPath())));
            } else {
                orders.add(builder.desc(cursor(root, sort.getPath())));
            }
        }
        return orders;
    }

    public static RestPredicate.Visitor visitorVisit(AbstractQuery<?> query, CriteriaBuilder builder, EntityManager entityManager, Path<?> root, List<Order> sorting,  Map<String, Class<?>> tables) {
        return new RestPredicate.Visitor() {
            @Override
            public Expression visit(And and) {
                List<Predicate> predicates = new ArrayList<>();
                for (RestPredicate restPredicate : and.getValue()) {
                    predicates.add((Predicate) restPredicate.accept(this));
                }
                return builder.and(Iterables.toArray(predicates, Predicate.class));
            }

            @Override
            public Expression visit(Or or) {
                List<Predicate> predicates = new ArrayList<>();
                for (RestPredicate restPredicate : or.getValue()) {
                    predicates.add((Predicate) restPredicate.accept(this));
                }
                return builder.or(Iterables.toArray(predicates, Predicate.class));
            }

            @Override
            public Expression visit(Like like) {
                if (StringUtils.isEmpty(like.getValue())) {
                    return builder.conjunction();
                } else {
                    return builder.like(builder.lower(cursor(root, like.getPath())), like.getValue().toLowerCase() + "%");

                }
            }

            @Override
            public Expression visit(In in) {
                if (in.getValue() == null || in.getValue().isEmpty()) {
                    return builder.disjunction();
                } else {
                    return cursor(root, in.getPath()).in(in.getValue());
                }

            }

            @Override
            public Expression visit(Noop noop) {
                return builder.conjunction();
            }

            @Override
            public Expression visit(Date restDate) {
                List<Predicate> predicates = new ArrayList<>();

                if (restDate.getValue().getLt() != null) {
                    predicates.add(builder.lessThanOrEqualTo(cursor(root, restDate.getPath()), restDate.getValue().getLt()));
                }

                if (restDate.getValue().getGt() != null) {
                    predicates.add(builder.greaterThanOrEqualTo(cursor(root, restDate.getPath()), restDate.getValue().getGt()));
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
            public Expression visit(Join join) {
                return join.accept(visitorVisit(query, builder, entityManager, ((Root<?>) root).join(join.getPath()), sorting, tables));
            }

            @Override
            public Expression visit(SubQuery subQueryPredicate) {
                Class<?> fromClass = tables.get(subQueryPredicate.getFrom());

                Metamodel metamodel = entityManager.getMetamodel();
                EntityType<?> entity = metamodel.entity(fromClass);
                EntityType<?> cursor = cursor(metamodel, entity, subQueryPredicate.getPath());

                Subquery subquery = query.subquery(cursor.getJavaType());
                Root from = subquery.from(fromClass);
                subquery.select(cursor(from, subQueryPredicate.getPath())).where(subQueryPredicate.getValue().accept(visitorVisit(subquery, builder, entityManager, from, sorting,tables)));
                return subquery;
            }

            @Override
            public Expression visit(Equal equal) {
                return builder.equal(cursor(root, equal.getPath()), equal.getValue());
            }

            @Override
            public Expression visit(InSelect select) {
                return cursor(root, select.getPath()).in(select.getValue().accept(this));
            }

            @Override
            public Expression visit(IsNull isNull) {
                return builder.isNull(cursor(root, isNull.getPath()));
            }

            @Override
            public Expression visit(Not not) {
                return builder.not(not.getValue().accept(this));
            }

            @Override
            public Expression visit(Levensthein levensthein) {
                if (StringUtils.isEmpty(levensthein.getValue())) {
                    return builder.conjunction();
                }

                List<Path> paths = Lists.newArrayList();
                for (String path : levensthein.getPaths()) {
                    paths.add(cursor(root, path));
                }
                Expression<String> contact = builder.function("contact", String.class, Iterables.toArray(paths, Path.class));
                Expression<Integer> levenshtein = builder.function("levenshtein", Integer.class, builder.literal(levensthein.getValue()), contact, builder.literal(1), builder.literal(1), builder.literal(255));
                sorting.add(builder.asc(levenshtein));

                return builder.conjunction();
            }

        };

    }

    private static Path cursor(Path<?> path, String pathString) {
        if (StringUtils.isEmpty(pathString)) {
            return path;
        }
        String[] paths = pathString.split("\\.");
        Path<?> cursor = path;
        for (String segment : paths) {
            cursor = cursor.get(segment);
        }
        return cursor;
    }

    private static EntityType<?> cursor(Metamodel metamodel, EntityType<?> path, String pathString) {
        if (StringUtils.isEmpty(pathString)) {
            return path;
        }
        String[] paths = pathString.split("\\.");
        EntityType<?> cursor = path;
        for (String segment : paths) {
            Attribute<?, ?> attribute = cursor.getAttribute(segment);
            Class<?> javaType = attribute.getJavaType();
            cursor = metamodel.entity(javaType);
        }
        return cursor;
    }

}
