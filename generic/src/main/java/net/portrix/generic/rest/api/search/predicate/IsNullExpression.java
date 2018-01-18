package net.portrix.generic.rest.api.search.predicate;

import com.fasterxml.jackson.annotation.JsonTypeName;
import net.portrix.generic.rest.api.search.PredicateVisitor;

import javax.persistence.criteria.Expression;

@JsonTypeName("isNull")
public class IsNullExpression extends AbstractExpression {
    @Override
    public Expression<?> accept(PredicateVisitor visitor) {
        return visitor.visitIsNull(this);
    }
}
