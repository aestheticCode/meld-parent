package net.portrix.meld.social.profile.places;

import net.portrix.generic.rest.google.details.LocationDetailForm;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Patrick Bittner on 21/12/2016.
 */
public class AddressForm {

    private UUID id;

    private LocationDetailForm location;

    private LocalDate start;

    private LocalDate end;

    private boolean tillNow;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocationDetailForm getLocation() {
        return location;
    }

    public void setLocation(LocationDetailForm location) {
        this.location = location;
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
}
