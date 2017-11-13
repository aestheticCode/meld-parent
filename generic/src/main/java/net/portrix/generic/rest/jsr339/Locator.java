package net.portrix.generic.rest.jsr339;

import net.portrix.generic.rest.jsr339.Resource;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Patrick Bittner on 07.06.2015.
 */
public class Locator {

    private final boolean secured;

    private final String path;

    private final String name;

    private final Set<Resource<?>> types = new HashSet<>();

    private final Resource<?> resource;

    public Locator(boolean secured, String path, String name, Resource<?> resource) {
        this.secured = secured;
        this.path = path;
        this.name = name;
        this.resource = resource;
    }

    public boolean add(Resource resource) {
        return types.add(resource);
    }


    public boolean isSecured() {
        return secured;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public Set<Resource<?>> getTypes() {
        return types;
    }

    public Resource<?> getResource() {
        return resource;
    }

}
