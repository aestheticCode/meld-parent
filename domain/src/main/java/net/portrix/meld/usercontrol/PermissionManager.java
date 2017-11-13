package net.portrix.meld.usercontrol;

import org.picketlink.Identity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.*;

/**
 * @author Patrick Bittner on 29/03/15.
 */
@ApplicationScoped
public class PermissionManager {

    private final EntityManager entityManager;

    private final Identity identity;

    @Inject
    public PermissionManager(final EntityManager entityManager, final Identity identity) {
        this.entityManager = entityManager;
        this.identity = identity;
    }

    protected PermissionManager() {
        this(null, null);
    }

    @Transactional
    public boolean hasPermissions(Permission... permissions) {

        if (identity.isLoggedIn()) {

            final User user;
            try {
                user = entityManager
                        .createQuery("select u from User u where u.externalId = :id", User.class)
                        .setParameter("id", identity.getAccount().getId())
                        .getSingleResult();
            } catch (NoResultException e) {
                return false;
            }

            final List<Group> groups = entityManager
                    .createQuery("select g from Group g join g.members m where m.id = :mId", Group.class)
                    .setParameter("mId", user.getId())
                    .getResultList();

            final List<net.portrix.meld.usercontrol.Identity> identities = new ArrayList<>();
            identities.addAll(groups);
            identities.add(user);

            final List<Role> roles = entityManager
                    .createQuery("select r from Role r join r.scopes s where s in (:identities)", Role.class)
                    .setParameter("identities", identities)
                    .getResultList();

            final Set<Permission> allPermissions = new HashSet<>();
            for (final Role role : roles) {
                allPermissions.addAll(role.getPermissions());
            }

            if (allPermissions.isEmpty()) {
                return false;
            }

            return allPermissions.containsAll(Arrays.asList(permissions));
        } else {
            return false;
        }
    }

    public void save(Permission permission) {
        entityManager.persist(permission);
    }

    public List<Permission> findAll() {
        return entityManager.createQuery("select p from Permission p", Permission.class)
                .getResultList();
    }

}
