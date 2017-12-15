package net.portrix.generic.rest.api.query;

import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.criteria.Expression;
import java.util.Set;
import java.util.UUID;

@JsonTypeName("in")
public class In implements RestPredicate<Set<UUID>> {

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
    public Expression accept(Visitor visitor) {
        return visitor.visit(this);
    }

    public void setValue(Set<UUID> value) {
        this.value = value;
    }
}
