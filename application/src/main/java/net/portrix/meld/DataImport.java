package net.portrix.meld;

import net.portrix.generic.ddd.Module;
import net.portrix.meld.importer.UserImport;
import net.portrix.meld.usercontrol.User;
import net.portrix.meld.usercontrol.UserManager;

import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * @author Patrick Bittner on 15/02/15.
 */
@Singleton
@DependsOn("ServerResources")
@Startup
public class DataImport {


    @Inject
    private Instance<Module> modules;

    @Inject
    private UserImport userImport;

    @Inject
    private EntityManager entityManager;

    @Inject
    private UserManager userManager;


    @PostConstruct
    public void execute() {


     /*   User patrick = entityManager.createQuery("select u from User u where u.name = :name", User.class)
                .setParameter("name", "PatrickBittner1Apr1980")
                .getSingleResult();
        userManager.updateToken(patrick, "patrick");

        User martin = entityManager.createQuery("select u from User u where u.name = :name", User.class)
                .setParameter("name", "MartinDutkiewicz21Dec1979")
                .getSingleResult();
        userManager.updateToken(martin, "martin");
*/


  /*      for (Module module : modules) {
            module.init();
        }

        userImport.execute();
  */
    }


}
