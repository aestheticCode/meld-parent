package net.portrix.meld.social.profile.user.form;

import com.google.common.collect.Iterables;
import net.portrix.meld.social.people.Category;
import net.portrix.meld.social.profile.CategoryFinder;
import net.portrix.meld.social.profile.UserProfile;
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
public class UserFormService implements CategoryFinder {

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

    public UserProfile findUser(UUID id) {
        UserProfile userProfile = new UserProfile();
        try {
            userProfile = entityManager.createNamedQuery("findUserProfileByUserId", UserProfile.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            User user = entityManager.find(User.class, id);
            userProfile.setUser(user);
            entityManager.persist(userProfile);
        }

        return userProfile;
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

    public void save(UserProfile user) {
        userManager.save(user.getUser());
    }

    @Override
    public Category findCategory(UUID id) {
        return entityManager.createNamedQuery("findCategoryById", Category.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public void updateUser(User user) {
        userManager.update(user);
    }
}
