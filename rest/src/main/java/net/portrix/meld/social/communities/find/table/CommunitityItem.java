package net.portrix.meld.social.communities.find.table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.portrix.generic.rest.api.Blob;

import java.util.UUID;

public class CommunitityItem {

    private final UUID id;

    private final Blob blob;

    private final String text;

    @JsonCreator
    public CommunitityItem(@JsonProperty("id") UUID id,
                           @JsonProperty("blob") Blob blob,
                           @JsonProperty("text") String text) {
        this.id = id;
        this.blob = blob;
        this.text = text;
    }

    public UUID getId() {
        return id;
    }

    public Blob getBlob() {
        return blob;
    }

    public String getText() {
        return text;
    }
}
