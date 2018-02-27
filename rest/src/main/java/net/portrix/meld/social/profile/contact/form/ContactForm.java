package net.portrix.meld.social.profile.contact.form;

import net.portrix.generic.rest.api.Link;
import net.portrix.generic.rest.api.LinksContainer;

import java.util.*;

/**
 * @author Patrick Bittner on 21/12/2016.
 */
public class ContactForm implements LinksContainer {

    private UUID id;

    private final List<PhoneForm> phones = new ArrayList<>();

    private final List<EmailForm> emails = new ArrayList<>();

    private final List<ChatForm> chats = new ArrayList<>();

    private final Set<Link> links = new HashSet<>();

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

    @Override
    public Set<Link> getLinks() {
        return links;
    }

    @Override
    public boolean addLink(Link link) {
        return links.add(link);
    }
}
