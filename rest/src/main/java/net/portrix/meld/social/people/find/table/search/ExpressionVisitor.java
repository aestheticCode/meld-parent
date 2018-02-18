package net.portrix.meld.social.people.find.table.search;

import net.portrix.meld.social.people.find.table.search.expression.*;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

public interface ExpressionVisitor {

    Predicate visit(NoopExpression expression);

    Predicate visit(NameExpression expression);

    Predicate visit(SchoolExpression expression);

    Predicate visit(AndExpression expression);

    Predicate visit(OrExpression expression);

    Predicate visit(FollowingExpression expression);

}
