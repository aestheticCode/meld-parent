package net.portrix.meld.social.profile;

import net.portrix.generic.ddd.AbstractEntity;

import javax.ejb.Local;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * @author Patrick Bittner on 21/12/2016.
 */
@Entity
@Table(name = "so_address")
public class Address extends AbstractEntity {

    private String street;

    private String zipCode;

    private String state;

    private String city;

    private String country;

    @Column(name = "startDate")
    private LocalDate start;

    @Column(name = "endDate")
    private LocalDate end;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
