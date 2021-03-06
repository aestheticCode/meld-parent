package net.portrix.meld.social.profile.contact.form;

import net.portrix.generic.phone.PhoneNumbers;
import net.portrix.meld.social.people.Category;
import net.portrix.meld.social.profile.CategoryFinder;
import net.portrix.meld.social.profile.PersonalContact;
import net.portrix.meld.usercontrol.User;
import net.portrix.meld.usercontrol.UserManager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.UUID;

@ApplicationScoped
public class ContactFormService implements CategoryFinder {

    private final PhoneNumbers phoneNumbers;

    private final UserManager userManager;

    private final EntityManager entityManager;

    @Inject
    public ContactFormService(PhoneNumbers phoneNumbers, UserManager userManager, EntityManager entityManager) {
        this.phoneNumbers = phoneNumbers;
        this.userManager = userManager;
        this.entityManager = entityManager;
    }

    public ContactFormService() {
        this(null, null, null);
    }

    public PersonalContact findPersonalContact(User user) {
        try {
            return entityManager.createNamedQuery("findPersonalContact", PersonalContact.class)
                    .setParameter("user", user)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public User findUser(UUID id) {
        return entityManager.find(User.class, id);
    }

    public void savePersonalContact(PersonalContact contact) {
        entityManager.persist(contact);
    }

    public User currentUser() {
        return userManager.current();
    }

    public void delete(PersonalContact contact) {
        entityManager.remove(contact);
    }

    public String formatPhoneNumber(String number) {
        return phoneNumbers.format(number);
    }

    @Override
    public Category findCategory(UUID id) {
        return entityManager.createNamedQuery("findCategoryById", Category.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
