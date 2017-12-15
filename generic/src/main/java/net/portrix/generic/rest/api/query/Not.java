package net.portrix.generic.rest.api.query;

import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.criteria.Expression;

@JsonTypeName("not")
public class Not implements RestPredicate<RestPredicate<?>> {

    private RestPredicate<?> value;

    @Override
    public RestPredicate<?> getValue() {
        return value;
    }

    public void setValue(RestPredicate<?> value) {
        this.value = value;
    }

    @Override
    public Expression accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
