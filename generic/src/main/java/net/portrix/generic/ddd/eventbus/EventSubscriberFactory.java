package net.portrix.generic.ddd.eventbus;

import net.portrix.generic.ddd.eventbus.annotation.AnnotationUtil;
import net.portrix.generic.ddd.eventbus.annotation.Handles;
import net.portrix.generic.model.type.resolved.ResolvedMethod;
import net.portrix.generic.model.type.resolved.ResolvedParameter;
import net.portrix.generic.model.type.resolved.ResolvedType;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Patrick Bittner on 26.05.2014.
 */
public final class EventSubscriberFactory {

    public static <X> Set<EventSubscriber<X, ?>> create(final ResolvedType<X> resolvedType) {
        final Set<EventSubscriber<X, ?>> eventSubscribers = new HashSet<>();
        for (final ResolvedMethod<X> resolvedMethod : resolvedType.getMethods()) {
            final List<ResolvedParameter<X>> parameters = resolvedMethod.getParameters();
            for (final ResolvedParameter<X> parameter : parameters) {
                if (parameter.isAnnotationPresent(Handles.class)) {
                    if (parameters.size() == 1) {
                        final Annotation eventBusQualifier = AnnotationUtil.findEventBusQualifier(Arrays.asList(parameter.getAnnotations()));
                        eventSubscribers.add(new EventSubscriber<>(resolvedMethod, parameter, eventBusQualifier));
                    } else {
                        throw new IllegalStateException("Only one argument allowed for EventBus handlers");
                    }
                }
            }
        }
        return eventSubscribers;
    }


}
