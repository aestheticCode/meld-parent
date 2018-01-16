package net.portrix.generic.rest.api.search.predicate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import net.portrix.generic.rest.api.search.PredicateVisitor;

import javax.persistence.criteria.Expression;

@JsonTypeName("inSelect")
public class InSelectExpression implements RestExpression {

    private final SubQueryExpression subQuery;

    @JsonCreator
    public InSelectExpression(@JsonProperty("subQuery") SubQueryExpression subQuery) {
        this.subQuery = subQuery;
    }

    public SubQueryExpression getSubQuery() {
        return subQuery;
    }

    @Override
    public Expression<?> accept(PredicateVisitor visitor) {
        return visitor.visitInSelect(this);
    }
}
