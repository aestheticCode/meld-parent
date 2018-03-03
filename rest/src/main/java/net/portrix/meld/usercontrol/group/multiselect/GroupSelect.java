package net.portrix.meld.usercontrol.group.multiselect;

import net.portrix.generic.rest.api.AbstractRestEntity;
import net.portrix.generic.rest.api.Link;
import net.portrix.generic.rest.api.LinksContainer;

import java.util.*;

public class GroupSelect extends AbstractRestEntity implements LinksContainer {

    private String name;

    private Set<Link> links = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
