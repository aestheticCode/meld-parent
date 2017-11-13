package net.portrix.meld.usercontrol;

import net.portrix.generic.ddd.AbstractEntity;
import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import javax.persistence.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Patrick Bittner on 07/10/16.
 */
@Entity
@Table(name = "uc_image")
@NamedQuery(name = "findUserImage", query = "select i from UserImage i where i.user = :user")
public class UserImage extends AbstractEntity {

    private String fileName;

    private LocalDateTime lastModified;

    @Lob
    private byte[] image;

    @Lob
    private byte[] thumbnail;

    @ManyToOne
    private User user;

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
