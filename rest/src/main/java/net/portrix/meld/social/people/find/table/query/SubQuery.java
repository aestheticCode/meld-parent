package net.portrix.meld.social.people.find.table.query;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("subQuery")
public class SubQuery implements Predicate<Predicate<?>>{

    private Predicate<?> value;
    private Object from;
    private String path;

    @Override
    public Predicate<?> getValue() {
        return value;
    }

    public void setValue(Predicate<?> value) {
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
}
