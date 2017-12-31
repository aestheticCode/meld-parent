package net.portrix.meld.channel.meld.like;

import net.portrix.generic.rest.api.Link;
import net.portrix.generic.rest.api.LinksContainer;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Patrick Bittner on 02/12/2016.
 */
public class MeldLikeResponse implements LinksContainer {

    private boolean current;

    private URI avatar;

    private Set<Link> links = new HashSet<>();

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }

    public URI getAvatar() {
        return avatar;
    }

    public void setAvatar(URI avatar) {
        this.avatar = avatar;
    }

    public void setLinks(Set<Link> links) {
        this.links = links;
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
