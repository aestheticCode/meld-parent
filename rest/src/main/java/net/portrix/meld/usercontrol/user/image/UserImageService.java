package net.portrix.meld.usercontrol.user.image;

import net.portrix.meld.social.profile.Profile;
import net.portrix.meld.usercontrol.User;
import net.portrix.meld.usercontrol.UserImage;
import net.portrix.meld.usercontrol.UserManager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.UUID;

@ApplicationScoped
public class UserImageService {

    private final UserManager userManager;

    private final EntityManager entityManager;

    @Inject
    public UserImageService(UserManager userManager, EntityManager entityManager) {
        this.userManager = userManager;
        this.entityManager = entityManager;
    }

    public UserImageService() {
        this(null, null);
    }

    public UserImage findUserImage() {
        User current = userManager.current();
        return entityManager.createQuery("select u from UserImage u where u.user = :user", UserImage.class)
                .setParameter("user", current)
                .getSingleResult();
    }

    public Profile findUserImage(User user) {
        return entityManager.createQuery("select u from Profile u where u.user = :user", Profile.class)
                .setParameter("user", user)
                .getSingleResult();
    }

    public User findUser(UUID id) {
        return entityManager.find(User.class, id);
    }
}
