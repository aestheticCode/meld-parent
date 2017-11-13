package net.portrix.meld.usercontrol.permission.table;

import net.portrix.generic.rest.api.Link;
import net.portrix.generic.rest.api.Links;
import net.portrix.generic.rest.api.LinksContainer;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author Patrick Bittner on 01/10/16.
 */
public class PermissionRowResponse implements LinksContainer {

    private UUID id;

    private String name;

    private String path;

    private String method;

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }


    @Override
    public Set<Link> getLinks() {
        return links;
    }

    public void setLinks(Set<Link> links) {
        this.links = links;
    }

    @Override
    public boolean addLink(Link link) {
        return links.add(link);
    }
}
