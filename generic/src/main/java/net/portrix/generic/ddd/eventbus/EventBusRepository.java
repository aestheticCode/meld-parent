package net.portrix.generic.ddd.eventbus;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * @author Patrick Bittner on 10.05.2014.
 */
public interface EventBusRepository {

    EventBus find(final Annotation qualifier);

    Collection<EventBus> findAll();

}
