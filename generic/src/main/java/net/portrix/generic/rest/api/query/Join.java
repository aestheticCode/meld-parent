package net.portrix.generic.rest.api.query;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("join")
public class Join implements RestPredicate<RestPredicate<?>>, RestPath{

    private RestPredicate<?> value;

    private String path;
    
    @Override
    public RestPredicate<?> getValue() {
        return value;
    }

    public void setValue(RestPredicate<?> value) {
        this.value = value;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public javax.persistence.criteria.Predicate accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
