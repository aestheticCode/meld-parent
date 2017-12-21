package net.portrix.generic.rest.api.query;

import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.criteria.Expression;
import java.time.LocalDate;

@JsonTypeName("date")
public class Date implements RestPredicate {

    private Date.Value value;

    private String path;

    public Date.Value getValue() {
        return value;
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

    public void setValue(Date.Value value) {
        this.value = value;
    }

    public static class Value {

        private LocalDate lt;

        private LocalDate gt;

        public LocalDate getLt() {
            return lt;
        }

        public void setLt(LocalDate lt) {
            this.lt = lt;
        }

        public LocalDate getGt() {
            return gt;
        }

        public void setGt(LocalDate gt) {
            this.gt = gt;
        }
    }
}

