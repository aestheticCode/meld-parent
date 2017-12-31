package net.portrix.meld.channel.meld.list;


import com.fasterxml.jackson.annotation.JsonTypeName;
import net.portrix.generic.rest.api.Blob;

import java.net.URI;

@JsonTypeName("photo")
public class MeldPhotoItem extends MeldItem {

    private URI photo;

    public URI getPhoto() {
        return photo;
    }

    public void setPhoto(URI photo) {
        this.photo = photo;
    }
}
