package net.portrix.generic.rest.api.query;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.persistence.criteria.Expression;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(And.class),
        @JsonSubTypes.Type(In.class),
        @JsonSubTypes.Type(Or.class),
        @JsonSubTypes.Type(Like.class),
        @JsonSubTypes.Type(Date.class),
        @JsonSubTypes.Type(Join.class),
        @JsonSubTypes.Type(Equal.class),
        @JsonSubTypes.Type(SubQuery.class),
        @JsonSubTypes.Type(InSelect.class),
        @JsonSubTypes.Type(IsNull.class),
        @JsonSubTypes.Type(Not.class),
        @JsonSubTypes.Type(Levensthein.class)
})
public interface RestPredicate {

    Expression accept(Visitor visitor);

    interface Visitor {

        Expression visit(And and);

        Expression visit(Or or);

        Expression visit(Like like);

        Expression visit(In in);

        Expression visit(Noop noop);

        Expression visit(Date restDate);

        Expression visit(Join join);

        Expression visit(SubQuery subQuery);

        Expression visit(Equal equal);

        Expression visit(InSelect select);

        Expression visit(IsNull isNull);

        Expression visit(Not not);

        Expression visit(Levensthein concat);
    }

}
