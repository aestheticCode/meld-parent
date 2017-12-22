package net.portrix.generic.rest.api.search.predicate;

import com.fasterxml.jackson.annotation.JsonTypeName;
import net.portrix.generic.rest.api.search.PredicateVisitor;

import javax.persistence.criteria.Expression;
import java.util.List;

@JsonTypeName("concat")
public class ConcatExpression implements RestExpression {

    private RestExpression expression;

    private List<String> paths;

    public RestExpression getExpression() {
        return expression;
    }

    public void setExpression(RestExpression expression) {
        this.expression = expression;
    }

    public List<String> getPaths() {
        return paths;
    }

    public void setPaths(List<String> paths) {
        this.paths = paths;
    }

    @Override
    public Expression<?> accept(PredicateVisitor visitor) {
        return visitor.visitConcat(this);
    }
}
