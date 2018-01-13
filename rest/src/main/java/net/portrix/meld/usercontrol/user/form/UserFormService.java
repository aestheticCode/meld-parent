package net.portrix.meld.usercontrol.user.form;

import com.google.common.collect.Iterables;
import net.portrix.meld.media.photos.Photo;
import net.portrix.meld.social.profile.Profile;
import net.portrix.meld.usercontrol.*;
import org.apache.commons.lang.StringUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class UserFormService {

    private final UserManager userManager;

    private final EntityManager entityManager;

    @Inject
    public UserFormService(UserManager userManager, EntityManager entityManager) {
        this.userManager = userManager;
        this.entityManager = entityManager;
    }

    public UserFormService() {
        this(null, null);
    }

    public User currentUser() {
        return userManager.current();
    }

    public void deleteUser(User user) {
        userManager.delete(user);
    }

    public User findUser(UUID id) {
        return entityManager.find(User.class, id);
    }


    public List<Role> findAllRoles(User user) {
        return entityManager.createQuery("select r from Role r join r.scopes i where i = :user", Role.class)
                .setParameter("user", user)
                .getResultList();
    }


    public List<Role> findAllRoles() {
        return entityManager
                    .createNamedQuery("findAllRoles", Role.class)
                    .getResultList();
    }

    public List<Group> findAllGroups(User user) {
        return entityManager.createQuery("select g from Group g join g.members i where i = :user", Group.class)
                .setParameter("user", user)
                .getResultList();
    }

    public List<Group> findAllGroups() {
        return entityManager
                    .createNamedQuery("findAllGroups", Group.class)
                    .getResultList();
    }

    public boolean validateUserName(UserNameValidation validation) {
        if (StringUtils.isEmpty(validation.getName())) {
            return true;
        }
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Long> query = builder.createQuery(Long.class);
        final Root<User> root = query.from(User.class);
        query.select(builder.count(root));
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.like(builder.lower(root.get(User_.name)), validation.getName().toLowerCase()));

        if (validation.getId() != null) {
            predicates.add(builder.notEqual(root.get(User_.id), validation.getId()));
        }

        query.where(Iterables.toArray(predicates, Predicate.class));

        return entityManager.createQuery(query)
                .getSingleResult() == 0;
    }

    public Profile findProfile(User user) {
        try {
            return entityManager
                    .createNamedQuery("findProfileByUser", Profile.class)
                    .setParameter("user", user)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void save(User user) {
        userManager.save(user);
    }

    public void savePhoto(Photo photo) {
        entityManager.persist(photo);
    }
}
