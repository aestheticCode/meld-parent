package net.portrix.meld.social.profile;

import net.portrix.generic.rest.api.Link;
import net.portrix.generic.rest.api.LinksContainer;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ProfileForm implements LinksContainer {

    private UUID photoId;

    private Set<Link> links = new HashSet<>();

    public UUID getPhotoId() {
        return photoId;
    }

    public void setPhotoId(UUID photoId) {
        this.photoId = photoId;
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
