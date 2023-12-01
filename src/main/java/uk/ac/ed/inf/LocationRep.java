package uk.ac.ed.inf;

public class LocationRep {
    private double lng;
    private double lat;

    // Constructor
    public LocationRep(double lng, double lat) {
        this.lng = lng;
        this.lat = lat;
    }

    // Getters and Setters
    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "LocationRep{" +
                "lng=" + lng +
                ", lat=" + lat +
                '}';
    }
}
