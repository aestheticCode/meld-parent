package net.portrix.meld.social.profile.contact.form;

import net.portrix.meld.social.profile.PhoneCategory;

import java.util.UUID;

/**
 * @author Patrick Bittner on 21/12/2016.
 */
public class PhoneForm {

    private UUID id;

    private String number;

    private PhoneCategory type;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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
