package net.portrix.meld.usercontrol.group.form;

import com.google.common.collect.Iterables;
import net.portrix.meld.usercontrol.Group;
import net.portrix.meld.usercontrol.Group_;
import net.portrix.meld.usercontrol.Role;
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
public class GroupFormService {

    private final EntityManager entityManager;

    @Inject
    public GroupFormService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public GroupFormService() {
        this(null);
    }

    public List<Role> findRoles() {
        return entityManager
                .createNamedQuery("findAllRoles", Role.class)
                .getResultList();
    }

    public Group findGroup(UUID id) {
        return entityManager.find(Group.class, id);
    }

    public void save(Group group) {
        entityManager.persist(group);
    }

    public void delete(Group group) {
        entityManager.remove(group);
    }

    boolean validateName(GroupNameValidation validation) {
        if (StringUtils.isEmpty(validation.getName())) {
            return true;
        }
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Long> query = builder.createQuery(Long.class);
        final Root<Group> root = query.from(Group.class);
        query.select(builder.count(root));
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.like(builder.lower(root.get(Group_.name)), validation.getName().toLowerCase()));

        if (validation.getId() != null) {
            predicates.add(builder.notEqual(root.get(Group_.id), validation.getId()));
        }

        query.where(Iterables.toArray(predicates, Predicate.class));

        return entityManager.createQuery(query)
                .getSingleResult() == 0;
    }
}
