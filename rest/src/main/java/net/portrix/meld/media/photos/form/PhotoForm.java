package net.portrix.meld.media.photos.form;

import net.portrix.generic.rest.api.Blob;

import java.util.UUID;

public class PhotoForm {

    private UUID id;

    private Blob image;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }
}
