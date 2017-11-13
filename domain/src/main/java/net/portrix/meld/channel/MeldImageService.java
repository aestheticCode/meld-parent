package net.portrix.meld.channel;

import org.imgscalr.Scalr;

import javax.enterprise.context.ApplicationScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @author Patrick Bittner on 07/10/16.
 */
@ApplicationScoped
public class MeldImageService {

    private final EntityManager entityManager;

    @Inject
    public MeldImageService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public MeldImageService() {
        this(null);
    }

    public MeldImage find(final UUID uuid) {
        return entityManager.find(MeldImage.class, uuid);
    }

    public void save(final MeldImage image) {
        try {
            final BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(image.getImage()));
            BufferedImage scaledImg = Scalr.resize(bufferedImage, Scalr.Mode.FIT_TO_WIDTH, 400);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write( scaledImg, "jpg", baos ); // if your image is a jpg
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            image.setThumbnail(imageInByte);
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        entityManager.persist(image);
    }

}
