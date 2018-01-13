package net.portrix.meld.social.profile;

import net.portrix.generic.ddd.AbstractEntity;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * @author Patrick Bittner on 21/12/2016.
 */
@Entity
@Table(name = "so_company")
@NamedQuery(name = "findCompanyNameByName", query = "select c.name from Company c where lower(c.name) like :name order by c.name asc")
public class Company extends AbstractEntity {

    private String name;

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

    private String title;

    @Column(name = "startDate")
    private LocalDate start;

    @Column(name = "endDate")
    private LocalDate end;

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
