package net.portrix.generic.ddd.eventbus;

import com.google.common.base.Objects;
import net.portrix.generic.model.type.resolved.AbstractAnnotatedElement;
import net.portrix.generic.model.type.resolved.ResolvedMethod;
import net.portrix.generic.model.type.resolved.ResolvedParameter;

import java.lang.annotation.Annotation;
import java.util.function.BiConsumer;

/**
 * @author Patrick Bittner on 04.05.2014.
 */
public class EventSubscriber<X, T> extends AbstractAnnotatedElement implements BiConsumer<X, T> {

    private final ResolvedMethod<X> method;
    private final ResolvedParameter<X> handledParameter;
    private final Annotation qualifier;

    public EventSubscriber(final ResolvedMethod<X> method,
                           final ResolvedParameter<X> handledParameter,
                           final Annotation qualifier) {
        super(method);
        this.method = method;
        this.handledParameter = handledParameter;
        this.qualifier = qualifier;
    }

    @Override
    public void accept(final X bean, final T event) {
        method.invoke(bean, event);
    }

    public ResolvedMethod<X> getMethod() {
        return method;
    }

    public ResolvedParameter<X> getHandledParameter() {
        return handledParameter;
    }

    public Annotation getQualifier() {
        return qualifier;
    }

    public Class<X> getDeclaringClass() {
        //noinspection unchecked
        return (Class<X>) method.getDeclaringClass();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(method);
    }

    @Override
    public boolean equals(final Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj instanceof EventSubscriber<?, ?>) {
            final EventSubscriber<?, ?> other = (EventSubscriber<?, ?>) obj;
            return Objects.equal(method, other.getMethod());
        }

        return false;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("handledParameter", handledParameter)
                .add("method", method)
                .toString();
    }

}
