package net.portrix.meld.social.profile;

import net.portrix.generic.ddd.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Patrick Bittner on 21/12/2016.
 */
@Entity
@Table(name = "so_phone")
public class Phone extends AbstractEntity {

    private String number;

    private PhoneCategory type;

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
}
