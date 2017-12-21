package net.portrix.generic.rest.api.query;

import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.criteria.Expression;

@JsonTypeName("subQuery")
public class SubQuery implements RestPredicate {

    private RestPredicate value;
    private String from;
    private String path;

    public RestPredicate getValue() {
        return value;
    }

    public void setValue(RestPredicate value) {
        this.value = value;
    }

    @Override
    public Expression accept(Visitor visitor) {
        return visitor.visit(this);
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
