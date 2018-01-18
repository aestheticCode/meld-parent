package net.portrix.generic.rest.api.search.predicate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import net.portrix.generic.rest.api.Link;
import net.portrix.generic.rest.api.search.PredicateVisitor;

import javax.persistence.criteria.Expression;

@JsonTypeName("not")
public class NotExpression extends AbstractExpression {

    private final RestExpression expression;

    @JsonCreator
    public NotExpression(@JsonProperty("expression") RestExpression expression,
                         @JsonProperty("links") Link... links) {
        super(links);
        this.expression = expression;
    }

    public RestExpression getExpression() {
        return expression;
    }

    @Override
    public Expression<?> accept(PredicateVisitor visitor) {
        return visitor.visitNot(this);
    }
}
