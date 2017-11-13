package net.portrix.meld;

import net.portrix.meld.usercontrol.User;
import net.portrix.meld.usercontrol.UserImage;
import net.portrix.meld.usercontrol.UserManager;
import org.picketlink.Identity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * @author Patrick Bittner on 28.07.17.
 */
@ApplicationScoped
public class ApplicationService {

    private final Identity identity;

    private final UserManager userManager;

    private final EntityManager entityManager;

    @Inject
    public ApplicationService(Identity identity, UserManager userManager, EntityManager entityManager) {
        this.identity = identity;
        this.userManager = userManager;
        this.entityManager = entityManager;
    }

    public ApplicationService() {
        this(null, null, null);
    }

    public boolean isLoggedIn() {
        return identity.isLoggedIn();
    }

    public User current() {
        return userManager.current();
    }

    public UserImage findUserImage(User current) {
        return entityManager.createNamedQuery("findUserImage", UserImage.class)
                .setParameter("user", current)
                .getSingleResult();
    }
}
