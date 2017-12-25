package net.portrix.generic.rest.google;

public class LocationForm {

    private String id;

    private String name;

    private String formatted;

    private Double lat;

    private Double lng;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormatted() {
        return formatted;
    }

    public void setFormatted(String formatted) {
        this.formatted = formatted;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
