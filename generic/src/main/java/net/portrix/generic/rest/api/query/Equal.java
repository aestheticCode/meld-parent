package net.portrix.generic.rest.api.query;

import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.UUID;

@JsonTypeName("equal")
public class Equal implements RestPredicate<UUID>, RestPath{

    private UUID value;

    private String path;

    @Override
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
    public javax.persistence.criteria.Predicate accept(Visitor visitor) {
        return visitor.visit(this);
    }

    public String getPath() {
        return this.path;
    }
}