package net.portrix.generic.ddd.eventbus.dispatch;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.reflect.TypeToken;
import net.portrix.generic.ddd.eventbus.Callback;
import net.portrix.generic.ddd.eventbus.EventSubscriber;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * @author Patrick Bittner on 08.05.2014.
 */
public class StandardDispatchStrategy implements EventDispatchStrategy {

    private static final LoadingCache<Class<?>, Set<Class<?>>> flattenHierarchyCache =
            CacheBuilder.newBuilder()
                    .weakKeys()
                    .build(new CacheLoader<Class<?>, Set<Class<?>>>() {
                        @SuppressWarnings({"unchecked", "rawtypes"})
                        @Override
                        public Set<Class<?>> load(final Class<?> concreteClass) {
                            return (Set) TypeToken.of(concreteClass).getTypes().rawTypes();
                        }
                    });

    private final Multimap<Class<?>, Object> subscriberInstancesByType = LinkedListMultimap.create();

    @Override
    public <B, E> void execute(final E event, Callback[] callbacks, final EventSubscriber<B, E> subscriber) {
        try {
            final Class<B> declaringClass = subscriber.getDeclaringClass();

            final Set<Class<?>> hierarchy = flattenHierarchyCache.get(declaringClass);

            for (final Class<?> aClass : hierarchy) {

                @SuppressWarnings("unchecked")
                final Collection<B> instances = (Collection<B>) subscriberInstancesByType.get(aClass);

                for (final B instance : instances) {
                    subscriber.accept(instance, event);
                }

                for (Callback callback : callbacks) {
                    callback.execute();
                }

            }


        } catch (final ExecutionException e) {
            throw new IllegalStateException(e);
        }
    }

    public <B> void register(final Class<B> beanClass, final B bean) {
        subscriberInstancesByType.put(beanClass, bean);
    }

    public <B> void unRegister(final Class<B> beanClass, final B bean) {
        subscriberInstancesByType.remove(beanClass, bean);
    }


}
