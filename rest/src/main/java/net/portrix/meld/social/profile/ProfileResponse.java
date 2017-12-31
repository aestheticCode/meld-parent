package net.portrix.meld.social.profile;

import com.google.common.collect.Sets;
import net.portrix.generic.rest.api.Link;
import net.portrix.generic.rest.api.LinksContainer;

import java.net.URI;
import java.util.Set;

public class ProfileResponse implements LinksContainer {

    private String name;

    private URI userImage;

    private URI backgroundImage;

    private URI image;

    private URI background;

    private Set<Link> links = Sets.newHashSet();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public URI getUserImage() {
        return userImage;
    }

    public void setUserImage(URI userImage) {
        this.userImage = userImage;
    }

    public URI getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(URI backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public void setLinks(Set<Link> links) {
        this.links = links;
    }

    public URI getImage() {
        return image;
    }

    public void setImage(URI image) {
        this.image = image;
    }

    public URI getBackground() {
        return background;
    }

    public void setBackground(URI background) {
        this.background = background;
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
