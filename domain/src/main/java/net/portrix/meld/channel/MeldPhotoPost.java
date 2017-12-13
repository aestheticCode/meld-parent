package net.portrix.meld.channel;

import net.portrix.meld.media.photos.Photo;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cn_post")
public class MeldPhotoPost extends MeldPost {

    @ManyToOne
    private Photo photo;

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
