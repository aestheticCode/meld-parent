package net.portrix.generic.rest.jsr339;

import net.portrix.generic.model.type.resolved.ResolvedType;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Patrick Bittner on 07.06.2015.
 */
public class Resource<X> {

    private final ResolvedType<X> type;
    private final Set<Locator> parents = new HashSet<>();
    private final Set<Operation> operations = new HashSet<>();
    private final Set<Locator> locators = new HashSet<>();

    public Resource(ResolvedType<X> type) {
        this.type = type;
    }

    public ResolvedType<X> getType() {
        return type;
    }

    public Set<Locator> getParents() {
        return parents;
    }

    public boolean add(Operation operation) {
        return operations.add(operation);
    }

    public boolean addParent(Locator locator) {
        return parents.add(locator);
    }

    public boolean add(Locator locator) {
        return locators.add(locator);
    }

    public Set<Operation> getOperations() {
        return operations;
    }

    public Set<Locator> getLocators() {
        return locators;
    }
}
