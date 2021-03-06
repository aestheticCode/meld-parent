package net.portrix.meld.social.profile;

import net.portrix.generic.ddd.AbstractEntity;
import net.portrix.meld.usercontrol.User;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * @author Patrick Bittner on 21/12/2016.
 */
@Entity
@Table(name = "so_address")
@NamedQuery(name = "findAddress", query = "select a from Address a where id = :id")
public class Address extends AbstractEntity {

    @AttributeOverrides({
            @AttributeOverride(name="id", column=@Column(name = "place_id")),
            @AttributeOverride(name="name", column=@Column(name = "place_name")),
            @AttributeOverride(name="street", column=@Column(name = "place_street")),
            @AttributeOverride(name="streetNumber", column=@Column(name = "place_street_number")),
            @AttributeOverride(name="zipCode", column=@Column(name = "place_zipCode")),
            @AttributeOverride(name="state", column=@Column(name = "place_state")),
            @AttributeOverride(name="country", column=@Column(name = "place_country")),
            @AttributeOverride(name="lat", column=@Column(name = "place_lat")),
            @AttributeOverride(name="lng", column=@Column(name = "place_lng"))

    })
    @Embedded
    private Place place;

    @Column(name = "startDate")
    private LocalDate start;

    @Column(name = "endDate")
    private LocalDate end;

    private boolean tillNow;

    @ManyToOne
    private Places places;

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public boolean isTillNow() {
        return tillNow;
    }

    public void setTillNow(boolean tillNow) {
        this.tillNow = tillNow;
    }

    public Places getPlaces() {
        return places;
    }

    public void setPlaces(Places places) {
        this.places = places;
    }
}
