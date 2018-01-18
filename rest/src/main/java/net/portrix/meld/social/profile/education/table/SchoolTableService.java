package net.portrix.meld.social.profile.education.table;

import com.google.common.collect.Maps;
import net.portrix.generic.ddd.AbstractQueryService;
import net.portrix.meld.social.profile.School;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Map;

@ApplicationScoped
public class SchoolTableService extends AbstractQueryService<School> {

    @Inject
    public SchoolTableService(EntityManager entityManager) {
        super(entityManager);
    }

    public SchoolTableService() {
        this(null);
    }

    @Override
    public Class<School> getEntityClass() {
        return School.class;
    }

    @Override
    public Map<String, Class<?>> getTables() {
        return Maps.newHashMap();
    }
}
