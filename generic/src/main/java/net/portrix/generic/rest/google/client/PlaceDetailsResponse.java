package net.portrix.generic.rest.google.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaceDetailsResponse {

    private PlaceDetails result;

    public PlaceDetails getResult() {
        return result;
    }

    public void setResult(PlaceDetails result) {
        this.result = result;
    }
}
