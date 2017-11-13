package net.portrix.generic.ddd.eventbus.publish;

import com.google.common.reflect.TypeToken;
import net.portrix.generic.ddd.eventbus.Callback;
import net.portrix.generic.ddd.eventbus.dispatch.EventDispatcher;

import java.lang.annotation.Annotation;

/**
 * @author Patrick Bittner on 08.05.2014.
 */
public class StandardPublishStrategy implements EventPublishStrategy {

    private EventDispatcher eventDispatcher;

    @Override
    public void start(final EventDispatcher dispatcher) {
        eventDispatcher = dispatcher;
    }

    @Override
    public void shutdown(final EventDispatcher eventDispatcher) {
    }

    @Override
    public <E> void execute(final E event, final TypeToken<E> eventType, Callback[] callbacks, final Annotation... qualifiers) {
        eventDispatcher.dispatch(event, eventType, callbacks, qualifiers);
    }

}
