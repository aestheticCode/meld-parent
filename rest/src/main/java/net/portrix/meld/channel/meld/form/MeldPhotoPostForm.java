package net.portrix.meld.channel.meld.form;

import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.UUID;

@JsonTypeName("photo")
public class MeldPhotoPostForm extends AbstractPostForm {

    private UUID photoId;

    public UUID getPhotoId() {
        return photoId;
    }

    public void setPhotoId(UUID photoId) {
        this.photoId = photoId;
    }

    @Override
    AbstractPostForm visit(Visitor visitor) {
        return visitor.visit(this);
    }
}
