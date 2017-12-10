package net.portrix.generic.rest.api.query;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.persistence.criteria.Predicate;

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
public interface RestPredicate<V> {

    V getValue();

    Predicate accept(Visitor visitor);

    interface Visitor {

        Predicate visit(And and);

        Predicate visit(Or or);

        Predicate visit(Like like);

        Predicate visit(In in);

        Predicate visit(Noop noop);

        Predicate visit(Date restDate);

        Predicate visit(Join join);

        Predicate visit(SubQuery subQuery);

        Predicate visit(Equal equal);
    }

}
