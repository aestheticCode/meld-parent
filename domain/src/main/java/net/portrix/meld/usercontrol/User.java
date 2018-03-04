package net.portrix.meld.usercontrol;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

/**
 * @author Patrick Bittner on 31/01/15.
 */
@Entity
@Table(name = "uc_user")
@NamedQueries({
        @NamedQuery(name = "findUserByName", query = "select u from User u where u.name = :name"),
        @NamedQuery(name = "findUserByExternal", query = "select u from User u where u.externalId = :id")
})
public class User extends Identity {

    private String externalId;

    private String firstName;

    private String lastName;

    private Gender gender;

    private LocalDate birthdate;

    @PrePersist
    @PreUpdate
    public void updateName() {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("ddMMMuuuu")
                .toFormatter();
        String birthday = getBirthdate().format(formatter);
        String userId = getFirstName() + getLastName() + birthday;

        setName(userId);
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstname) {
        this.firstName = firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastname) {
        this.lastName = lastname;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visit(this);
    }
}

