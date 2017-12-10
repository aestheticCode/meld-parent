package net.portrix.generic.rest.api.query;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("subQuery")
public class SubQuery implements RestPredicate<RestPredicate<?>>, RestPath {

    private RestPredicate<?> value;
    private Object from;
    private String path;
    private String select;

    @Override
    public RestPredicate<?> getValue() {
        return value;
    }

    public void setValue(RestPredicate<?> value) {
        this.value = value;
    }

    @Override
    public javax.persistence.criteria.Predicate accept(Visitor visitor) {
        return visitor.visit(this);
    }

    public Object getFrom() {
        return from;
    }

    public void setFrom(Object from) {
        this.from = from;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }
}
