package net.portrix.meld.social.contact;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Patrick Bittner on 21/12/2016.
 */
public class PersonalContactForm {

    private UUID id;

    private final List<PhoneForm> phones = new ArrayList<>();

    private final List<EmailForm> emails = new ArrayList<>();

    private final List<ChatForm> chats = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<PhoneForm> getPhones() {
        return phones;
    }

    public List<EmailForm> getEmails() {
        return emails;
    }

    public List<ChatForm> getChats() {
        return chats;
    }

    public void addChat(ChatForm responseType) {
        chats.add(responseType);
    }

    public void addEmail(EmailForm email) {
        emails.add(email);
    }

    public void addPhone(PhoneForm responseType) {
        phones.add(responseType);
    }
}
