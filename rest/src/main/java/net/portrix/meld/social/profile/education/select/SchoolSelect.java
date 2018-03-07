package net.portrix.meld.social.profile.education.select;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class SchoolSelect {

    private final UUID id;

    private final String name;

    @JsonCreator
    public SchoolSelect(@JsonProperty("id") final UUID id,
                        @JsonProperty("name") final String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
