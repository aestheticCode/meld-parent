package net.portrix.generic.rest.api.search.predicate;

import com.fasterxml.jackson.annotation.JsonTypeName;
import net.portrix.generic.rest.api.search.PredicateVisitor;

import javax.persistence.criteria.Expression;
import java.util.UUID;

@JsonTypeName("equal")
public class EqualExpression implements RestExpression {

    private UUID value;

    public UUID getValue() {
        return value;
    }

    public void setValue(UUID value) {
        this.value = value;
    }

    @Override
    public Expression<?> accept(PredicateVisitor visitor) {
        return visitor.visitEqual(this);
    }
}
