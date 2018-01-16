package net.portrix.generic.rest.api.search.predicate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import net.portrix.generic.rest.api.search.PredicateVisitor;

import javax.persistence.criteria.Expression;

@JsonTypeName("like")
public class LikeExpression implements RestExpression {

    private final String value;

    @JsonCreator
    public LikeExpression(@JsonProperty("value") String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public Expression<?> accept(PredicateVisitor visitor) {
        return visitor.visitLike(this);
    }
}
