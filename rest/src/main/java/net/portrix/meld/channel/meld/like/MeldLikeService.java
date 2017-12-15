package net.portrix.meld.channel.meld.like;

import net.portrix.meld.channel.MeldComment;
import net.portrix.meld.channel.MeldPost;
import net.portrix.meld.usercontrol.User;
import net.portrix.meld.usercontrol.UserManager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.UUID;

/**
 * @author Patrick Bittner on 10.08.17.
 */
@ApplicationScoped
public class MeldLikeService {

    private final UserManager userManager;

    private final EntityManager entityManager;

    @Inject
    public MeldLikeService(UserManager userManager, EntityManager entityManager) {
        this.userManager = userManager;
        this.entityManager = entityManager;
    }

    public MeldLikeService() {
        this(null, null);
    }

    public User currentUser() {
        return userManager.current();
    }

    public MeldPost findPost(UUID id) {
        return entityManager.find(MeldPost.class, id);
    }

    public MeldComment findComment(UUID id) {
        return entityManager.find(MeldComment.class, id);
    }
}
