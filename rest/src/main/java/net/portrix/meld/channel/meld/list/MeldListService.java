package net.portrix.meld.channel.meld.list;

import net.portrix.meld.channel.MeldImagePost;
import net.portrix.meld.channel.MeldPost;
import net.portrix.meld.usercontrol.User;
import net.portrix.meld.usercontrol.UserImage;
import net.portrix.meld.usercontrol.UserManager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * @author Patrick Bittner on 09.08.17.
 */
@ApplicationScoped
public class MeldListService {

    private final UserManager userManager;

    private final EntityManager entityManager;

    @Inject
    public MeldListService(UserManager userManager, EntityManager entityManager) {
        this.userManager = userManager;
        this.entityManager = entityManager;
    }


    public MeldListService() {
        this(null, null);
    }

    public UserImage findUserImage(User user) {
        return entityManager.createNamedQuery("findUserImage", UserImage.class)
                .setParameter("user", user)
                .getSingleResult();
    }

    public User currentUser() {
        return userManager.current();
    }

    public List<MeldPost> findAll(int start, int limit) {
        return entityManager.createQuery("select p from MeldPost p order by p.created asc ", MeldPost.class)
                .setFirstResult(start)
                .setMaxResults(limit)
                .getResultList();
    }

    public long countAll() {
        return entityManager.createQuery("select count(p) from MeldImagePost p ", Long.class)
                .getSingleResult();
    }

    public void facebook() {

    }
}
