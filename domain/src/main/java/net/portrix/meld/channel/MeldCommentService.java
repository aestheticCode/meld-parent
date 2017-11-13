package net.portrix.meld.channel;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.UUID;

/**
 * @author Patrick Bittner on 30/11/2016.
 */
@ApplicationScoped
public class MeldCommentService {

    private final EntityManager entityManager;

    @Inject
    public MeldCommentService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public MeldCommentService() {
        this(null);
    }

    public void save(MeldComment comment) {
        entityManager.persist(comment);
    }

    public MeldComment find(UUID id) {
       return entityManager.find(MeldComment.class, id);
    }
}
