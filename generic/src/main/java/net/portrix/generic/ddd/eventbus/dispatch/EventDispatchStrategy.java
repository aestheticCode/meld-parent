package net.portrix.generic.ddd.eventbus.dispatch;

import net.portrix.generic.ddd.eventbus.Callback;
import net.portrix.generic.ddd.eventbus.EventSubscriber;

/**
 * @author Patrick Bittner on 08.05.2014.
 */
public interface EventDispatchStrategy {

    <B, E> void execute(final E event, Callback[] callbacks, final EventSubscriber<B, E> subscriber);

}
