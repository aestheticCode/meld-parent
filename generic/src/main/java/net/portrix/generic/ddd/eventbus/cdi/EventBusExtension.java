package net.portrix.generic.ddd.eventbus.cdi;

import com.google.common.base.Joiner;
import net.portrix.generic.ddd.eventbus.EventBusSpecification;
import net.portrix.generic.ddd.eventbus.EventSubscriber;
import net.portrix.generic.ddd.eventbus.EventSubscriberContainer;
import net.portrix.generic.ddd.eventbus.EventSubscriberFactory;
import net.portrix.generic.model.type.cdi.ProcessResolvedType;
import net.portrix.generic.model.type.resolved.ResolvedType;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.AfterTypeDiscovery;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import java.util.*;

/**
 * @author Patrick Bittner on 02.05.2014.
 */
public class EventBusExtension implements Extension {

    private final EventBusSpecification specification = new EventBusSpecification();
    private final Set<EventSubscriber<?, ?>> eventSubscribers = new HashSet<>();
    private final List<Throwable> definitionErrors = new ArrayList<>();

    private EventSubscriberContainer container;

    public void processResolvedType(@Observes final ProcessResolvedType event) {
        final ResolvedType<?> resolvedType = event.getResolvedType();
        if (specification.isSatisfiedBy(resolvedType)) {
            eventSubscribers.addAll(EventSubscriberFactory.create(resolvedType));
        }
    }

    public void afterTypeDiscovery(@Observes final AfterTypeDiscovery afterBeanDiscovery,
                                   final BeanManager beanManager) {
        for (final EventSubscriber<?, ?> eventSubscriber : eventSubscribers) {
            final DefaultProcessEventSubscriber processEventSubscriber = new DefaultProcessEventSubscriber(eventSubscriber);
            beanManager.fireEvent(processEventSubscriber);

            final Throwable definitionError = processEventSubscriber.getDefinitionError();

            if (definitionError != null) {
                definitionErrors.add(definitionError);
            }
        }
    }

    public void afterBeanDiscovery(@Observes final AfterBeanDiscovery afterBeanDiscovery) {
        if (definitionErrors.isEmpty()) {
            container = EventSubscriberContainer.create(eventSubscribers);
        } else {
            afterBeanDiscovery.addDefinitionError(DefinitionError.create(definitionErrors));
        }
    }

    public EventSubscriberContainer getContainer() {
        return container;
    }

    public static class DefinitionError extends Error {

        private final List<Throwable> definitionErrors;

        DefinitionError(final String message, final List<Throwable> definitionErrors) {
            super(message);
            this.definitionErrors = definitionErrors;
        }

        public static DefinitionError create(final List<Throwable> definitionErrors) {
            final String message = Joiner
                    .on("\n")
                    .join(definitionErrors
                                    .stream()
                                    .map(Throwable::getMessage)
                                    .toArray(String[]::new)
                    );

            return new DefinitionError(message, definitionErrors);
        }

        public List<Throwable> getDefinitionErrors() {
            return Collections.unmodifiableList(definitionErrors);
        }
    }
}
