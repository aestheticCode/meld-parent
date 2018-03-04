package net.portrix.meld.usercontrol.login.form;

import net.portrix.generic.rest.LoginToken;
import net.portrix.meld.usercontrol.User;
import net.portrix.meld.usercontrol.UserManager;
import org.picketlink.Identity;
import org.picketlink.authentication.AuthenticationException;
import org.picketlink.credential.DefaultLoginCredentials;
import org.picketlink.idm.credential.TokenCredential;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.ForbiddenException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.UUID;

@ApplicationScoped
public class LoginFormService {

    private final Identity identity;

    private final DefaultLoginCredentials credentials;

    private final UserManager userManager;

    @Inject
    public LoginFormService(Identity identity, DefaultLoginCredentials credentials, UserManager userManager) {
        this.identity = identity;
        this.credentials = credentials;
        this.userManager = userManager;
    }

    public LoginFormService() {
        this(null, null, null);
    }

    public Identity.AuthenticationResult login(UUID uuid, LoginForm loginForm) throws AuthenticationException  {

        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("ddMMMuuuu")
                .toFormatter();
        String birthday = loginForm.getBirthday().format(formatter);
        String userId = loginForm.getFirstName() + loginForm.getLastName() + birthday;
        credentials.setUserId(userId);
        credentials.setPassword(loginForm.getPassword());

        Identity.AuthenticationResult authenticationResult = identity.login();

        if (authenticationResult.equals(Identity.AuthenticationResult.FAILED)) {
            throw new ForbiddenException();
        }

        User current = userManager.current();

        userManager.updateToken(current, uuid.toString());

        return authenticationResult;

    }


}
