package net.portrix.meld.social.communities.find.form;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.portrix.generic.rest.api.AbstractRestEntity;
import net.portrix.generic.rest.api.Blob;

import java.util.UUID;

public class CommunityForm extends AbstractRestEntity {

    private final String name;

    private final Blob image;

    @JsonCreator
    public CommunityForm(@JsonProperty("id") UUID id,
                         @JsonProperty("name") String name,
                         @JsonProperty("image") Blob image) {
        super(id);
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public Blob getImage() {
        return image;
    }

}
