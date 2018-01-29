package net.portrix.meld.social.people.find.table.search.expression;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import net.portrix.generic.rest.api.Link;
import net.portrix.meld.social.people.find.table.search.ExpressionVisitor;

import javax.persistence.criteria.Expression;
import java.util.Set;

@JsonTypeName("or")
public class OrExpression extends AbstractExpression {

    private final Set<RestExpression> value;

    @JsonCreator
    public OrExpression(@JsonProperty("value") Set<RestExpression> value,
                        @JsonProperty("links") Link... links) {
        super(links);
        this.value = value;
    }

    public Set<RestExpression> getValue() {
        return value;
    }

    @Override
    public Expression<?> accept(ExpressionVisitor visitor) {
        return visitor.visit(this);
    }

}
