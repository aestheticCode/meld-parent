package net.portrix.meld.social.communities;

import net.portrix.generic.rest.api.Link;
import net.portrix.generic.rest.api.LinksContainer;

import java.util.HashSet;
import java.util.Set;

public class CommunitiesResponse implements LinksContainer {

    private final Set<Link> links = new HashSet<>();

    @Override
    public Set<Link> getLinks() {
        return links;
    }

    @Override
    public boolean addLink(Link link) {
        return links.add(link);
    }

}
