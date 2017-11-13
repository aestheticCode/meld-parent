package net.portrix.generic.ddd.eventbus;

import com.google.common.reflect.TypeToken;

import java.lang.annotation.Annotation;

/**
 * @author Patrick Bittner on 04.05.2014.
 */
public class Publisher<E> {

    private final EventBusRepository eventBusRepository;

    private final Annotation eventBusQualifier;


    private final TypeToken<E> eventType;

    private final Annotation[] qualifiers;


    public Publisher(final EventBusRepository eventBusRepository,
                     final Annotation eventBusQualifier,
                     final TypeToken<E> eventType,
                     final Annotation... qualifiers) {
        this.eventType = eventType;
        this.eventBusQualifier = eventBusQualifier;
        this.eventBusRepository = eventBusRepository;
        this.qualifiers = qualifiers;
    }

    @SuppressWarnings("unchecked")
    protected Publisher(final EventBusRepository eventBusRepository,
                        final Annotation eventBusQualifier,
                        final Annotation... qualifiers) {
        eventType = (TypeToken<E>) TypeToken
                .of(getClass())
                .resolveType(Publisher.class.getTypeParameters()[0]);
        this.eventBusQualifier = eventBusQualifier;
        this.eventBusRepository = eventBusRepository;
        this.qualifiers = qualifiers;
    }

    public Publisher<E> select(final Annotation... qualifiers) {
        return new Publisher<>(eventBusRepository, eventBusQualifier, eventType, qualifiers);
    }

    public Publisher<E> select(final Annotation eventBusQualifier, final Annotation... qualifiers) {
        return new Publisher<>(eventBusRepository, eventBusQualifier, eventType, qualifiers);
    }

    public <S> Publisher<S> select(final TypeToken<S> subType, final Annotation... qualifiers) {
        return new Publisher<>(eventBusRepository, eventBusQualifier, subType, qualifiers);
    }

    public <S> Publisher<S> select(final Class<S> subClass, final Annotation... qualifiers) {
        return new Publisher<>(eventBusRepository, eventBusQualifier, TypeToken.of(subClass), qualifiers);
    }


    public void fireAsync(final E event, Callback... callbacks) {
        final EventBus eventBus = eventBusRepository.find(eventBusQualifier);

        if (eventBus != null) {
            if (eventType.getRawType() == Object.class) {
                //noinspection unchecked
                eventBus.publish(event, (TypeToken<E>) TypeToken.of(event.getClass()), callbacks, qualifiers);
            } else {
                eventBus.publish(event, eventType, callbacks, qualifiers);
            }
        }
    }

    public void fire(final E event) {
        final EventBus eventBus = eventBusRepository.find(eventBusQualifier);

        if (eventBus != null) {
            if (eventType.getRawType() == Object.class) {
                //noinspection unchecked
                eventBus.publish(event, (TypeToken<E>) TypeToken.of(event.getClass()), null, qualifiers);
            } else {
                eventBus.publish(event, eventType, null, qualifiers);
            }
        }
    }
}
