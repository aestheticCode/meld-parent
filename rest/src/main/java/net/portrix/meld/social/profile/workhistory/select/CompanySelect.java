package net.portrix.meld.social.profile.workhistory.select;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class CompanySelect {

    private final UUID id;

    private final String name;

    @JsonCreator
    public CompanySelect(@JsonProperty("id") UUID id,
                         @JsonProperty("name")  String name) {
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
