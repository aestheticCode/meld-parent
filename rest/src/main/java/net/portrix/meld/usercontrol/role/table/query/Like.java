package net.portrix.meld.usercontrol.role.table.query;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("like")
public class Like implements Predicate<String> {

    private String path;

    private String value;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String getType() {
        return "like";
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public javax.persistence.criteria.Predicate accept(Visitor visitor) {
        return visitor.visit(this);
    }

    public void setValue(String value) {
        this.value = value;
    }
}
