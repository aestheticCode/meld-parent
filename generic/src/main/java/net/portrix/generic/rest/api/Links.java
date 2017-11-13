package net.portrix.generic.rest.api;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Patrick Bittner on 13.06.2015.
 */
public class Links {

    private final Map<String, Link> data = new HashMap<>();

    @JsonAnyGetter
    public Map<String, Link> getData() {
        return data;
    }

    @JsonAnySetter
    public void setData(String rel, Link link) {
        data.put(rel, link);
    }
}
