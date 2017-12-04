package net.portrix.meld.social.profile.user;

import com.google.common.collect.Iterables;
import net.portrix.meld.usercontrol.*;
import org.apache.commons.lang3.StringUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
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
        entityManager.remove(user);
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

    public UserImage findUserImage(User user) {
        return entityManager
                .createQuery("select u from UserImage u where u.user = :user", UserImage.class)
                .setParameter("user", user)
                .getSingleResult();
    }

    public void save(User user) {
        userManager.save(user);
    }

}