package net.portrix.meld.usercontrol.group.select;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.portrix.generic.rest.api.AbstractRestEntity;
import net.portrix.generic.rest.api.Link;
import net.portrix.generic.rest.api.LinksContainer;

import java.util.*;

public class GroupSelect extends AbstractRestEntity {

    private final String name;

    @JsonCreator
    public GroupSelect(@JsonProperty("id") UUID id,
                       @JsonProperty("name") String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
