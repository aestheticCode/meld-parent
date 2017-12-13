package net.portrix.meld.channel.meld.list;


import com.fasterxml.jackson.annotation.JsonTypeName;
import net.portrix.generic.rest.api.Blob;

@JsonTypeName("photo")
public class MeldPhotoItem extends MeldItem {

    private Blob photo;

    public Blob getPhoto() {
        return photo;
    }

    public void setPhoto(Blob photo) {
        this.photo = photo;
    }
}
