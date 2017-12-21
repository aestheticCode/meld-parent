package net.portrix.generic.rest.api.query;

import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.criteria.Expression;
import java.util.Set;

@JsonTypeName("or")
public class Or implements RestPredicate {

    private Set<RestPredicate> value;

    public Set<RestPredicate> getValue() {
        return value;
    }

    @Override
    public Expression accept(Visitor visitor) {
        return visitor.visit(this);
    }

    public void setValue(Set<RestPredicate> value) {
        this.value = value;
    }
}
