package net.portrix.generic.rest.api.query;

import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.criteria.Expression;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

@JsonTypeName("inSelect")
public class InSelect implements RestPredicate<SubQuery> {

    private SubQuery value;

    private String path;

    @Override
    public SubQuery getValue() {
        return value;
    }

    public void setValue(SubQuery value) {
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
