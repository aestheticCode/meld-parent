package net.portrix.meld;

import net.portrix.generic.ddd.Module;
import net.portrix.meld.importer.UserImport;
import net.portrix.meld.usercontrol.UserManager;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Patrick Bittner on 15/02/15.
 */
@Singleton
@DependsOn("ServerResources")
@Startup
public class DataImport {

    private final static Logger log = LoggerFactory.getLogger(DataImport.class);


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

        String home = System.getProperty("user.home");
        File meld = new File(home + File.separator + ".meld");
        try {
            FileUtils.forceMkdir(meld);

            InputStream screenCapture = Thread
                    .currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("/META-INF/screenCapture.js");

            String pathname = meld.getCanonicalPath() + File.separator + "screenCapture.js";
            FileUtils.copyToFile(screenCapture, new File(pathname));

        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

  /*
        userImport.execute();
  */
        for (Module module : modules) {
            module.init();
        }

    }


}
