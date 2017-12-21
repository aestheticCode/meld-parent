package net.portrix.generic.rest.api.query;


import javax.persistence.criteria.Expression;

/**
 * Created by Patrick on 18.07.2017.
 */
public class Noop implements RestPredicate {

    @Override
    public Expression accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
