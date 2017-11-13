package net.portrix.meld.social.contact;

import net.portrix.meld.social.ChatCategory;

import java.util.UUID;

/**
 * @author Patrick Bittner on 21/12/2016.
 */
public class ChatForm {

    private UUID id;

    private String name;

    private ChatCategory type;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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
