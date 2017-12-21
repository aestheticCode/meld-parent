package net.portrix.generic.rest.api.search.expression;

import com.fasterxml.jackson.annotation.JsonTypeName;
import net.portrix.generic.rest.api.search.Visitor;

import javax.persistence.criteria.Expression;
import java.util.Set;
import java.util.UUID;

@JsonTypeName("in")
public class InExpression implements RestExpression {

    private Set<UUID> values;

    public Set<UUID> getValues() {
        return values;
    }

    public void setValues(Set<UUID> values) {
        this.values = values;
    }

    @Override
    public Expression<?> accept(Visitor visitor) {
        return visitor.visitIn(this);
    }
}
