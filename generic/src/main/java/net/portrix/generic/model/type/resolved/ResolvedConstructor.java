package net.portrix.generic.model.type.resolved;

/**
 * @author Patrick Bittner on 04.05.2014.
 */
public interface ResolvedConstructor<X> extends ResolvedExecutable<X> {

    X invoke(Object... args);
}
