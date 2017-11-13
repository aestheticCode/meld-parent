package net.portrix.meld;

import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.inject.Produces;

/**
 * @author Patrick Bittner on 10.05.2014.
 */
@Singleton
public class ServerResources {


    @Resource
    private ManagedExecutorService executor;

    @Produces
    public ManagedExecutorService getExecutor() {
        return executor;
    }

}
