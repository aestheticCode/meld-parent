package net.portrix.generic.rest.api.query;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("subQuery")
public class SubQuery implements RestPredicate<RestPredicate<?>> {

    private RestPredicate<?> value;
    private Object from;
    private String fromPath;
    private String select;
    private String selectPath;

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

    public String getFromPath() {
        return fromPath;
    }

    public void setFromPath(String fromPath) {
        this.fromPath = fromPath;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public String getSelectPath() {
        return selectPath;
    }

    public void setSelectPath(String selectPath) {
        this.selectPath = selectPath;
    }
}
