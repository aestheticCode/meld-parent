package net.portrix.generic.rest.api.meta;

import net.portrix.generic.rest.api.Links;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrick Bittner on 17/04/2017.
 */
public class MetaResponseType {

    private List<Property> properties = new ArrayList<>();

    private Links links = new Links();

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public Links getLinks() {
        return links;
    }
}
