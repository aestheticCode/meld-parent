package net.portrix.generic.ddd.eventbus.cdi;

import net.portrix.generic.ddd.eventbus.EventBus;
import net.portrix.generic.ddd.eventbus.EventBusRepository;
import net.portrix.generic.ddd.eventbus.EventSubscriberContainer;
import net.portrix.generic.ddd.eventbus.dispatch.CDIDispatchStrategy;
import net.portrix.generic.ddd.eventbus.publish.DisruptorPublishStrategy;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.BeanManager;
import javax.transaction.UserTransaction;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.Executor;

/**
 * @author Patrick Bittner on 09.05.2014.
 */
public final class EventBusRepositoryProducer {

    private static final int RING_BUFFER_SIZE = 1024;

    @Produces
    @ApplicationScoped
    public static EventBusRepository produce(final EventBusExtension extension,
                                             final BeanManager beanManager,
                                             final Executor executor,
                                             final UserTransaction userTransaction) {

        final EventSubscriberContainer subscriberRepository = extension.getContainer();

        final Set<Annotation> eventBusQualifiers = subscriberRepository.getEventBusQualifiers();

        final HashMap<Annotation, EventBus> eventBusMap = new HashMap<>();

        for (final Annotation eventBusQualifier : eventBusQualifiers) {
            final CDIDispatchStrategy dispatchStrategy = new CDIDispatchStrategy(beanManager, userTransaction);

            final DisruptorPublishStrategy publishStrategy = new DisruptorPublishStrategy(RING_BUFFER_SIZE, executor);

            final EventBus eventPublisher = new EventBus(eventBusQualifier, subscriberRepository, publishStrategy, dispatchStrategy);

            eventBusMap.put(eventBusQualifier, eventPublisher);

            eventPublisher.start();
        }

        return new EventBusRepositoryBean(eventBusMap);


    }

    public static void dispose(@Disposes final EventBusRepository eventBusRepository) {
        for (final EventBus eventBus : eventBusRepository.findAll()) {
            eventBus.shutdown();
        }
    }

    private static class EventBusRepositoryBean implements EventBusRepository {

        private final HashMap<Annotation, EventBus> eventBusMap;

        public EventBusRepositoryBean(HashMap<Annotation, EventBus> eventBusMap) {
            this.eventBusMap = eventBusMap;
        }

        @Override
        public EventBus find(final Annotation qualifier) {
            return eventBusMap.get(qualifier);
        }

        @Override
        public Collection<EventBus> findAll() {
            return eventBusMap.values();
        }
    }
}
