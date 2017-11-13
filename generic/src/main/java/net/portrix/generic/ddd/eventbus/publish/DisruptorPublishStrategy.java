package net.portrix.generic.ddd.eventbus.publish;

import com.google.common.reflect.TypeToken;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import net.portrix.generic.ddd.eventbus.Callback;
import net.portrix.generic.ddd.eventbus.dispatch.EventDispatcher;

import java.lang.annotation.Annotation;
import java.util.concurrent.Executor;

/**
 * @author Patrick Bittner on 04.05.2014.
 */
public class DisruptorPublishStrategy implements EventPublishStrategy {

    private final Disruptor<TypedEventEntry<Object>> disruptor;

    public DisruptorPublishStrategy(final int bufferSize, final Executor executor) {
        disruptor = new Disruptor<>(TypedEventEntry::new, bufferSize, executor);
    }


    @Override
    public void start(final EventDispatcher eventDispatcher) {

        disruptor.handleEventsWith(eventDispatcher.getSubscribers()
                        .stream()
                        .map(subscriber -> new TypedEventHandler(subscriber.getHandledParameter().getType(), eventDispatcher))
                        .toArray(TypedEventHandler[]::new)
        );

        disruptor.start();
    }

    @Override
    public void shutdown(final EventDispatcher eventDispatcher) {
        disruptor.shutdown();
    }

    public RingBuffer<TypedEventEntry<Object>> getRingBuffer() {
        return disruptor.getRingBuffer();
    }


    @Override
    public <E> void execute(final E event, final TypeToken<E> eventType, Callback[] callbacks, final Annotation... qualifiers) {

        final long sequence = getRingBuffer().next();

        try {

            final TypedEventEntry<Object> entry = getRingBuffer().get(sequence);

            entry.setEvent(event);
            entry.setEventType(eventType);
            entry.setQualifiers(qualifiers);
            entry.setCallbacks(callbacks);

        } finally {
            getRingBuffer().publish(sequence);
        }

    }

}
