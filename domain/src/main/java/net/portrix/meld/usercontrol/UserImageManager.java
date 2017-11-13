package net.portrix.meld.usercontrol;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;

import javax.enterprise.context.ApplicationScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Patrick Bittner on 07/10/16.
 */
@ApplicationScoped
public class UserImageManager {

    private final EntityManager entityManager;

    @Inject
    public UserImageManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public UserImageManager() {
        this(null);
    }

    public UserImage find(User user) {
        try {
            return entityManager.createQuery("select u from UserImage u where u.user = :user", UserImage.class)
                    .setParameter("user", user)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void save(final UserImage image) {
        try {
            final BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(image.getImage()));
            BufferedImage scaledImg = Scalr.resize(bufferedImage, 36, 100);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write( scaledImg, FilenameUtils.getExtension(image.getFileName()), baos ); // if your image is a jpg
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            image.setThumbnail(imageInByte);
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        entityManager.persist(image);
    }

    public void delete(final UserImage image) {
        entityManager.remove(image);
    }

}
