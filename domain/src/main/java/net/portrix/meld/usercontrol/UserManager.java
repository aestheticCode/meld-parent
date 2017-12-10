package net.portrix.meld.usercontrol;

import net.portrix.generic.image.ImageUtils;
import net.portrix.generic.rest.LoginToken;
import org.apache.commons.io.IOUtils;
import org.picketlink.Identity;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.credential.Password;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * @author Patrick Bittner on 20/03/15.
 */
@ApplicationScoped
public class UserManager implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(UserManager.class);

    private final EntityManager entityManager;

    private final IdentityManager identityManager;

    private final Identity identity;

    @Inject
    public UserManager(EntityManager entityManager,
                       IdentityManager identityManager,
                       Identity identity) {
        this.entityManager = entityManager;
        this.identityManager = identityManager;
        this.identity = identity;
    }

    protected UserManager() {
        this(null, null, null);
    }

    public User current() {

        if (identity.isLoggedIn()) {
            final String userId = identity.getAccount().getId();

            return entityManager.createQuery("select u from User u where  u.externalId = :id", User.class)
                    .setParameter("id", userId)
                    .getSingleResult();
        } else {
            throw new RuntimeException("No User loggin in");
        }

    }

    public void save(User user) {

        entityManager.persist(user);

        final org.picketlink.idm.model.basic.User identityType
                = new org.picketlink.idm.model.basic.User(user.getName());

        identityManager.add(identityType);

        user.setExternalId(identityType.getId());

        final InputStream inputStream = Thread
                .currentThread()
                .getContextClassLoader()
                .getResourceAsStream("/META-INF/images/user.png");
        byte[] bytes = null;
        try {
            bytes = IOUtils.toByteArray(inputStream);

            UserImage image = new UserImage();
            image.setUser(user);
            final String fileName = "user.png";
            image.setFileName(fileName);
            image.setImage(bytes);
            image.setThumbnail(ImageUtils.thumnail(fileName, bytes));

            entityManager.persist(image);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }


    }

    public void delete(User user) {

        entityManager.remove(user);

        identityManager.remove(new org.picketlink.idm.model.basic.User(user.getName()));

    }

    public void updatePassword(User user, String password) {
        final List<org.picketlink.idm.model.basic.User> resultList = identityManager.createIdentityQuery(org.picketlink.idm.model.basic.User.class)
                .setParameter(org.picketlink.idm.model.basic.User.LOGIN_NAME, user.getName())
                .getResultList();

        if (resultList.isEmpty()) {
            throw new IllegalStateException("No User found for name " + user.getName());
        }

        if (resultList.size() > 1) {
            throw new IllegalStateException("Multiple User found for name " + user.getName());
        }

        identityManager.updateCredential(resultList.get(0), new Password(password));
    }

    public void updateToken(User user, String token) {
        final List<org.picketlink.idm.model.basic.User> resultList = identityManager.createIdentityQuery(org.picketlink.idm.model.basic.User.class)
                .setParameter(org.picketlink.idm.model.basic.User.LOGIN_NAME, user.getName())
                .getResultList();

        if (resultList.isEmpty()) {
            throw new IllegalStateException("No User found for name " + user.getName());
        }

        if (resultList.size() > 1) {
            throw new IllegalStateException("Multiple User found for name " + user.getName());
        }

        identityManager.updateCredential(resultList.get(0), new LoginToken(user.getName(), token));
    }

    public User find(UUID id) {
        return entityManager.find(User.class, id);
    }
}
