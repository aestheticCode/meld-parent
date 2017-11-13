package net.portrix.generic.ddd.eventbus.cdi;

import net.portrix.generic.ddd.eventbus.EventSubscriber;

/**
 * @author Patrick Bittner on 28.05.2014.
 */
public interface ProcessEventSubscriber {

    EventSubscriber<?, ?> getEventSubscriber();

    void addDefinitionError(Throwable t);

}
