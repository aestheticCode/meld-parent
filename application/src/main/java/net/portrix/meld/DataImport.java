package net.portrix.meld;

import net.portrix.meld.usercontrol.*;

import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Patrick Bittner on 15/02/15.
 */
@Singleton
@DependsOn("ServerResources")
@Startup
public class DataImport {

    @Inject
    private UserManager userManager;

    @Inject
    private EntityManager entityManager;

    @PostConstruct
    public void execute() {

        User patrick;
        try {
            patrick = entityManager.createQuery("select u from User u where u.name = :name", User.class)
                    .setParameter("name", "PatrickBittner1Apr1980")
                    .getSingleResult();
        } catch (NoResultException e) {
            patrick = new User();
            patrick.setName("PatrickBittner1Apr1980");
            patrick.setFirstName("Patrick");
            patrick.setLastName("Bittner");
            patrick.setGender(Gender.MALE);
            patrick.setBirthdate(LocalDate.now().minusYears(35));
            userManager.save(patrick);
            userManager.updatePassword(patrick, "patrick");
        }


        User martin;
        try {
            martin = entityManager.createQuery("select u from User u where u.name = :name", User.class)
                    .setParameter("name", "MartinDutkiewicz21Dec1979")
                    .getSingleResult();
        } catch (NoResultException e) {
            martin = new User();
            martin.setName("MartinDutkiewicz21Dec1979");
            martin.setFirstName("Martin");
            martin.setLastName("Dutkiewicz");
            martin.setGender(Gender.MALE);
            martin.setBirthdate(LocalDate.now().minusYears(36));
            userManager.save(martin);
            userManager.updatePassword(martin, "martin");
        }


        final Group group = entityManager.createQuery("select g from Group g where g.name = :name", Group.class)
                .setParameter("name", "Administrators")
                .getSingleResult();

        group.getMembers().add(martin);
        group.getMembers().add(patrick);

        final Role admin = entityManager.createQuery("select g from Role g where g.name = :name", Role.class)
                .setParameter("name", "Administrator")
                .getSingleResult();

        entityManager.persist(admin);

        final List<Permission> permissions = entityManager.createNamedQuery("findAllPermissions", Permission.class).getResultList();

        admin.getPermissions().addAll(permissions);


    }


}
