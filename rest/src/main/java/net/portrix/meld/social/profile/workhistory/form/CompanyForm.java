package net.portrix.meld.social.profile.workhistory.form;

import net.portrix.generic.rest.google.details.LocationDetailForm;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Patrick Bittner on 21/12/2016.
 */
public class CompanyForm {

    private UUID id;

    private String name;

    private LocationDetailForm location;

    private String title;

    private LocalDate start;

    private LocalDate end;

    private boolean tillNow;

    private String description;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocationDetailForm getLocation() {
        return location;
    }

    public void setLocation(LocationDetailForm location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
