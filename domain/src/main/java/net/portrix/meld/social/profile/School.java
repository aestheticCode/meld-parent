package net.portrix.meld.social.profile;

import net.portrix.generic.ddd.AbstractEntity;

import javax.persistence.*;

/**
 * @author Patrick Bittner on 21/12/2016.
 */
@Entity
@Table(name = "so_school")
public class School extends AbstractEntity {

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
            @AttributeOverride(name="semester", column=@Column(name = "startSemester")),
            @AttributeOverride(name="year", column=@Column(name = "startYear"))
    })
    private SchoolDate start;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="semester", column=@Column(name = "endSemester")),
            @AttributeOverride(name="year", column=@Column(name = "endYear"))
    })
    private SchoolDate end;

    private boolean tillNow;

    private String description;

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

    public SchoolDate getStart() {
        return start;
    }

    public void setStart(SchoolDate start) {
        this.start = start;
    }

    public SchoolDate getEnd() {
        return end;
    }

    public void setEnd(SchoolDate end) {
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
