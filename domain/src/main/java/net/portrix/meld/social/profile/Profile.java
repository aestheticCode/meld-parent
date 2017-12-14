package net.portrix.meld.social.profile;


import net.portrix.generic.ddd.AbstractAggregate;
import net.portrix.meld.UserControlModule;
import net.portrix.meld.media.photos.Photo;
import net.portrix.meld.usercontrol.User;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import javax.persistence.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "so_profile")
public class Profile extends AbstractAggregate {

    @ManyToOne
    private Photo photo;

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }
}
