package net.portrix.meld.media.photos.grid;

import net.portrix.generic.ddd.AbstractSearchService;
import net.portrix.meld.media.photos.Photo;
import net.portrix.meld.media.photos.Photo_;
import net.portrix.meld.usercontrol.User;
import org.picketlink.Identity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@ApplicationScoped
public class PhotoGridService extends AbstractSearchService<Photo, PhotoSearch> {

    @Inject
    public PhotoGridService(EntityManager entityManager, Identity identity) {
        super(entityManager, identity);
    }

    public PhotoGridService() {
        this(null, null);
    }

    @Override
    public Predicate filter(CriteriaBuilder builder, Root<Photo> root, CriteriaQuery<?> query) {
        User user = entityManager.createNamedQuery("findUserByExternal", User.class)
                .setParameter("id", identity.getAccount().getId())
                .getSingleResult();

        return builder.equal(root.get(Photo_.user), user);
    }
}
