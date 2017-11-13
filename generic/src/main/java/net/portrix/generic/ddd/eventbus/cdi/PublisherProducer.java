package net.portrix.generic.ddd.eventbus.cdi;

import com.google.common.reflect.TypeToken;
import net.portrix.generic.ddd.eventbus.EventBusRepository;
import net.portrix.generic.ddd.eventbus.Publisher;
import net.portrix.generic.ddd.eventbus.annotation.AnnotationUtil;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.InjectionPoint;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * @author Patrick Bittner on 09.05.2014.
 */
public final class PublisherProducer {

    @Produces
    public static <E> Publisher<E> produce(final InjectionPoint injectionPoint,
                                           final EventBusRepository eventBusRepository) {

        final Type type = injectionPoint.getType();

        @SuppressWarnings("unchecked")
        final TypeToken<E> typeToken = (TypeToken<E>) TypeToken
                .of(type)
                .resolveType(Publisher.class.getTypeParameters()[0]);


        final Annotated annotated = injectionPoint.getAnnotated();

        final Annotation eventBusQualifier = AnnotationUtil.findEventBusQualifier(annotated.getAnnotations());

        return new Publisher<>(eventBusRepository, eventBusQualifier, typeToken);


    }

}
