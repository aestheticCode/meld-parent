package net.portrix.generic.model.type.cdi;

import net.portrix.generic.model.type.resolved.ResolvedType;

/**
 * @author Patrick Bittner on 18.05.2014.
 */
public interface ProcessResolvedType {

    ResolvedType<?> getResolvedType();

    void veto();


}
