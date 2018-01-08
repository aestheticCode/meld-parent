package net.portrix.meld.channel;

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
@Table(name = "cn_image")
public class MeldImage extends AbstractEntity {

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

    public void setImage(byte[] data) {
        this.image = data;
    }

    public byte[] getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
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
