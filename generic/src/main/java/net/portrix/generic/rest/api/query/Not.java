package net.portrix.generic.rest.api.query;

import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.criteria.Expression;

@JsonTypeName("not")
public class Not implements RestPredicate {

    private RestPredicate value;

    public RestPredicate getValue() {
        return value;
    }

    public void setValue(RestPredicate value) {
        this.value = value;
    }

    @Override
    public Expression accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
