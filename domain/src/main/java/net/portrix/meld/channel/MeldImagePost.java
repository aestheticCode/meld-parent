package net.portrix.meld.channel;

import javax.persistence.*;

/**
 * @author Patrick Bittner on 06/10/16.
 */
@Entity
@Table(name = "cn_post")
public class MeldImagePost extends MeldPost {

    @ManyToOne(cascade = CascadeType.ALL)
    private MeldImage image;

    public MeldImage getImage() {
        return image;
    }

    public void setImage(MeldImage image) {
        this.image = image;
    }

    public boolean hasImage() {
        return image != null;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
