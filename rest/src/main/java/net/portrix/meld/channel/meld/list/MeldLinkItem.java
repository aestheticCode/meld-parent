package net.portrix.meld.channel.meld.list;

import com.fasterxml.jackson.annotation.JsonTypeName;
import net.portrix.generic.rest.api.Blob;

import java.net.URL;

@JsonTypeName("link")
public class MeldLinkItem extends AbstractMeldItem {

    private URL link;

    private Blob image;

    public URL getLink() {
        return link;
    }

    public void setLink(URL link) {
        this.link = link;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }
}
