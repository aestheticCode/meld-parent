package net.portrix.meld.channel;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.net.URL;

@Entity
@Table(name = "cn_post")
public class MeldLinkPost extends MeldPost {

    private URL link;

    @ManyToOne(cascade = CascadeType.ALL)
    private MeldImage image;

    public URL getLink() {
        return link;
    }

    public void setLink(URL url) {
        this.link = url;
    }

    public MeldImage getImage() {
        return image;
    }

    public void setImage(MeldImage image) {
        this.image = image;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
