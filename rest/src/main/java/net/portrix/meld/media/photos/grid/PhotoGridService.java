package net.portrix.meld.media.photos.grid;

import net.portrix.generic.ddd.AbstractQueryService;
import net.portrix.meld.media.photos.Photo;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.Map;

@ApplicationScoped
public class PhotoGridService extends AbstractQueryService<Photo> {

    @Inject
    public PhotoGridService(EntityManager entityManager) {
        super(entityManager);
    }

    public PhotoGridService() {
        this(null);
    }

    @Override
    public Class<Photo> getEntityClass() {
        return Photo.class;
    }

    @Override
    public Map<String, Class<?>> getTables() {
        return Collections.emptyMap();
    }

}
