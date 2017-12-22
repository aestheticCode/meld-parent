package net.portrix.generic.rest.api.search.predicate;

import com.fasterxml.jackson.annotation.JsonTypeName;
import net.portrix.generic.rest.api.search.PredicateVisitor;

import javax.persistence.criteria.Expression;

@JsonTypeName("not")
public class NotExpression implements RestExpression {

    private RestExpression expression;

    public RestExpression getExpression() {
        return expression;
    }

    public void setExpression(RestExpression expression) {
        this.expression = expression;
    }

    @Override
    public Expression<?> accept(PredicateVisitor visitor) {
        return visitor.visitNot(this);
    }
}
