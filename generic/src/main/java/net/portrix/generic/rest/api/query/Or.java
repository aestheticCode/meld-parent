package net.portrix.generic.rest.api.query;

import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Set;

@JsonTypeName("or")
public class Or implements RestPredicate<Set<RestPredicate<?>>> {

    private Set<RestPredicate<?>> value;

    @Override
    public Set<RestPredicate<?>> getValue() {
        return value;
    }

    @Override
    public javax.persistence.criteria.Predicate accept(Visitor visitor) {
        return visitor.visit(this);
    }

    public void setValue(Set<RestPredicate<?>> value) {
        this.value = value;
    }
}
