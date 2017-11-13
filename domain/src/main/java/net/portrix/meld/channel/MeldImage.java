package net.portrix.meld.channel;

import net.portrix.generic.ddd.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @author Patrick Bittner on 07/10/16.
 */
@Entity
@Table(name = "cn_image")
public class MeldImage extends AbstractEntity {

    private String fileName;

    private LocalDateTime lastModified;

    @Lob
    private byte[] image;

    @Lob
    private byte[] thumbnail;

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
}
