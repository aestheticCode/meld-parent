package net.portrix.meld.social.profile.user;

import com.google.common.collect.Iterables;
import net.portrix.meld.usercontrol.*;
import org.apache.commons.lang.StringUtils;

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

    public void save(User user) {
        userManager.save(user);
    }

}
