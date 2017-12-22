package net.portrix.generic.rest.api.search.predicate;

import com.fasterxml.jackson.annotation.JsonTypeName;
import net.portrix.generic.rest.api.search.PredicateVisitor;

import javax.persistence.criteria.Expression;

@JsonTypeName("noop")
public class NoopExpression implements RestExpression {
    @Override
    public Expression<?> accept(PredicateVisitor visitor) {
        return visitor.visitNoop(this);
    }
}
