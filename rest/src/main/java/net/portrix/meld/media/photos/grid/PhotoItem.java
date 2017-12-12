package net.portrix.meld.media.photos.grid;

import net.portrix.generic.rest.api.Blob;

import java.util.UUID;

public class PhotoItem {

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
