package net.portrix.generic.rest.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Patrick Bittner on 30.05.2015.
 */
public class Column {

    private final String id;

    private final String name;

    private final boolean visible;

    private final int width;

    private final Boolean ascending;

    @JsonCreator
    public Column(@JsonProperty("id") String id,
                  @JsonProperty("name") String name,
                  @JsonProperty("visible") boolean visible,
                  @JsonProperty("width") int width,
                  @JsonProperty("ascending") Boolean ascending) {
        this.id = id;
        this.name = name;
        this.visible = visible;
        this.width = width;
        this.ascending = ascending;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isVisible() {
        return visible;
    }

    public int getWidth() {
        return width;
    }

    public Boolean isAscending() {
        return ascending;
    }
}
