package net.portrix.generic.rest.api.search.predicate;

import com.fasterxml.jackson.annotation.JsonTypeName;
import net.portrix.generic.rest.api.search.PredicateVisitor;

import javax.persistence.criteria.Expression;

@JsonTypeName("subQuery")
public class SubQueryExpression implements RestExpression {

    private RestExpression expression;
    private String from;
    private String path;

    public RestExpression getExpression() {
        return expression;
    }

    public void setExpression(RestExpression expression) {
        this.expression = expression;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public Expression<?> accept(PredicateVisitor visitor) {
        return visitor.visitSubQuery(this);
    }
}
