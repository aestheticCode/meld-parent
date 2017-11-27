package net.portrix.meld.social.profile;

import net.portrix.generic.ddd.AbstractEntity;
import net.portrix.meld.usercontrol.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrick Bittner on 21/12/2016.
 */
@Entity
@Table(name = "so_places")
@NamedQuery(name = "findPlaces", query = "select p from Places p where p.user = :user")
public class Places extends AbstractEntity {

    @OneToOne
    private User user;

    @OrderColumn
    @OneToMany(cascade = CascadeType.ALL)
    private final List<Address> addresses = new ArrayList<>();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void addAddress(Address address) {
        addresses.add(address);
    }

    public void clearAddresses() {
        addresses.clear();
    }

}
