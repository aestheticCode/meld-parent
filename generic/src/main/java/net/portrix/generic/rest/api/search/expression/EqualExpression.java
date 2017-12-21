package net.portrix.generic.rest.api.search.expression;

import com.fasterxml.jackson.annotation.JsonTypeName;
import net.portrix.generic.rest.api.search.Visitor;

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
    public Expression<?> accept(Visitor visitor) {
        return visitor.visitEqual(this);
    }
}
