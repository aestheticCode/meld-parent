package net.portrix.meld.usercontrol.registration.form;

import net.portrix.meld.usercontrol.Group;
import net.portrix.meld.usercontrol.User;
import net.portrix.meld.usercontrol.UserManager;
import org.picketlink.Identity;
import org.picketlink.authentication.AuthenticationException;
import org.picketlink.credential.DefaultLoginCredentials;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

@ApplicationScoped
public class RegistrationFormService {

    private final Identity identity;

    private final UserManager userManager;

    private final DefaultLoginCredentials credentials;

    private final EntityManager entityManager;

    @Inject
    public RegistrationFormService(Identity identity, UserManager userManager, DefaultLoginCredentials credentials, EntityManager entityManager) {
        this.identity = identity;
        this.userManager = userManager;
        this.credentials = credentials;
        this.entityManager = entityManager;
    }

    public RegistrationFormService() {
        this(null, null, null, null);
    }

    public Identity.AuthenticationResult register(RegistrationForm registrationForm) throws AuthenticationException  {

        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("dMMMuuuu")
                .toFormatter();
        String birthday = registrationForm.getBirthday().format(formatter);
        String userId = registrationForm.getFirstName() + registrationForm.getLastName() + birthday;

        User user = new User();
        user.setName(userId);
        user.setFirstName(registrationForm.getFirstName());
        user.setLastName(registrationForm.getLastName());
        user.setBirthdate(registrationForm.getBirthday());
        userManager.save(user);

        Group group = entityManager.createQuery("select g from Group g where g.name = :name", Group.class)
                .setParameter("name", "Users")
                .getSingleResult();

        group.getMembers().add(user);

        userManager.updatePassword(user, registrationForm.getPassword());

        credentials.setUserId(userId);
        credentials.setPassword(registrationForm.getPassword());

        return identity.login();

    }
}
