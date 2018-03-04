package net.portrix.meld.social.profile;

import net.portrix.meld.usercontrol.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Patrick Bittner on 21/12/2016.
 */
@Entity
@Table(name = "so_places")
@NamedQuery(name = "findPlaces", query = "select p from Places p where p.user = :user")
public class Places extends AbstractProfileVisibility {

    @OneToOne
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "places", orphanRemoval = true)
    private final Set<Address> addresses = new HashSet<>();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void addAddress(Address address) {
        address.setPlaces(this);
        addresses.add(address);
    }

    public void clearAddresses() {
        addresses.clear();
    }

}
