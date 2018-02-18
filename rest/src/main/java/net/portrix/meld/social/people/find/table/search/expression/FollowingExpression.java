package net.portrix.meld.social.people.find.table.search.expression;

import net.portrix.meld.social.people.find.table.search.ExpressionVisitor;

import javax.persistence.criteria.Expression;

public class FollowingExpression extends AbstractExpression {

    @Override
    public Expression<?> accept(ExpressionVisitor visitor) {
        return visitor.visit(this);
    }
}
