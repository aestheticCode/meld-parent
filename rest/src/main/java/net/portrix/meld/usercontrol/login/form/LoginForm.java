package net.portrix.meld.usercontrol.login.form;

import net.portrix.generic.rest.api.Link;
import net.portrix.generic.rest.api.Links;
import net.portrix.generic.rest.api.LinksContainer;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * @author by Patrick Bittner on 12.06.15.
 */
public class LoginForm implements LinksContainer {

    private String firstName;

    private String lastName;

    private LocalDate birthday;

    private String password;

    private Set<Link> links = new HashSet<>();

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
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
}
