package net.portrix.meld.social.people.find.table.query;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("join")
public class Join implements Predicate<Predicate<?>>{

    private Predicate<?> value;

    private String path;
    
    @Override
    public Predicate<?> getValue() {
        return value;
    }

    public void setValue(Predicate<?> value) {
        this.value = value;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public javax.persistence.criteria.Predicate accept(Predicate.Visitor visitor) {
        return visitor.visit(this);
    }

}
