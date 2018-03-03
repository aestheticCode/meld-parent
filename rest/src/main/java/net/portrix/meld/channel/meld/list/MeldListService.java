package net.portrix.meld.channel.meld.list;

import net.portrix.generic.ddd.AbstractSearchService;
import net.portrix.meld.channel.MeldPost;
import net.portrix.meld.social.profile.Profile;
import net.portrix.meld.usercontrol.User;
import net.portrix.meld.usercontrol.UserManager;
import org.picketlink.Identity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

/**
 * @author Patrick Bittner on 09.08.17.
 */
@ApplicationScoped
public class MeldListService extends AbstractSearchService<MeldPost, MeldSearch> {

    private final UserManager userManager;

    @Inject
    public MeldListService(EntityManager entityManager, Identity identity, UserManager userManager) {
        super(entityManager, identity);
        this.userManager = userManager;
    }

    public MeldListService() {
        this(null, null, null);
    }

    public User currentUser() {
        return userManager.current();
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

}
