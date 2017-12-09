package net.portrix.meld.social.people.find.table.query;

import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Set;

@JsonTypeName("or")
public class Or implements Predicate<Set<Predicate<?>>> {

    private Set<Predicate<?>> value;

    @Override
    public Set<Predicate<?>> getValue() {
        return value;
    }

    @Override
    public javax.persistence.criteria.Predicate accept(Visitor visitor) {
        return visitor.visit(this);
    }

    public void setValue(Set<Predicate<?>> value) {
        this.value = value;
    }
}
