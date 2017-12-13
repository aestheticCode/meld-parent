package net.portrix.meld.media.photos.form;

import net.portrix.meld.media.photos.Photo;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.UUID;

@ApplicationScoped
public class PhotoFormService {

    private final EntityManager entityManager;

    @Inject
    public PhotoFormService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public PhotoFormService() {
        this(null);
    }

    public Photo find(UUID id) {
        return entityManager.find(Photo.class, id);
    }

    public void save(Photo photo) {
        entityManager.persist(photo);
    }

    public void remove(Photo photo) {
        entityManager.remove(photo);
    }
}
