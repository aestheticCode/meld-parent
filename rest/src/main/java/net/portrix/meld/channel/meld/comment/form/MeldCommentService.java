package net.portrix.meld.channel.meld.comment.form;

import net.portrix.meld.channel.MeldComment;
import net.portrix.meld.channel.MeldPost;
import net.portrix.meld.social.profile.Profile;
import net.portrix.meld.usercontrol.User;
import net.portrix.meld.usercontrol.UserManager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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

    public void deleteComment(MeldComment comment) {
        entityManager.remove(comment);
    }

    public Profile findProfile(User user) {
        try {
            return entityManager.createQuery("select p from Profile p where p.user = :user", Profile.class)
                    .setParameter("user", user)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public MeldPost findPost(MeldComment comment) {
        return entityManager.createQuery("select m from MeldPost m join m.comments c where c = :comment", MeldPost.class)
                .setParameter("comment", comment)
                .getSingleResult();
    }
}
