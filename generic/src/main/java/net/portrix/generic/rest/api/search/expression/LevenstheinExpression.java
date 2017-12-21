package net.portrix.generic.rest.api.search.expression;

import com.fasterxml.jackson.annotation.JsonTypeName;
import net.portrix.generic.rest.api.search.Visitor;

import javax.persistence.criteria.Expression;

@JsonTypeName("levensthein")
public class LevenstheinExpression implements RestExpression {

    private String value;

    private RestExpression expression;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public RestExpression getExpression() {
        return expression;
    }

    public void setExpression(RestExpression expression) {
        this.expression = expression;
    }

    @Override
    public Expression<?> accept(Visitor visitor) {
        return visitor.visitLevensthein(this);
    }
}
