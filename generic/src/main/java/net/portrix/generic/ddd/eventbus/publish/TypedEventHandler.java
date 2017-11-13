package net.portrix.generic.ddd.eventbus.publish;

import com.google.common.reflect.TypeToken;
import com.lmax.disruptor.EventHandler;
import net.portrix.generic.ddd.eventbus.Callback;
import net.portrix.generic.ddd.eventbus.dispatch.EventDispatcher;

import java.lang.annotation.Annotation;

/**
 * @author Patrick Bittner on 04.05.2014.
 */
public class TypedEventHandler implements EventHandler<TypedEventEntry<Object>> {

    private final TypeToken<?> eventType;

    private final EventDispatcher dispatchHandler;

    public TypedEventHandler(final TypeToken<?> eventType, final EventDispatcher dispatchHandler) {
        this.eventType = eventType;
        this.dispatchHandler = dispatchHandler;
    }

    @Override
    public void onEvent(final TypedEventEntry<Object> event, final long sequence, final boolean endOfBatch) throws Exception {
        final TypeToken<Object> eventInstanceType = event.getEventType();

        if (eventType.equals(eventInstanceType)) {
            final Object eventInstance = event.getEvent();
            final Annotation[] qualifiers = event.getQualifiers();
            final Callback[] callbacks = event.getCallbacks();

            dispatchHandler.dispatch(eventInstance, eventInstanceType, callbacks, qualifiers);
        }
    }

}
