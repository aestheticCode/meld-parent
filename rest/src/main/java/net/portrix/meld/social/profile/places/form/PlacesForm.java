package net.portrix.meld.social.profile.places.form;

import net.portrix.generic.rest.api.Link;
import net.portrix.generic.rest.api.LinksContainer;

import java.util.*;

/**
 * @author Patrick Bittner on 21/12/2016.
 */
public class PlacesForm implements LinksContainer {

    private UUID id;

    private final Set<Link> links = new HashSet<>();

    private final List<AddressForm> addresses = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<AddressForm> getAddresses() {
        return addresses;
    }

    public void addAddress(AddressForm responseType) {
        addresses.add(responseType);
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
