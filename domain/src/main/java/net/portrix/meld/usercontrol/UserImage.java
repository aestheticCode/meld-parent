package net.portrix.meld.usercontrol;

import net.portrix.generic.ddd.AbstractEntity;
import net.portrix.generic.image.ImageUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import javax.persistence.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Patrick Bittner on 07/10/16.
 */
@Entity
@Table(name = "uc_image")
@NamedQuery(name = "findUserImage", query = "select i from UserImage i where i.user = :user")
public class UserImage extends AbstractEntity {

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
        image = loadImage(getId(), fileName);
        thumbnail = loadThumbnail(getId(), fileName);
    }

    @PostPersist
    void postPersist() throws IOException {
        save(getId(), fileName, image, thumbnail);
    }

    @PostUpdate
    void postUpdate() throws IOException {
        save(getId(), fileName, image, thumbnail);
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

    private static void save(UUID id, String fileName, byte[] image, byte[] thumbnail) throws IOException {
        File imageWorkingDir = ImageUtils.workingDirectory(id);
        String extension = FilenameUtils.getExtension(fileName);
        File imageFile = new File(imageWorkingDir.getCanonicalPath() + File.separator + "image." + extension);
        File thumbnailFile = new File(imageWorkingDir.getCanonicalPath() + File.separator + "thumbnail." + extension);
        FileUtils.writeByteArrayToFile(imageFile, image);
        FileUtils.writeByteArrayToFile(thumbnailFile, thumbnail);
    }

    private static byte[] loadImage(UUID id, String fileName) throws IOException {
        File imageWorkingDir = ImageUtils.workingDirectory(id);
        String extension = FilenameUtils.getExtension(fileName);
        File imageFile = new File(imageWorkingDir.getCanonicalPath() + File.separator + "image." + extension);
        return IOUtils.toByteArray(imageFile.toURI());
    }

    private static byte[] loadThumbnail(UUID id, String fileName) throws IOException {
        File imageWorkingDir = ImageUtils.workingDirectory(id);
        String extension = FilenameUtils.getExtension(fileName);
        File thumbnailFile = new File(imageWorkingDir.getCanonicalPath() + File.separator + "thumbnail." + extension);
        return IOUtils.toByteArray(thumbnailFile.toURI());
    }
}
