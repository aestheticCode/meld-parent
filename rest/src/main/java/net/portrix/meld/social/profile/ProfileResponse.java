package net.portrix.meld.social.profile;

import com.google.common.collect.Sets;
import net.portrix.generic.rest.api.Blob;
import net.portrix.generic.rest.api.Link;
import net.portrix.generic.rest.api.LinksContainer;

import java.util.Set;

public class ProfileResponse implements LinksContainer {

    private Blob image;

    private Set<Link> links = Sets.newHashSet();

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
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
