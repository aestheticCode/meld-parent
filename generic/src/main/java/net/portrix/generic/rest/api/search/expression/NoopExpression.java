package net.portrix.generic.rest.api.search.expression;

import com.fasterxml.jackson.annotation.JsonTypeName;
import net.portrix.generic.rest.api.search.Visitor;

import javax.persistence.criteria.Expression;

@JsonTypeName("noop")
public class NoopExpression implements RestExpression {
    @Override
    public Expression<?> accept(Visitor visitor) {
        return visitor.visitNoop(this);
    }
}
