package net.portrix.meld.social.people.find.table.search.sort;

import com.fasterxml.jackson.annotation.JsonTypeName;
import net.portrix.meld.social.people.find.table.search.SortVisitor;

import javax.persistence.criteria.Order;

@JsonTypeName("normal")
public class NormalExpression implements SortExpression {

    private String path;

    private Boolean asc;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean getAsc() {
        return asc;
    }

    public void setAsc(Boolean asc) {
        this.asc = asc;
    }

    @Override
    public Order accept(SortVisitor visitor) {
        return visitor.visit(this);
    }
}
