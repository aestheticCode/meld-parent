package net.portrix.meld.channel.meld.comment;

import net.portrix.meld.channel.MeldPost;
import net.portrix.meld.channel.MeldComment;
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
public class MeldCommentService {

    private final UserManager userManager;

    private final EntityManager entityManager;

    @Inject
    public MeldCommentService(UserManager userManager, EntityManager entityManager) {
        this.userManager = userManager;
        this.entityManager = entityManager;
    }

    public MeldCommentService() {
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

    public void saveComment(MeldComment comment) {
        entityManager.persist(comment);
    }
}
