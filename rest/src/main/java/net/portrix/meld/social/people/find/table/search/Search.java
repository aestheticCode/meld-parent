package net.portrix.meld.social.people.find.table.search;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import net.portrix.meld.social.people.find.table.search.expression.*;
import net.portrix.meld.social.people.find.table.search.sort.LevenstheinExpression;
import net.portrix.meld.social.people.find.table.search.sort.NormalExpression;
import net.portrix.meld.social.people.find.table.search.sort.SortExpression;
import net.portrix.meld.social.profile.*;
import net.portrix.meld.usercontrol.User;
import net.portrix.meld.usercontrol.User_;
import org.apache.commons.lang.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class Search {

    private int index;

    private int limit;

    private RestExpression expression = new NoopExpression();

    private List<SortExpression> sorting = new ArrayList<>();

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

    public RestExpression getExpression() {
        return expression;
    }

    public void setExpression(RestExpression expression) {
        this.expression = expression;
    }

    public List<SortExpression> getSorting() {
        return sorting;
    }

    public void setSorting(List<SortExpression> sorting) {
        this.sorting = sorting;
    }

    public static List<Order> sorting(List<SortExpression> sorting, CriteriaBuilder builder, Path<User> root) {
        List<Order> orders = new ArrayList<>();
        for (SortExpression sort : sorting) {
            orders.add(sort.accept(new SortVisitor() {
                @Override
                public Order visit(NormalExpression sort) {
                    if (sort.getAsc()) {
                        return builder.asc(cursor(root, sort.getPath()));
                    } else {
                        return builder.desc(cursor(root, sort.getPath()));
                    }
                }
                @Override
                public Order visit(LevenstheinExpression levenstheinExpression) {

                    List<Path> paths = Lists.newArrayList();
                    for (String path : levenstheinExpression.getPaths()) {
                        paths.add(cursor(root, path));
                    }
                    Expression<String> concat = builder.function("concat", String.class, Iterables.toArray(paths, Path.class));
                    Expression[] expressions = {
                            builder.literal(levenstheinExpression.getValue().toLowerCase()),
                            builder.lower(concat)};
                    Expression<Integer> levenshtein = builder.function("difference", Integer.class, expressions);

                    if (levenstheinExpression.getAsc()) {
                        return builder.asc(levenshtein);
                    } else {
                        return builder.desc(levenshtein);
                    }
                }
            }));
        }
        return orders;
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

    public static ExpressionVisitor visitorVisit(CriteriaBuilder builder, CriteriaQuery<?> query, Root<User> root) {
        return new ExpressionVisitor() {
            @Override
            public Predicate visit(NoopExpression expression) {
                return builder.conjunction();
            }

            @Override
            public Predicate visit(NameExpression expression) {
                if (StringUtils.isEmpty(expression.getValue())) {
                    return builder.conjunction();
                }
                return builder.or(
                        builder.like(builder.lower(root.get(User_.firstName)), expression.getValue().toLowerCase() + "%"),
                        builder.like(builder.lower(root.get(User_.lastName)), expression.getValue().toLowerCase() + "%")
                );
            }

            @Override
            public Predicate visit(SchoolExpression expression) {
                Subquery<User> subquery = query.subquery(User.class);
                Root<School> subRoot = subquery.from(School.class);
                Join<School, Education> subJoin = subRoot.join(School_.education);

                subquery.select(subJoin.get(Education_.user)).where(
                        builder.and(
                                subRoot.get(School_.place).get(Place_.street).in(streetSubQuery(expression, builder, query)),
                                subRoot.get(School_.place).get(Place_.streetNumber).in(streetNumberSubQuery(expression, builder, query)),
                                subRoot.get(School_.place).get(Place_.zipCode).in(zipCodeSubQuery(expression, builder, query)),
                                subRoot.get(School_.place).get(Place_.state).in(stateSubQuery(expression, builder, query))
                        )
                );

                return root.in(subquery);
            }

            private Subquery<String> streetSubQuery(SchoolExpression expression, CriteriaBuilder builder, CriteriaQuery<?> query) {
                Subquery<String> subquery = query.subquery(String.class);
                Root<School> subRoot = subquery.from(School.class);
                subquery.select(subRoot.get(School_.place).get(Place_.street)).where(
                        builder.equal(subRoot.get(School_.id), expression.getValue())
                );
                return subquery;
            }

            private Subquery<String> streetNumberSubQuery(SchoolExpression expression, CriteriaBuilder builder, CriteriaQuery<?> query) {
                Subquery<String> subquery = query.subquery(String.class);
                Root<School> subRoot = subquery.from(School.class);
                subquery.select(subRoot.get(School_.place).get(Place_.streetNumber)).where(
                        builder.equal(subRoot.get(School_.id), expression.getValue())
                );
                return subquery;
            }

            private Subquery<String> zipCodeSubQuery(SchoolExpression expression, CriteriaBuilder builder, CriteriaQuery<?> query) {
                Subquery<String> subquery = query.subquery(String.class);
                Root<School> subRoot = subquery.from(School.class);
                subquery.select(subRoot.get(School_.place).get(Place_.zipCode)).where(
                        builder.equal(subRoot.get(School_.id), expression.getValue())
                );
                return subquery;
            }

            private Subquery<String> stateSubQuery(SchoolExpression expression, CriteriaBuilder builder, CriteriaQuery<?> query) {
                Subquery<String> subquery = query.subquery(String.class);
                Root<School> subRoot = subquery.from(School.class);
                subquery.select(subRoot.get(School_.place).get(Place_.state)).where(
                        builder.equal(subRoot.get(School_.id), expression.getValue())
                );
                return subquery;
            }


            @Override
            public Predicate visit(AndExpression and) {
                List<Predicate> predicates = Lists.newArrayList();

                for (RestExpression expression : and.getValue()) {
                    Predicate predicate = (Predicate) expression.accept(this);
                    predicates.add(predicate);
                }

                return builder.and(Iterables.toArray(predicates, Predicate.class));

            }

            @Override
            public Predicate visit(OrExpression or) {
                List<Predicate> predicates = Lists.newArrayList();

                for (RestExpression expression : or.getValue()) {
                    Predicate predicate = (Predicate) expression.accept(this);
                    predicates.add(predicate);
                }

                return builder.and(Iterables.toArray(predicates, Predicate.class));
            }
        };
    }

}
