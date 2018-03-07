package net.portrix.meld.channel.meld.post.list;

import com.fasterxml.jackson.annotation.JsonTypeName;
import net.portrix.generic.rest.api.Blob;

@JsonTypeName("image")
public class MeldImageItem extends AbstractMeldItem {

    private Blob image;

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }
}
