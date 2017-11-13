package net.portrix.meld.social.contact;

import net.portrix.generic.rest.Secured;
import net.portrix.generic.rest.jsr339.Name;
import net.portrix.meld.social.Chat;
import net.portrix.meld.social.PersonalContact;
import net.portrix.meld.social.Phone;
import net.portrix.meld.usercontrol.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

/**
 * @author Patrick Bittner on 22/12/2016.
 */
@Path("social")
@ApplicationScoped
@Name("Social")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonalContactController {

    private final PersonalContactService service;

    @Inject
    public PersonalContactController(PersonalContactService service) {
        this.service = service;
    }

    public PersonalContactController() {
        this(null);
    }

    @GET
    @Path("user/current/contact")
    @Name("Personal Contact Save")
    @Secured
    @Transactional
    public PersonalContactForm read() {
        User user = service.currentUser();
        return read(user.getId());
    }

    @GET
    @Path("user/{id}/contact")
    @Name("Personal Contact Save")
    @Secured
    @Transactional
    public PersonalContactForm read(@PathParam("id") UUID id) {

        User user = service.findUser(id);

        PersonalContact contact = service.findPersonalContact(user);

        if (contact == null) {
            return new PersonalContactForm();
        }

        final PersonalContactForm contactResponseType = new PersonalContactForm();
        contactResponseType.setId(contact.getId());

        for (Chat chat : contact.getChats()) {
            ChatForm responseType = new ChatForm();
            responseType.setId(chat.getId());
            responseType.setName(chat.getName());
            responseType.setType(chat.getType());

            contactResponseType.addChat(responseType);
        }

        for (String email : contact.getEmails()) {
            contactResponseType.addEmail(new EmailForm(email));
        }

        for (Phone phone : contact.getPhones()) {
            PhoneForm responseType = new PhoneForm();
            responseType.setId(phone.getId());
            responseType.setNumber(phone.getNumber());
            responseType.setType(phone.getType());

            contactResponseType.addPhone(responseType);
        }

        return contactResponseType;
    }

    @POST
    @Path("user/current/contact")
    @Name("Personal Contact Save")
    @Secured
    @Transactional
    public PersonalContactForm save(PersonalContactForm type) {

        User user = service.currentUser();

        final PersonalContact contact = new PersonalContact();
        contact.setUser(user);

        for (ChatForm chatType : type.getChats()) {
            final Chat chat = new Chat();
            chat.setName(chatType.getName());
            chat.setType(chatType.getType());
            contact.addChat(chat);
        }

        for (EmailForm form : type.getEmails()) {
            contact.addEmail(form.getEmail());
        }

        for (PhoneForm phoneType : type.getPhones()) {
            final Phone phone = new Phone();
            phone.setNumber(phoneType.getNumber());
            phone.setType(phoneType.getType());
            contact.addPhone(phone);
        }

        service.savePersonalContact(contact);

        return read(user.getId());
    }

    @PUT
    @Path("user/current/contact")
    @Name("Personal Contact Save")
    @Secured
    @Transactional
    public PersonalContactForm update(PersonalContactForm type) {

        User user = service.currentUser();

        PersonalContact contact = service.findPersonalContact(user);

        contact.clearChats();
        for (ChatForm chatType : type.getChats()) {
            Chat chat = new Chat();
            chat.setName(chatType.getName());
            chat.setType(chatType.getType());

            contact.addChat(chat);
        }

        contact.clearEmails();
        for (EmailForm form : type.getEmails()) {
            contact.addEmail(form.getEmail());
        }

        contact.clearPhones();
        for (PhoneForm phoneType : type.getPhones()) {
            Phone phone = new Phone();
            phone.setNumber(phoneType.getNumber());
            phone.setType(phoneType.getType());

            contact.addPhone(phone);
        }

        return read(user.getId());

    }


}
