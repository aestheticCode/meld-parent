package net.portrix.generic.ddd.eventbus;

import com.google.common.reflect.TypeToken;
import net.portrix.generic.ddd.eventbus.dispatch.EventDispatchStrategy;
import net.portrix.generic.ddd.eventbus.dispatch.EventDispatcher;
import net.portrix.generic.ddd.eventbus.publish.EventPublishStrategy;
import net.portrix.generic.ddd.eventbus.publish.EventPublisher;
import net.portrix.generic.ddd.eventbus.publish.StandardPublishStrategy;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Set;

/**
 * @author Patrick Bittner on 04.05.2014.
 */
public class EventBus implements EventPublisher, EventDispatcher {

    private final Annotation eventBusQualifier;

    private final EventSubscriberContainer container;

    private final EventPublishStrategy publishStrategy;

    private final EventDispatchStrategy dispatchStrategy;

    private final StandardPublishStrategy standardPublishStrategy = new StandardPublishStrategy();


    public EventBus(final Annotation eventBusQualifier,
                    final EventSubscriberContainer container,
                    final EventPublishStrategy publishStrategy,
                    final EventDispatchStrategy dispatchStrategy) {
        this.eventBusQualifier = eventBusQualifier;
        this.container = container;
        this.publishStrategy = publishStrategy;
        this.dispatchStrategy = dispatchStrategy;
    }

    @Override
    public void start() {
        publishStrategy.start(this);
        standardPublishStrategy.start(this);
    }

    @Override
    public void shutdown() {
        publishStrategy.shutdown(this);
        standardPublishStrategy.shutdown(this);
    }

    @Override
    public <E> void publish(final E event, final TypeToken<E> eventType, Callback[] sync, final Annotation... qualifiers) {
        if (sync == null) {
            standardPublishStrategy.execute(event, eventType, sync, qualifiers);
        } else {
            publishStrategy.execute(event, eventType, sync, qualifiers);
        }
    }

    @Override
    public <E> void dispatch(final E event, final TypeToken<E> eventType, Callback[] callbacks, final Annotation... qualifiers) {
        final Set<EventSubscriber<?, E>> subscribers = container.find(eventBusQualifier, eventType, qualifiers);
        for (final EventSubscriber<?, E> subscriber : subscribers) {
            dispatchStrategy.execute(event, callbacks, subscriber);
        }
    }

    @Override
    public Collection<EventSubscriber<?, ?>> getSubscribers() {
        return container.getSubscribers();
    }
}
