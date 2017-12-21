package net.portrix.generic.rest.api.search.expression;

import com.fasterxml.jackson.annotation.JsonTypeName;
import net.portrix.generic.rest.api.search.Visitor;

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
    public Expression<?> accept(Visitor visitor) {
        return visitor.visitNot(this);
    }
}
