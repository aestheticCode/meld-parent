package net.portrix.generic.rest.api.search.predicate;

import com.fasterxml.jackson.annotation.JsonTypeName;
import net.portrix.generic.rest.api.search.PredicateVisitor;

import javax.persistence.criteria.Expression;

@JsonTypeName("inSelect")
public class InSelectExpression implements RestExpression {

    private SubQueryExpression subQuery;

    public SubQueryExpression getSubQuery() {
        return subQuery;
    }

    public void setSubQuery(SubQueryExpression subQuery) {
        this.subQuery = subQuery;
    }

    @Override
    public Expression<?> accept(PredicateVisitor visitor) {
        return visitor.visitInSelect(this);
    }
}
