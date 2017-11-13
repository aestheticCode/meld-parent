package net.portrix.generic.ddd.eventbus.cdi;

import net.portrix.generic.ddd.eventbus.EventSubscriber;

/**
 * @author Patrick Bittner on 28.05.2014.
 */
public class DefaultProcessEventSubscriber implements ProcessEventSubscriber {

    private final EventSubscriber<?, ?> eventSubscriber;

    private Throwable definitionError;

    public DefaultProcessEventSubscriber(final EventSubscriber<?, ?> eventSubscriber) {
        this.eventSubscriber = eventSubscriber;
    }

    @Override
    public EventSubscriber<?, ?> getEventSubscriber() {
        return eventSubscriber;
    }

    @Override
    public void addDefinitionError(final Throwable throwable) {
        definitionError = throwable;
    }

    public Throwable getDefinitionError() {
        return definitionError;
    }
}
