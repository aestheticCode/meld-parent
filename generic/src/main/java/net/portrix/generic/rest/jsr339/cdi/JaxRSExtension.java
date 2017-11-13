package net.portrix.generic.rest.jsr339.cdi;

import com.google.common.base.Joiner;
import net.portrix.generic.model.type.cdi.ProcessResolvedType;
import net.portrix.generic.model.type.resolved.ResolvedType;
import net.portrix.generic.rest.jsr339.JaxRSSpecification;
import net.portrix.generic.rest.jsr339.Resource;
import net.portrix.generic.rest.jsr339.ResourceUtil;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.AfterTypeDiscovery;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.ws.rs.Path;
import java.util.*;

/**
 * @author Patrick Bittner on 07.06.2015.
 */
public class JaxRSExtension implements Extension {

    final Set<Resource<?>> resources = new HashSet<>();

    private Set<ResolvedType<?>> resourceTypes = new HashSet<>();
    private Set<ResolvedType<?>> subResourceTypes = new HashSet<>();

    private final List<Throwable> definitionErrors = new ArrayList<>();

    private JaxRSSpecification specification = new JaxRSSpecification();

    public void processResolvedType(@Observes final ProcessResolvedType event) {
        final ResolvedType<?> resolvedType = event.getResolvedType();
        if (specification.isSatisfiedBy(resolvedType)) {
            if (event.getResolvedType().isAnnotationPresent(Path.class)) {
                resourceTypes.add(event.getResolvedType());
            } else {
                subResourceTypes.add(event.getResolvedType());
            }
        }
    }

    public void afterTypeDiscovery(@Observes final AfterTypeDiscovery afterBeanDiscovery,
                                   final BeanManager beanManager) {
        for (ResolvedType<?> resourceType : resourceTypes) {
            final Resource<?> resource = ResourceUtil.process(resourceType, null, subResourceTypes);
            resources.add(resource);

            DefaultProcessResourceClass processResourceClass = new DefaultProcessResourceClass(resource);
            beanManager.fireEvent(processResourceClass);

            final Throwable definitionError = processResourceClass.getDefinitionError();

            if (definitionError != null) {
                definitionErrors.add(definitionError);
            }
        }
    }

    public void afterBeanDiscovery(@Observes final AfterBeanDiscovery afterBeanDiscovery) {
        if (definitionErrors.isEmpty()) {

        } else {
            afterBeanDiscovery.addDefinitionError(DefinitionError.create(definitionErrors));
        }
    }

    public Set<Resource<?>> getResources() {
        return resources;
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
