package net.portrix.generic.rest.api;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public abstract class AbstractRestEntity implements RestEntity, LinksContainer {

    private final UUID id;

    private final Set<Link> links = new HashSet<>();

    public AbstractRestEntity(UUID id) {
        this.id = id;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public Set<Link> getLinks() {
        return links;
    }

    @Override
    public boolean addLink(Link link) {
        return links.add(link);
    }
}
