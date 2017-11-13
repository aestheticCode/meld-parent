package net.portrix.generic.ddd.eventbus.publish;

import com.google.common.reflect.TypeToken;
import net.portrix.generic.ddd.eventbus.Callback;

import java.lang.annotation.Annotation;

/**
 * @author Patrick Bittner on 10.05.2014.
 */
public class TypedEventEntry<E> {

    private TypeToken<? extends E> eventType;

    private Annotation[] qualifiers;

    private E event;

    private Callback[] callbacks;

    public TypedEventEntry(final TypeToken<E> eventType,
                           final E event) {
        this.eventType = eventType;
        this.event = event;
    }

    public TypedEventEntry() {
        this(null, null);
    }

    @SuppressWarnings("unchecked")
    public <U extends E> TypeToken<U> getEventType() {
        return (TypeToken<U>) eventType;
    }

    public void setEventType(final TypeToken<? extends E> eventType) {
        this.eventType = eventType;
    }

    public Annotation[] getQualifiers() {
        return qualifiers;
    }

    public void setQualifiers(final Annotation[] qualifiers) {
        this.qualifiers = qualifiers;
    }

    public E getEvent() {
        return event;
    }

    public void setEvent(final E event) {
        this.event = event;
    }

    public Callback[] getCallbacks() {
        return callbacks;
    }

    public void setCallbacks(Callback[] callbacks) {
        this.callbacks = callbacks;
    }
}
