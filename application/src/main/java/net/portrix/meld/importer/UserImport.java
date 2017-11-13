package net.portrix.meld.importer;

import net.portrix.meld.usercontrol.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class UserImport {

    @Inject
    private UserManager userManager;

    @Inject
    private EntityManager entityManager;

    @Transactional
    public void execute() {

        final User patrick = new User();
        patrick.setName("patrick");
        patrick.setFirstName("Patrick");
        patrick.setLastName("Bittner");
        patrick.setGender(Gender.MALE);
        patrick.setBirthdate(LocalDate.now().minusYears(35));
        userManager.save(patrick);
        userManager.updatePassword(patrick, "patrick");

        final User martin = new User();
        martin.setName("martin");
        martin.setFirstName("Martin");
        martin.setLastName("Dutkiewicz");
        martin.setGender(Gender.MALE);
        martin.setBirthdate(LocalDate.now().minusYears(36));
        userManager.save(martin);
        userManager.updatePassword(martin, "martin");


        final Group group = new Group();
        group.setName("Administrators");
        group.add(patrick);
        group.add(martin);
        entityManager.persist(group);

        for (int i = 0; i < 100; i++) {
            final Group testGroup = new Group();
            testGroup.setName("Test Group " + String.format("%3d", i));
            entityManager.persist(testGroup);
        }

        final Role admin = new Role();
        admin.setName("Administrator");
        admin.getScopes().add(group);

        entityManager.persist(admin);


        final List<Permission> permissions = entityManager.createNamedQuery("findAllPermissions", Permission.class).getResultList();

        admin.getPermissions().addAll(permissions);


    }
}
