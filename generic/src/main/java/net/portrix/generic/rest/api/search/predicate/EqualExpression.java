package net.portrix.generic.rest.api.search.predicate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import net.portrix.generic.rest.api.search.PredicateVisitor;

import javax.persistence.criteria.Expression;
import java.util.UUID;

@JsonTypeName("equal")
public class EqualExpression implements RestExpression {

    private final UUID value;

    @JsonCreator
    public EqualExpression(@JsonProperty("value") UUID value) {
        this.value = value;
    }

    public UUID getValue() {
        return value;
    }

    @Override
    public Expression<?> accept(PredicateVisitor visitor) {
        return visitor.visitEqual(this);
    }
}
