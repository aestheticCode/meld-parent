package net.portrix.generic.ddd.eventbus;

import net.portrix.generic.ddd.eventbus.annotation.Handles;
import net.portrix.generic.model.meta.MetaSpecification;
import net.portrix.generic.model.type.resolved.ResolvedType;

/**
 * @author Patrick Bittner on 26.05.2014.
 */
public class EventBusSpecification extends MetaSpecification {
    @Override
    public <X> boolean isSatisfiedBy(final ResolvedType<X> type) {
        return MetaSpecification.isSatisfiedWith(type, Handles.class);
    }

    @Override
    public String name() {
        return "EventBus";
    }
}
