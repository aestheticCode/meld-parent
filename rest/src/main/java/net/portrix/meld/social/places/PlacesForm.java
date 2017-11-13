package net.portrix.meld.social.places;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Patrick Bittner on 21/12/2016.
 */
public class PlacesForm {

    private UUID id;

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
}
