package net.portrix.generic.rest.api.query;

import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.criteria.Expression;

@JsonTypeName("join")
public class Join implements RestPredicate {

    private RestPredicate value;

    private String path;

    public RestPredicate getValue() {
        return value;
    }

    public void setValue(RestPredicate value) {
        this.value = value;
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
