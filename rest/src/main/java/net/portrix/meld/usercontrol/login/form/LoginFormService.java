package net.portrix.meld.usercontrol.login.form;

import org.picketlink.Identity;
import org.picketlink.authentication.AuthenticationException;
import org.picketlink.credential.DefaultLoginCredentials;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class LoginFormService {

    private final Identity identity;

    private final DefaultLoginCredentials credentials;

    @Inject
    public LoginFormService(Identity identity, DefaultLoginCredentials credentials) {
        this.identity = identity;
        this.credentials = credentials;
    }

    public LoginFormService() {
        this(null, null);
    }

    public Identity.AuthenticationResult login(LoginForm loginForm) throws AuthenticationException  {

        credentials.setUserId(loginForm.getEmail());
        credentials.setPassword(loginForm.getPassword());

        return identity.login();

    }
}
