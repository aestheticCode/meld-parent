package net.portrix.meld.social.people.find.table.search.expression;

import com.google.common.collect.Sets;
import net.portrix.generic.rest.api.Link;

import java.util.Set;

public abstract class AbstractExpression implements RestExpression {

    private final Set<Link> links;

    public AbstractExpression(Link... links) {
        if (links == null) {
            this.links = Sets.newHashSet();
        } else {
            this.links = Sets.newHashSet(links);
        }
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
