package net.portrix.meld.social.profile;

import net.portrix.meld.usercontrol.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrick Bittner on 21/12/2016.
 */
@Entity
@Table(name = "so_personalContact")
@NamedQuery(name = "findPersonalContact", query = "select p from PersonalContact p where p.user = :user")
public class PersonalContact extends AbstractProfileVisibility {

    @OneToOne
    private User user;

    @OrderColumn
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contact", orphanRemoval = true)
    private final List<Phone> phones = new ArrayList<>();

    @OrderColumn
    @ElementCollection
    private final List<String> emails = new ArrayList<>();

    @OrderColumn
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contact", orphanRemoval = true)
    private final List<Chat> chats = new ArrayList<>();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public List<String> getEmails() {
        return emails;
    }

    public List<Chat> getChats() {
        return chats;
    }

    public void addChat(Chat chat) {
        chat.setContact(this);
        chats.add(chat);
    }

    public void addEmail(String email) {
        emails.add(email);
    }

    public void addPhone(Phone phone) {
        phone.setContact(this);
        phones.add(phone);
    }

    public void clearChats() {
        chats.clear();
    }

    public void clearEmails() {
        emails.clear();
    }

    public void clearPhones() {
        phones.clear();
    }

}
