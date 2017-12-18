package net.portrix.meld.social.people.category.form;

import com.google.common.collect.Sets;
import net.portrix.generic.rest.api.Link;
import net.portrix.generic.rest.api.LinksContainer;

import java.util.Set;
import java.util.UUID;

public class CategoryForm implements LinksContainer {

    private UUID id;

    private String name;

    private final Set<Link> links = Sets.newHashSet();

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

    @Override
    public Set<Link> getLinks() {
        return links;
    }

    @Override
    public boolean addLink(Link link) {
        return links.add(link);
    }
}
