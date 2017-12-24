package net.portrix.meld.usercontrol.role.form;

import com.google.common.collect.Iterables;
import net.portrix.meld.usercontrol.Permission;
import net.portrix.meld.usercontrol.Role;
import net.portrix.meld.usercontrol.Role_;
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
public class RoleFormService {

    private final EntityManager entityManager;

    @Inject
    public RoleFormService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public RoleFormService() {
        this(null);
    }

    public Role findRole(UUID id) {
        return entityManager.find(Role.class, id);
    }

    public void saveRole(Role role) {
        entityManager.persist(role);
    }

    public void deleteRole(Role role) {
        entityManager.remove(role);
    }

    public List<Permission> findAllPermissions() {
        return entityManager
                    .createNamedQuery("findAllPermissions", Permission.class)
                    .getResultList();
    }

    public boolean validateName(RoleNameValidation validation) {
        if (StringUtils.isEmpty(validation.getName())) {
            return true;
        }
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Long> query = builder.createQuery(Long.class);
        final Root<Role> root = query.from(Role.class);
        query.select(builder.count(root));
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.like(builder.lower(root.get(Role_.name)), validation.getName().toLowerCase()));

        if (validation.getId() != null) {
            predicates.add(builder.notEqual(root.get(Role_.id), validation.getId()));
        }

        query.where(Iterables.toArray(predicates, Predicate.class));

        return entityManager.createQuery(query)
                .getSingleResult() == 0;
    }

}
