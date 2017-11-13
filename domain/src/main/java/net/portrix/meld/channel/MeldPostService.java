package net.portrix.meld.channel;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

/**
 * @author Patrick Bittner on 06/10/16.
 */
@ApplicationScoped
public class MeldPostService {

    private final EntityManager entityManager;

    @Inject
    public MeldPostService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public MeldPostService() {
        this(null);
    }

    public MeldPost find(UUID id) {
        return entityManager.find(MeldPost.class, id);
    }

    public void save(MeldPost post) {
        entityManager.persist(post);
    }

    public List<MeldPost> findAll(int start, int limit) {
        return entityManager.createQuery("select p from MeldPost p order by p.created asc ", MeldPost.class)
                .setFirstResult(start)
                .setMaxResults(limit)
                .getResultList();
    }

    public long countAll() {
        return entityManager.createQuery("select count(p) from MeldPost p ", Long.class)
                .getSingleResult();
    }

}
