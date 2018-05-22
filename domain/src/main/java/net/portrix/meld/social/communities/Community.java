package net.portrix.meld.social.communities;

import net.portrix.generic.image.ImageUtils;
import net.portrix.meld.social.people.Category;

import javax.persistence.*;
import java.io.IOException;
import java.time.LocalDateTime;

public class Community extends Category {

    private String fileName;

    private LocalDateTime lastModified;

    @Transient
    private byte[] image;

    @Transient
    private byte[] thumbnail;

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

    public void setImage(byte[] image) {
        this.image = image;
    }

    public byte[] getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }
}
