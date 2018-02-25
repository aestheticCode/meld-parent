package net.portrix.meld.social.profile;

import net.portrix.generic.ddd.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Patrick Bittner on 21/12/2016.
 */
@Entity
@Table(name = "so_phone")
public class Phone extends AbstractEntity {

    private String number;

    private PhoneCategory type;

    @ManyToOne
    private PersonalContact contact;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public PhoneCategory getType() {
        return type;
    }

    public void setType(PhoneCategory type) {
        this.type = type;
    }

    public PersonalContact getContact() {
        return contact;
    }

    public void setContact(PersonalContact contact) {
        this.contact = contact;
    }
}
