package net.portrix.meld.social.people.find.table.search.expression;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import net.portrix.generic.rest.api.Link;
import net.portrix.meld.social.people.find.table.search.ExpressionVisitor;

import javax.persistence.criteria.Expression;

@JsonTypeName("name")
public class NameExpression extends AbstractExpression {

    private final String value;

    @JsonCreator
    public NameExpression(@JsonProperty("value")  String value,
                          @JsonProperty("links") Link... links) {
        super(links);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public Expression<?> accept(ExpressionVisitor visitor) {
        return visitor.visit(this);
    }
}
