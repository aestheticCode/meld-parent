package net.portrix.meld.social.profile.education;

import net.portrix.generic.rest.google.details.LocationDetailForm;

import java.util.UUID;

/**
 * @author Patrick Bittner on 21/12/2016.
 */
public class SchoolForm {

    private UUID id;

    private String name;

    private LocationDetailForm location;

    private String course;

    private SchoolDateForm startYear;

    private SchoolDateForm endYear;

    private SchoolDateForm visitStart;

    private SchoolDateForm visitEnd;

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

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public SchoolDateForm getStartYear() {
        return startYear;
    }

    public void setStartYear(SchoolDateForm startYear) {
        this.startYear = startYear;
    }

    public SchoolDateForm getEndYear() {
        return endYear;
    }

    public void setEndYear(SchoolDateForm endYear) {
        this.endYear = endYear;
    }

    public SchoolDateForm getVisitStart() {
        return visitStart;
    }

    public void setVisitStart(SchoolDateForm visitStart) {
        this.visitStart = visitStart;
    }

    public SchoolDateForm getVisitEnd() {
        return visitEnd;
    }

    public void setVisitEnd(SchoolDateForm visitEnd) {
        this.visitEnd = visitEnd;
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
