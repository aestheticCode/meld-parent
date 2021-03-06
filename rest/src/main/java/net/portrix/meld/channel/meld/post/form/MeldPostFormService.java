package net.portrix.meld.channel.meld.post.form;

import net.portrix.meld.channel.MeldPost;
import net.portrix.meld.media.photos.Photo;
import net.portrix.meld.social.people.Category;
import net.portrix.meld.usercontrol.User;
import net.portrix.meld.usercontrol.UserManager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.UUID;

/**
 * @author Patrick Bittner on 28.07.17.
 */
@ApplicationScoped
public class MeldPostFormService {

    private final UserManager userManager;

    private final EntityManager entityManager;

    @Inject
    public MeldPostFormService(UserManager userManager, EntityManager entityManager) {
        this.userManager = userManager;
        this.entityManager = entityManager;
    }

    public MeldPostFormService() {
        this(null, null);
    }

    public MeldPost findPost(UUID id) {
        return entityManager.find(MeldPost.class, id);
    }

    public User currentUser() {
        return userManager.current();
    }

    public void savePost(MeldPost post) {
        entityManager.persist(post);
    }

    public Category findCategory(UUID category) {
        return entityManager.find(Category.class, category);
    }

    public Photo findPhoto(UUID photoId) {
        return entityManager.find(Photo.class, photoId);
    }

    public void deletePost(MeldPost post) {
        entityManager.remove(post);
    }
}
