package net.portrix.generic.rest.api.search.predicate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import net.portrix.generic.rest.api.Link;
import net.portrix.generic.rest.api.search.PredicateVisitor;

import javax.persistence.criteria.Expression;
import java.util.Set;
import java.util.UUID;

@JsonTypeName("in")
public class InExpression extends AbstractExpression {

    private final Set<UUID> values;

    @JsonCreator
    public InExpression(@JsonProperty("values") Set<UUID> values,
                        @JsonProperty("links") Link... links) {
        super(links);
        this.values = values;
    }

    public Set<UUID> getValues() {
        return values;
    }

    @Override
    public Expression<?> accept(PredicateVisitor visitor) {
        return visitor.visitIn(this);
    }
}
