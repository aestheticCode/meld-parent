package net.portrix.generic.ddd.eventbus;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.reflect.TypeToken;
import net.portrix.generic.model.type.resolved.ResolvedParameter;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Patrick Bittner on 04.05.2014.
 */
public class EventSubscriberContainer {

    private final Multimap<Annotation, EventSubscriber<?, ?>> subscribers;

    EventSubscriberContainer(final Multimap<Annotation, EventSubscriber<?, ?>> subscribers) {
        this.subscribers = subscribers;
    }

    public static EventSubscriberContainer create(final Set<EventSubscriber<?, ?>> eventSubscribers) {
        return new EventSubscriberContainer(Multimaps.index(eventSubscribers, EventSubscriber::getQualifier));
    }

    private static <X, E> boolean isApplicable(final TypeToken<E> eventType,
                                               final ResolvedParameter<X> parameter,
                                               final Annotation[] qualifiers) {
        final boolean assignableFromEventType = eventType.isAssignableFrom(parameter.getType());
        if (assignableFromEventType) {

            final boolean qualifiersMatchingType = Stream.of(qualifiers)
                    .allMatch(qualifier -> parameter.isAnnotationPresent(qualifier.annotationType()));
            if (qualifiersMatchingType) {

                return Stream.of(qualifiers)
                        .allMatch(qualifier -> parameter.getAnnotation(qualifier.annotationType()).equals(qualifier));
            }
        }

        return false;
    }

    @SuppressWarnings("unchecked")
    public <E> Set<EventSubscriber<?, E>> find(final Annotation eventBusQualifier,
                                               final TypeToken<E> eventType,
                                               final Annotation... qualifiers) {
        return subscribers
                .get(eventBusQualifier)
                .stream()
                .filter(subscriber -> isApplicable(eventType, subscriber.getHandledParameter(), qualifiers))
                .map(subscriber -> (EventSubscriber<?, E>) subscriber)
                .collect(Collectors.toSet());
    }

    public Collection<EventSubscriber<?, ?>> getSubscribers() {
        return Collections.unmodifiableCollection(subscribers.values());
    }

    public Set<Annotation> getEventBusQualifiers() {
        return Collections.unmodifiableSet(subscribers.keySet());
    }
}
