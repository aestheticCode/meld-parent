package net.portrix.meld.social.people.category.list.query;


/**
 * Created by Patrick on 18.07.2017.
 */
public class Noop implements Predicate<Object> {

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public javax.persistence.criteria.Predicate accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
