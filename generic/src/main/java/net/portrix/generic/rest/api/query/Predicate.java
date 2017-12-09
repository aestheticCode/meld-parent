package net.portrix.generic.rest.api.query;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(And.class),
        @JsonSubTypes.Type(In.class),
        @JsonSubTypes.Type(Or.class),
        @JsonSubTypes.Type(Like.class),
        @JsonSubTypes.Type(Date.class)
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
    }

}
