package net.portrix.meld.social.people.find.table;

import com.google.common.collect.Iterables;
import net.portrix.meld.social.profile.*;
import net.portrix.meld.usercontrol.User;
import net.portrix.meld.usercontrol.User_;
import org.apache.commons.lang.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class FindTables {

    public static Predicate predicate(FindTableSearch search, CriteriaBuilder builder, Root<User> root, CriteriaQuery<?> query) {
        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.isNotEmpty(search.getName())) {
            predicates.add(name(search.getName(), builder, root));
        }

        if (!Objects.isNull(search.getSchool())) {
            predicates.add(school(search.getSchool(), builder, root, query));
        }

        if (predicates.isEmpty()) {
            return builder.conjunction();
        }

        return builder.and(Iterables.toArray(predicates, Predicate.class));
    }

    public static List<Order> sort(FindTableSearch search, CriteriaBuilder builder, Root<User> root) {
        List<Order> result = new ArrayList<>();

        for (String sortExpression : search.getSort()) {

            String[] sortSegment = sortExpression.split(":");

            Path cursor = cursor(root, sortSegment[0]);

            String direction = sortSegment[1];

            switch (direction) {
                case "asc": {
                    result.add(builder.asc(cursor));
                }
                break;
                case "desc": {
                    result.add(builder.desc(cursor));
                }
                break;
            }

        }

        return result;
    }

    public static Predicate name(String name, CriteriaBuilder builder, Root<User> root) {
        return builder.or(
                builder.like(builder.lower(root.get(User_.firstName)), name.toLowerCase() + "%"),
                builder.like(builder.lower(root.get(User_.lastName)), name.toLowerCase() + "%")
        );
    }

    public static Predicate school(UUID id, CriteriaBuilder builder, Root<User> root, CriteriaQuery<?> query) {
        Subquery<User> subquery = query.subquery(User.class);
        Root<School> subRoot = subquery.from(School.class);
        Join<School, Education> subJoin = subRoot.join(School_.education);

        subquery.select(subJoin.get(Education_.user)).where(
                builder.and(
                        subRoot.get(School_.place).get(Place_.street).in(streetSubQuery(id, builder, query)),
                        subRoot.get(School_.place).get(Place_.streetNumber).in(streetNumberSubQuery(id, builder, query)),
                        subRoot.get(School_.place).get(Place_.zipCode).in(zipCodeSubQuery(id, builder, query)),
                        subRoot.get(School_.place).get(Place_.state).in(stateSubQuery(id, builder, query))
                )
        );

        return root.in(subquery);
    }

    private static Subquery<String> streetSubQuery(UUID id, CriteriaBuilder builder, CriteriaQuery<?> query) {
        Subquery<String> subquery = query.subquery(String.class);
        Root<School> subRoot = subquery.from(School.class);
        subquery.select(subRoot.get(School_.place).get(Place_.street)).where(
                builder.equal(subRoot.get(School_.id), id)
        );
        return subquery;
    }

    private static Subquery<String> streetNumberSubQuery(UUID id, CriteriaBuilder builder, CriteriaQuery<?> query) {
        Subquery<String> subquery = query.subquery(String.class);
        Root<School> subRoot = subquery.from(School.class);
        subquery.select(subRoot.get(School_.place).get(Place_.streetNumber)).where(
                builder.equal(subRoot.get(School_.id), id)
        );
        return subquery;
    }

    private static Subquery<String> zipCodeSubQuery(UUID id, CriteriaBuilder builder, CriteriaQuery<?> query) {
        Subquery<String> subquery = query.subquery(String.class);
        Root<School> subRoot = subquery.from(School.class);
        subquery.select(subRoot.get(School_.place).get(Place_.zipCode)).where(
                builder.equal(subRoot.get(School_.id), id)
        );
        return subquery;
    }

    private static Subquery<String> stateSubQuery(UUID id, CriteriaBuilder builder, CriteriaQuery<?> query) {
        Subquery<String> subquery = query.subquery(String.class);
        Root<School> subRoot = subquery.from(School.class);
        subquery.select(subRoot.get(School_.place).get(Place_.state)).where(
                builder.equal(subRoot.get(School_.id), id)
        );
        return subquery;
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


}
