package net.portrix.meld.social.people.category.list.query;

import com.fasterxml.jackson.annotation.JsonTypeName;

import java.time.LocalDate;

@JsonTypeName("date")
public class Date implements Predicate<Date.Value> {

    private Date.Value value;

    private String path;

    @Override
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
    public javax.persistence.criteria.Predicate accept(Visitor visitor) {
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

