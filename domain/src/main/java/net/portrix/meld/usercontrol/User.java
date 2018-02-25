package net.portrix.meld.usercontrol;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * @author Patrick Bittner on 31/01/15.
 */
@Entity
@Table(name = "uc_user")
@NamedQueries({
        @NamedQuery(name = "findUser", query = "select u from User u where u.name = :name"),
        @NamedQuery(name = "findUserByExternal", query = "select u from User u where u.externalId = :id")
})
@Indexed
public class User extends Identity {

    private String externalId;

    @Field
    private String firstName;

    @Field
    private String lastName;

    private Gender gender;

    private LocalDate birthdate;

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

