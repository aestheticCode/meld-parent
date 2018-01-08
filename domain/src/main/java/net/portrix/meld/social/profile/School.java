package net.portrix.meld.social.profile;

import net.portrix.generic.ddd.AbstractEntity;

import javax.persistence.*;

/**
 * @author Patrick Bittner on 21/12/2016.
 */
@Entity
@Table(name = "so_school")
@NamedQuery(name = "findSchoolNames", query = "select distinct s.name from School s where lower(s.name) like :name order by s.name asc")
public class School extends AbstractEntity {

    private String name;

    @Embedded
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
    private Place place;

    private String course;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="semester", column=@Column(name = "yearStartSemester")),
            @AttributeOverride(name="year", column=@Column(name = "yearStartYear"))
    })
    private SchoolDate startYear;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="semester", column=@Column(name = "yearEndSemester")),
            @AttributeOverride(name="year", column=@Column(name = "yearEndYear"))
    })
    private SchoolDate endYear;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="semester", column=@Column(name = "visitStartSemester")),
            @AttributeOverride(name="year", column=@Column(name = "visitStartYear"))
    })
    private SchoolDate visitStart;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="semester", column=@Column(name = "visitEndSemester")),
            @AttributeOverride(name="year", column=@Column(name = "visitEndYear"))
    })
    private SchoolDate visitEnd;


    private boolean tillNow;

    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public SchoolDate getStartYear() {
        return startYear;
    }

    public void setStartYear(SchoolDate start) {
        this.startYear = start;
    }

    public SchoolDate getEndYear() {
        return endYear;
    }

    public void setEndYear(SchoolDate end) {
        this.endYear = end;
    }

    public SchoolDate getVisitStart() {
        return visitStart;
    }

    public void setVisitStart(SchoolDate visitStart) {
        this.visitStart = visitStart;
    }

    public SchoolDate getVisitEnd() {
        return visitEnd;
    }

    public void setVisitEnd(SchoolDate visitEnd) {
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
