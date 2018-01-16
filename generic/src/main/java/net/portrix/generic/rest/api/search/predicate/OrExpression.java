package net.portrix.generic.rest.api.search.predicate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.google.common.collect.Sets;
import net.portrix.generic.rest.api.search.PredicateVisitor;

import javax.persistence.criteria.Expression;
import java.util.Set;

@JsonTypeName("or")
public class OrExpression implements RestExpression {

    private final Set<RestExpression> expressions;

    @JsonCreator
    public OrExpression(@JsonProperty("expressions") RestExpression... expressions) {
        this.expressions = Sets.newHashSet(expressions);
    }

    public Set<RestExpression> getExpressions() {
        return expressions;
    }

    @Override
    public Expression<?> accept(PredicateVisitor visitor) {
        return visitor.visitOr(this);
    }
}
