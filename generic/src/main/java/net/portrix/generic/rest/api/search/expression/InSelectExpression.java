package net.portrix.generic.rest.api.search.expression;

import com.fasterxml.jackson.annotation.JsonTypeName;
import net.portrix.generic.rest.api.search.Visitor;

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
    public Expression<?> accept(Visitor visitor) {
        return visitor.visitInSelect(this);
    }
}
