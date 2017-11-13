package net.portrix.generic.model.type.cdi;

import net.portrix.generic.model.type.TypeResolver;
import net.portrix.generic.model.type.resolved.ResolvedType;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;

/**
 * @author Patrick Bittner on 24.05.2014.
 */
public class ResolvedTypeExtension implements Extension {

    public <X> void onProcessAnnotatedType(@Observes final ProcessAnnotatedType<X> event,
                                           final BeanManager beanManager) {
        final ResolvedType<X> resolvedType = TypeResolver.resolve(event.getAnnotatedType().getJavaClass());

        beanManager.fireEvent(new DefaultProcessResolvedType(event, resolvedType));
    }

}
