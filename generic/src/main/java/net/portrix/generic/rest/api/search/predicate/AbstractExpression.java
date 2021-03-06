package net.portrix.generic.rest.api.search.predicate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Sets;
import net.portrix.generic.rest.api.Link;
import net.portrix.generic.rest.api.LinksContainer;

import java.util.Set;

public abstract class AbstractExpression implements RestExpression, LinksContainer {

    private final Set<Link> links;

    @JsonCreator
    public AbstractExpression(@JsonProperty("links") Link... links) {
        this.links = Sets.newHashSet(links);
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
