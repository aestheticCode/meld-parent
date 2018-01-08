package net.portrix.meld.media.photos;

import net.portrix.generic.ddd.AbstractAggregate;
import net.portrix.generic.image.ImageUtils;
import net.portrix.meld.usercontrol.User;

import javax.persistence.*;
import java.io.IOException;
import java.time.LocalDateTime;

@Entity
@Table(name = "me_photo")
public class Photo extends AbstractAggregate {

    private String fileName;

    private LocalDateTime lastModified;

    @Transient
    private byte[] image;

    @Transient
    private byte[] thumbnail;

    @ManyToOne
    private User user;

    @PostLoad
    void postLoad() throws IOException {
        image = ImageUtils.loadImageFromWorkingDirectory(getId(), fileName);
        thumbnail = ImageUtils.loadThumbnailFromWorkingDirectory(getId(), fileName);
    }

    @PostPersist
    void postPersist() throws IOException {
        ImageUtils.saveToWorkingDirectory(getId(), fileName, image, thumbnail);
    }

    @PostUpdate
    void postUpdate() throws IOException {
        ImageUtils.saveToWorkingDirectory(getId(), fileName, image, thumbnail);
    }

    @PostRemove
    void postDelete() throws IOException {
        ImageUtils.deleteFromWorkingDirectory(getId());
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] data) {
        this.image = data;
    }

    public byte[] getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
