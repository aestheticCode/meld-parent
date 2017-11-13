package net.portrix.meld.usercontrol.logout.form;

import org.picketlink.Identity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class LogoutFormService {

    private final Identity identity;

    @Inject
    public LogoutFormService(Identity identity) {
        this.identity = identity;
    }

    public LogoutFormService() {
        this(null);
    }

    public void logout() {
        identity.logout();
    }


}
