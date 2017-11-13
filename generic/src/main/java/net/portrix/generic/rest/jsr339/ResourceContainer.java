package net.portrix.generic.rest.jsr339;

import net.portrix.generic.rest.jsr339.cdi.JaxRSExtension;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Set;

/**
 * @author Patrick Bittner on 07.06.2015.
 */
@ApplicationScoped
public class ResourceContainer {

    private final JaxRSExtension extension;

    @Inject
    public ResourceContainer(JaxRSExtension extension) {
        this.extension = extension;
    }

    public ResourceContainer() {
        this(null);
    }

    public Set<Resource<?>> getResources() {
        return extension.getResources();
    }
}
