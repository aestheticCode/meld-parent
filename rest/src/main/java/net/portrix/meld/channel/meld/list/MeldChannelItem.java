package net.portrix.meld.channel.meld.list;

import java.net.URI;
import java.util.UUID;

public class MeldChannelItem {

    private UUID id;

    private String name;

    private String type;

    private URI thumbnail;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public URI getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(URI thumbnail) {
        this.thumbnail = thumbnail;
    }
}
