package net.portrix.meld.usercontrol.user.table.query;


/**
 * Created by Patrick on 18.07.2017.
 */
public class Noop implements Predicate<Object> {
    @Override
    public String getType() {
        return "noop";
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public javax.persistence.criteria.Predicate accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
