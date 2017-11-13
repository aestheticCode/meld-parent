package net.portrix.generic.ddd.eventbus.annotation;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * @author Patrick Bittner on 28.05.2014.
 */
public final class AnnotationUtil {
    public static Annotation findEventBusQualifier(final Collection<Annotation> annotations) {
        Annotation eventBusQualifier = DefaultLiteral.INSTANCE;
        for (final Annotation annotation : annotations) {
            final Class<? extends Annotation> annotationType = annotation.annotationType();
            if (annotationType.isAnnotationPresent(EventBusQualifier.class)) {
                if (eventBusQualifier.equals(DefaultLiteral.INSTANCE)) {
                    eventBusQualifier = annotation;
                } else {
                    throw new IllegalStateException("Only one EventBus qualifier allowed");
                }
            }
        }
        return eventBusQualifier;
    }
}
