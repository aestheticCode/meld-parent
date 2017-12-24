package net.portrix.generic.rest.google.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlacePredictions {

    private List<PlacePrediction> predictions;

    public List<PlacePrediction> getPredictions() {
        return predictions;
    }

    public void setPredictions(List<PlacePrediction> predictions) {
        this.predictions = predictions;
    }
}
