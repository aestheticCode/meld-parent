package net.portrix.meld.channel.meld.list;

import com.google.common.collect.Maps;
import net.portrix.generic.rest.api.query.Query;
import net.portrix.meld.channel.MeldImagePost;
import net.portrix.meld.channel.MeldPost;
import net.portrix.meld.channel.MeldPost_;
import net.portrix.meld.social.people.RelationShip;
import net.portrix.meld.usercontrol.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

/**
 * @author Patrick Bittner on 09.08.17.
 */
@ApplicationScoped
public class MeldListService {

    private final UserManager userManager;

    private final EntityManager entityManager;

    private final Map<String, Class<?>> tables = Maps.newHashMap();

    {
        tables.put("relationShip", RelationShip.class);
        tables.put("user", User.class);
    }

    @Inject
    public MeldListService(UserManager userManager, EntityManager entityManager) {
        this.userManager = userManager;
        this.entityManager = entityManager;
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

    public List<MeldPost> find(Query search) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<MeldPost> query = builder.createQuery(MeldPost.class);
        Root<MeldPost> root = query.from(MeldPost.class);
        Predicate predicate = search.getPredicate().accept(Query.visitorVisit(query, builder, root, tables));
        query.select(root).where(predicate).orderBy(builder.asc(root.get(MeldPost_.created)));
        TypedQuery<MeldPost> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(search.getIndex());
        typedQuery.setMaxResults(search.getLimit());
        return typedQuery.getResultList();
    }

    long count(Query search) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<MeldPost> root = query.from(MeldPost.class);
        Predicate predicate = search.getPredicate().accept(Query.visitorVisit(query, builder, root, tables));
        query.select(builder.count(root)).where(predicate);
        TypedQuery<Long> typedQuery = entityManager.createQuery(query);
        return typedQuery.getSingleResult();
    }

    public void facebook() {

    }
}
