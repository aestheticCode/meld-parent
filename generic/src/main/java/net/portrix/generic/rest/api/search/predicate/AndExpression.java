package net.portrix.generic.rest.api.search.predicate;

import com.fasterxml.jackson.annotation.JsonTypeName;
import net.portrix.generic.rest.api.search.PredicateVisitor;

import javax.persistence.criteria.Expression;
import java.util.Set;

@JsonTypeName("and")
public class AndExpression implements RestExpression {

    private Set<RestExpression> expressions;

    public Set<RestExpression> getExpressions() {
        return expressions;
    }

    public void setExpressions(Set<RestExpression> expressions) {
        this.expressions = expressions;
    }

    @Override
    public Expression<?> accept(PredicateVisitor visitor) {
        return visitor.visitAnd(this);
    }
}
