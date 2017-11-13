package net.portrix.generic.rest.jsr339;

import net.portrix.generic.model.meta.MetaSpecification;
import net.portrix.generic.model.type.resolved.ResolvedType;

import javax.ws.rs.Path;

/**
 * @author Patrick Bittner on 07.06.2015.
 */
public class JaxRSSpecification extends MetaSpecification {

    @Override
    public <X> boolean isSatisfiedBy(ResolvedType<X> type) {
        return MetaSpecification.isSatisfiedWith(type, Path.class);
    }

    @Override
    public String name() {
        return "JaxRS";
    }
}
