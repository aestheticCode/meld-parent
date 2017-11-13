package net.portrix.meld;

import net.portrix.generic.rest.api.Link;
import net.portrix.generic.rest.api.LinksContainer;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author Patrick Bittner on 28.07.17.
 */
public class Application implements LinksContainer {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private Set<Link> links = new HashSet<>();

    public Set<Link> getLinks() {
        return links;
    }

    @Override
    public boolean addLink(Link link) {
        return links.add(link);
    }

    public void setLinks(Set<Link> links) {
        this.links = links;
    }

    public static class User {

        private UUID id;

        private String email;

        private String firstName;

        private String lastName;

        private LocalDate birthday;

        private Link avatar;

        public UUID getId() {
            return id;
        }

        public void setId(UUID id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public LocalDate getBirthday() {
            return birthday;
        }

        public void setBirthday(LocalDate birthday) {
            this.birthday = birthday;
        }

        public Link getAvatar() {
            return avatar;
        }

        public void setAvatar(Link avatar) {
            this.avatar = avatar;
        }
    }
}
