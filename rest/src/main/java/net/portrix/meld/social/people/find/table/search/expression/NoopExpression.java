package net.portrix.meld.social.people.find.table.search.expression;

import com.fasterxml.jackson.annotation.JsonTypeName;
import net.portrix.meld.social.people.find.table.search.ExpressionVisitor;

import javax.persistence.criteria.Expression;

@JsonTypeName("noop")
public class NoopExpression extends AbstractExpression {

    @Override
    public Expression<?> accept(ExpressionVisitor visitor) {
        return visitor.visit(this);
    }

}
