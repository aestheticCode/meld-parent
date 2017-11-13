package net.portrix.meld;

import org.picketlink.idm.config.IdentityConfiguration;
import org.picketlink.idm.config.IdentityConfigurationBuilder;
import org.picketlink.idm.jpa.internal.JPAIdentityStore;
import org.picketlink.idm.jpa.model.sample.simple.*;
import org.picketlink.idm.spi.ContextInitializer;
import org.picketlink.idm.spi.IdentityContext;
import org.picketlink.idm.spi.IdentityStore;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Patrick Bittner on 28/01/15.
 */
public class SecurityConfiguration {

    @Produces
    @PersistenceContext(unitName = "main")
    private EntityManager entityManager;

    @Produces
    private IdentityConfiguration produceConfiguration() {
        IdentityConfigurationBuilder builder = new IdentityConfigurationBuilder();
        builder
                .named("default")
                .stores()
                .jpa()
                .addContextInitializer(new ContextInitializer() {
                    @Override
                    public void initContextForStore(IdentityContext context, IdentityStore<?> store) {
                        if (store instanceof JPAIdentityStore) {
                            context.setParameter(JPAIdentityStore.INVOCATION_CTX_ENTITY_MANAGER, entityManager);
                        }
                    }
                })
                .mappedEntity(
                        AccountTypeEntity.class,
                        RoleTypeEntity.class,
                        GroupTypeEntity.class,
                        IdentityTypeEntity.class,
                        RelationshipTypeEntity.class,
                        RelationshipIdentityTypeEntity.class,
                        PartitionTypeEntity.class,
                        PasswordCredentialTypeEntity.class,
                        AttributeTypeEntity.class
                )

                        // Specify that this identity store configuration supports all features
                .supportAllFeatures();
        return builder.build();
    }

}
