package net.portrix.generic.ddd.eventbus.publish;

import com.google.common.reflect.TypeToken;
import net.portrix.generic.ddd.eventbus.Callback;
import net.portrix.generic.ddd.eventbus.dispatch.EventDispatcher;

import java.lang.annotation.Annotation;

/**
 * @author Patrick Bittner on 04.05.2014.
 */
public interface EventPublishStrategy {

    void start(EventDispatcher eventDispatcher);

    void shutdown(EventDispatcher eventDispatcher);

    <E> void execute(final E event, final TypeToken<E> eventType, Callback[] sync, Annotation... qualifiers);


}
