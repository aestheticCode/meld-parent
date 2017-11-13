package net.portrix.generic.ddd.eventbus.dispatch;

import com.google.common.reflect.TypeToken;
import net.portrix.generic.ddd.eventbus.Callback;
import net.portrix.generic.ddd.eventbus.EventSubscriber;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * @author Patrick Bittner on 10.05.2014.
 */
public interface EventDispatcher {

    <E> void dispatch(E event, TypeToken<E> eventType, Callback[] callbacks, Annotation... qualifiers);

    Collection<EventSubscriber<?, ?>> getSubscribers();

}
