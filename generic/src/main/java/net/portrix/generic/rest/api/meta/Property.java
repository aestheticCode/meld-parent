package net.portrix.generic.rest.api.meta;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.portrix.generic.rest.api.Link;
import net.portrix.generic.rest.api.LinksContainer;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Patrick Bittner on 17/04/2017.
 */
public class Property implements LinksContainer {

    private final String id;

    private final String name;

    private final String type;

    private Set<Link> links = new HashSet<>();

    @JsonCreator
    public Property(@JsonProperty("id") String id,
                    @JsonProperty("name") String name,
                    @JsonProperty("type") String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
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
