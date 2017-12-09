package net.portrix.meld.usercontrol.group.table.query;

import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Set;
import java.util.UUID;

@JsonTypeName("in")
public class In implements Predicate<Set<UUID>> {

    private String path;

    private Set<UUID> value;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public Set<UUID> getValue() {
        return value;
    }

    @Override
    public javax.persistence.criteria.Predicate accept(Visitor visitor) {
        return visitor.visit(this);
    }

    public void setValue(Set<UUID> value) {
        this.value = value;
    }
}
