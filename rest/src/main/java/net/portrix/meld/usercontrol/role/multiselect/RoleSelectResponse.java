package net.portrix.meld.usercontrol.role.multiselect;

import net.portrix.generic.rest.api.Link;
import net.portrix.generic.rest.api.Links;
import net.portrix.generic.rest.api.LinksContainer;

import java.util.*;

public class RoleSelectResponse implements LinksContainer {

    private UUID id;

    private String name;

    private List<String> permissions = new ArrayList<>();

    private Set<Link> links = new HashSet<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
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
