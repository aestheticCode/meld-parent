package net.portrix.meld;

import net.portrix.generic.ddd.Module;
import net.portrix.meld.importer.ProfileImport;
import net.portrix.meld.importer.UserImport;

import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

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
    private ProfileImport profileImport;

    @PostConstruct
    public void execute() {

        for (Module module : modules) {
            module.init();
        }

        userImport.execute();

        profileImport.execute();


    }


}
