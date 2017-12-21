package net.portrix.generic.rest.api.query;

import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.criteria.Expression;
import java.util.UUID;

@JsonTypeName("equal")
public class Equal implements RestPredicate {

    private UUID value;

    private String path;

    public UUID getValue() {
        return value;
    }

    public void setValue(UUID value) {
        this.value = value;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public Expression accept(Visitor visitor) {
        return visitor.visit(this);
    }

    public String getPath() {
        return this.path;
    }
}
