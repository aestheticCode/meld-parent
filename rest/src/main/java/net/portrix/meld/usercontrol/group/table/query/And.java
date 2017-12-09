package net.portrix.meld.usercontrol.group.table.query;

import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Set;

@JsonTypeName("and")
public class And implements Predicate<Set<Predicate<?>>> {

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
