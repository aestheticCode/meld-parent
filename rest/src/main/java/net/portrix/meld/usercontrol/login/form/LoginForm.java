package net.portrix.meld.usercontrol.login.form;

import net.portrix.generic.rest.api.Link;
import net.portrix.generic.rest.api.Links;
import net.portrix.generic.rest.api.LinksContainer;

import java.util.HashSet;
import java.util.Set;

/**
 * @author by Patrick Bittner on 12.06.15.
 */
public class LoginForm implements LinksContainer {

    private String email;

    private String password;

    private Set<Link> links = new HashSet<>();

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
