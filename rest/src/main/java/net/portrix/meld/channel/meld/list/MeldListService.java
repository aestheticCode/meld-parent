package net.portrix.meld.channel.meld.list;

import com.google.common.collect.Maps;
import net.portrix.generic.ddd.AbstractQueryService;
import net.portrix.generic.rest.api.query.Query;
import net.portrix.meld.channel.MeldPost;
import net.portrix.meld.channel.MeldPost_;
import net.portrix.meld.social.people.RelationShip;
import net.portrix.meld.social.profile.Profile;
import net.portrix.meld.usercontrol.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Map;

/**
 * @author Patrick Bittner on 09.08.17.
 */
@ApplicationScoped
public class MeldListService extends AbstractQueryService<MeldPost> {

    private final UserManager userManager;

    @Inject
    public MeldListService(EntityManager entityManager, UserManager userManager) {
        super(entityManager);
        this.userManager = userManager;
    }

    public MeldListService() {
        this(null, null);
    }

    public UserImage findUserImage(User user) {
        return entityManager.createNamedQuery("findUserImage", UserImage.class)
                .setParameter("user", user)
                .getSingleResult();
    }

    public User currentUser() {
        return userManager.current();
    }

    public Profile findProfile(User user) {
        try {
            return entityManager.createQuery("select p from Profile p where p.user = :user", Profile.class)
                    .setParameter("user", user)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Class<MeldPost> getEntityClass() {
        return MeldPost.class;
    }

    @Override
    public Map<String, Class<?>> getTables() {
        Map<String, Class<?>> tables = Maps.newHashMap();
        tables.put("relationShip", RelationShip.class);
        tables.put("user", User.class);
        return tables;
    }


}
