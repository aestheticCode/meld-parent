package net.portrix.generic.rest.api;

import java.util.UUID;

public abstract class AbstractRestEntity implements RestEntity {

    private UUID id;

    @Override
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
