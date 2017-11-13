package net.portrix.generic.rest.api;

import java.util.Set;

public interface LinksContainer {

    Set<Link> getLinks();

    boolean addLink(Link link);

}
