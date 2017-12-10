package net.portrix.meld.social.people.find.table.query;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(And.class),
        @JsonSubTypes.Type(In.class),
        @JsonSubTypes.Type(Or.class),
        @JsonSubTypes.Type(Like.class),
        @JsonSubTypes.Type(Date.class),
        @JsonSubTypes.Type(Join.class),
        @JsonSubTypes.Type(Equal.class),
        @JsonSubTypes.Type(SubQuery.class)
})
public interface Predicate<V> {

    V getValue();

    javax.persistence.criteria.Predicate accept(Visitor visitor);

    interface Visitor {

        javax.persistence.criteria.Predicate visit(And and);

        javax.persistence.criteria.Predicate visit(Or or);

        javax.persistence.criteria.Predicate visit(Like like);

        javax.persistence.criteria.Predicate visit(In in);

        javax.persistence.criteria.Predicate visit(Noop noop);

        javax.persistence.criteria.Predicate visit(Date restDate);

        javax.persistence.criteria.Predicate visit(Join join);

        javax.persistence.criteria.Predicate visit(SubQuery subQuery);

        javax.persistence.criteria.Predicate visit(Equal equal);
    }

}
