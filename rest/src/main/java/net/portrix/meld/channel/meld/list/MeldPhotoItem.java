package net.portrix.meld.channel.meld.list;


import com.fasterxml.jackson.annotation.JsonTypeName;

import java.net.URI;

@JsonTypeName("photo")
public class MeldPhotoItem extends AbstractMeldItem {

    private URI photo;

    public URI getPhoto() {
        return photo;
    }

    public void setPhoto(URI photo) {
        this.photo = photo;
    }
}
