package net.portrix.generic.rest.api.search.predicate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import net.portrix.generic.rest.api.search.PredicateVisitor;

import javax.persistence.criteria.Expression;

@JsonTypeName("subQuery")
public class SubQueryExpression implements RestExpression {

    private final RestExpression expression;
    private final String from;
    private final String path;

    @JsonCreator
    public SubQueryExpression(@JsonProperty("expression") RestExpression expression,
                              @JsonProperty("from") String from,
                              @JsonProperty("path") String path) {
        this.expression = expression;
        this.from = from;
        this.path = path;
    }

    public RestExpression getExpression() {
        return expression;
    }

    public String getFrom() {
        return from;
    }

    public String getPath() {
        return path;
    }

    @Override
    public Expression<?> accept(PredicateVisitor visitor) {
        return visitor.visitSubQuery(this);
    }
}
