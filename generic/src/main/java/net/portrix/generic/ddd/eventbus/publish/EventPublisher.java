package net.portrix.generic.ddd.eventbus.publish;

import com.google.common.reflect.TypeToken;
import net.portrix.generic.ddd.eventbus.Callback;

import java.lang.annotation.Annotation;

/**
 * @author Patrick Bittner on 10.05.2014.
 */
public interface EventPublisher {

    void start();

    void shutdown();

    <E> void publish(E event, TypeToken<E> eventType, Callback[] sync, Annotation... qualifiers);


}
