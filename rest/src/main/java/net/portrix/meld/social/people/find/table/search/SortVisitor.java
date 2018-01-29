package net.portrix.meld.social.people.find.table.search;


import net.portrix.meld.social.people.find.table.search.sort.LevenstheinExpression;
import net.portrix.meld.social.people.find.table.search.sort.NormalExpression;

import javax.persistence.criteria.Order;

public interface SortVisitor {
    Order visit(NormalExpression normalExpression);

    Order visit(LevenstheinExpression levenstheinExpression);
}
