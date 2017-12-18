package net.portrix.meld.social.people.find.table;

import com.google.common.collect.Maps;
import net.portrix.generic.ddd.AbstractQueryService;
import net.portrix.generic.rest.api.query.Query;
import net.portrix.meld.social.people.RelationShip;
import net.portrix.meld.social.profile.Profile;
import net.portrix.meld.usercontrol.User;
import net.portrix.meld.usercontrol.User_;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class FindTableService extends AbstractQueryService<User> {
    
    @Inject
    public FindTableService(EntityManager entityManager) {
        super(entityManager);
    }

    public FindTableService() {
        this(null);
    }

    public Profile findImage(User user) {
        try {
            return entityManager.createQuery("select i from Profile i where i.user = :user", Profile.class)
                    .setParameter("user", user)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public RelationShip findRelationShip(User current, User user) {
        try {
            return entityManager.createQuery("select r from RelationShip r where r.from = :user1 and r.to = :user2", RelationShip.class)
                    .setParameter("user1", current)
                    .setParameter("user2", user)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    public Map<String, Class<?>> getTables() {
        Map<String, Class<?>> tables = Maps.newHashMap();
        tables.put("relationShip", RelationShip.class);
        tables.put("user", User.class);
        return tables;
    }
}
