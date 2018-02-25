package net.portrix.generic.rest.api.meta;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrick Bittner on 17/04/2017.
 */
public class MetaResponseType {

    private List<Property> properties = new ArrayList<>();

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

}
