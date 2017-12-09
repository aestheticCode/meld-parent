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
        patrick.setName("PatrickBittner1Apr1980");
        patrick.setFirstName("Patrick");
        patrick.setLastName("Bittner");
        patrick.setGender(Gender.MALE);
        patrick.setBirthdate(LocalDate.now().minusYears(35));
        userManager.save(patrick);
        userManager.updatePassword(patrick, "patrick");

        final User martin = new User();
        martin.setName("MartinDutkiewicz21Dec1979");
        martin.setFirstName("Martin");
        martin.setLastName("Dutkiewicz");
        martin.setGender(Gender.MALE);
        martin.setBirthdate(LocalDate.now().minusYears(36));
        userManager.save(martin);
        userManager.updatePassword(martin, "martin");


        final Group administrators = new Group();
        administrators.setName("Administrators");
        administrators.add(patrick);
        administrators.add(martin);
        entityManager.persist(administrators);

        final Role admin = new Role();
        admin.setName("Administrator");
        admin.getScopes().add(administrators);

        entityManager.persist(admin);

        final List<Permission> permissions = entityManager.createNamedQuery("findAllPermissions", Permission.class).getResultList();

        admin.getPermissions().addAll(permissions);

        final Group users = new Group();
        users.setName("Users");
        users.add(patrick);
        users.add(martin);
        entityManager.persist(users);

        final Role user = new Role();
        user.setName("User");
        user.getScopes().add(users);

        entityManager.persist(user);

    }
}
