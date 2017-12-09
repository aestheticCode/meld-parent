package net.portrix.meld.channel.meld.image;

import net.portrix.meld.channel.MeldImage;
import net.portrix.meld.channel.MeldImagePost;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.UUID;

/**
 * @author Patrick Bittner on 27.07.17.
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

    public void save(MeldImage image) {
        entityManager.persist(image);
    }

    public MeldImagePost find(UUID id) {
        return entityManager.find(MeldImagePost.class, id);
    }
}
