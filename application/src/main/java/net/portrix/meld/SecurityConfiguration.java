package net.portrix.meld;

import org.picketlink.idm.config.IdentityConfiguration;
import org.picketlink.idm.config.IdentityConfigurationBuilder;
import org.picketlink.idm.credential.encoder.BCryptPasswordEncoder;
import org.picketlink.idm.jpa.internal.JPAIdentityStore;
import org.picketlink.idm.jpa.model.sample.simple.*;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.picketlink.idm.credential.handler.PasswordCredentialHandler.PASSWORD_ENCODER;

/**
 * @author Patrick Bittner on 28/01/15.
 */
public class SecurityConfiguration {

    @Produces
    @PersistenceContext(unitName = "main")
    private EntityManager entityManager;

    @Produces
    private IdentityConfiguration produceJPAConfiguration() {
        IdentityConfigurationBuilder builder = new IdentityConfigurationBuilder();
        builder
                .named("default")
                .stores()
                .jpa()
                .addContextInitializer((context, store) -> {
                    if (store instanceof JPAIdentityStore) {
                        context.setParameter(JPAIdentityStore.INVOCATION_CTX_ENTITY_MANAGER, entityManager);
                    }
                })
                .setCredentialHandlerProperty(PASSWORD_ENCODER, new BCryptPasswordEncoder(12))
                .mappedEntity(
                        AccountTypeEntity.class,
                        RoleTypeEntity.class,
                        GroupTypeEntity.class,
                        IdentityTypeEntity.class,
                        RelationshipTypeEntity.class,
                        RelationshipIdentityTypeEntity.class,
                        PartitionTypeEntity.class,
                        PasswordCredentialTypeEntity.class,
                        TokenCredentialTypeEntity.class,
                        AttributeTypeEntity.class
                )
                .supportAllFeatures();
        return builder.build();
    }

}
