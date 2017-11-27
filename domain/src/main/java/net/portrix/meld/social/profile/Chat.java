package net.portrix.meld.social.profile;

import net.portrix.generic.ddd.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Patrick Bittner on 21/12/2016.
 */
@Entity
@Table(name = "so_chat")
public class Chat extends AbstractEntity {

    private String name;

    private ChatCategory type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ChatCategory getType() {
        return type;
    }

    public void setType(ChatCategory type) {
        this.type = type;
    }
}
