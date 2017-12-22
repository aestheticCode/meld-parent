package net.portrix.generic.rest.api.search;

import net.portrix.generic.rest.api.search.sort.LevenstheinExpression;
import net.portrix.generic.rest.api.search.sort.NormalExpression;

import javax.persistence.criteria.Order;

public interface SortVisitor {
    Order visit(NormalExpression normalExpression);

    Order visit(LevenstheinExpression levenstheinExpression);
}
