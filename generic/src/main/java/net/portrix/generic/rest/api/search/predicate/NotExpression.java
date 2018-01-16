package net.portrix.generic.rest.api.search.predicate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import net.portrix.generic.rest.api.search.PredicateVisitor;

import javax.persistence.criteria.Expression;

@JsonTypeName("not")
public class NotExpression implements RestExpression {

    private final RestExpression expression;

    @JsonCreator
    public NotExpression(@JsonProperty("expression") RestExpression expression) {
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
