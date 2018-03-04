package net.portrix.meld.usercontrol;

import net.portrix.generic.image.ImageUtils;
import net.portrix.generic.rest.LoginToken;
import org.apache.commons.io.IOUtils;
import org.picketlink.Identity;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.credential.Password;
import org.picketlink.idm.query.Condition;
import org.picketlink.idm.query.IdentityQuery;
import org.picketlink.idm.query.IdentityQueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
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

    public void update(User user) {

        org.picketlink.idm.model.basic.User picketLinkUser = findUser(user);
        user.updateName();
        picketLinkUser.setLoginName(user.getName());

        identityManager.update(picketLinkUser);

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
            image.setThumbnail(ImageUtils.thumnail(fileName, bytes, 100));

            entityManager.persist(image);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }


    }

    public void delete(User user) {

        UserImage image = entityManager.createQuery("select i from UserImage i where i.user = :user", UserImage.class)
                .setParameter("user", user)
                .getSingleResult();

        entityManager.remove(image);

        entityManager.remove(user);

        org.picketlink.idm.model.basic.User user1 = findUser(user);

        identityManager.remove(user1);

    }

    public void updatePassword(User user, String password) {
        org.picketlink.idm.model.basic.User resultList = findUser(user);

        identityManager.updateCredential(resultList, new Password(password));
    }

    public void updateToken(User user, String token) {
        org.picketlink.idm.model.basic.User user1 = findUser(user);

        identityManager.updateCredential(user1, new LoginToken(user.getName(), token));
    }

    public User find(UUID id) {
        return entityManager.find(User.class, id);
    }

    private org.picketlink.idm.model.basic.User findUser(User user) {
        IdentityQueryBuilder builder = identityManager.getQueryBuilder();
        Condition equal = builder.equal(org.picketlink.idm.model.basic.User.ID, user.getExternalId());
        IdentityQuery<org.picketlink.idm.model.basic.User> identityQuery = builder.createIdentityQuery(org.picketlink.idm.model.basic.User.class);
        identityQuery.where(equal);
        List<org.picketlink.idm.model.basic.User> resultList = identityQuery.getResultList();

        if (resultList.isEmpty()) {
            throw new IllegalStateException("No User found for name " + user.getName());
        }

        if (resultList.size() > 1) {
            throw new IllegalStateException("Multiple User found for name " + user.getName());
        }
        return resultList.get(0);
    }

}
