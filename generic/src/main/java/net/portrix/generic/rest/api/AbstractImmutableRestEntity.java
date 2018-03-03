package net.portrix.generic.rest.api;

import java.util.UUID;

public abstract class AbstractImmutableRestEntity implements RestEntity {

    private final UUID id;

    public AbstractImmutableRestEntity(UUID id) {
        this.id = id;
    }

    @Override
    public UUID getId() {
        return id;
    }
}
