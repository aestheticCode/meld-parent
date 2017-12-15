package net.portrix.generic.rest.api.query;

import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.criteria.Expression;

@JsonTypeName("isNull")
public class IsNull implements RestPredicate<Object> {

    private String path;

    @Override
    public Object getValue() {
        return null;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public Expression accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
