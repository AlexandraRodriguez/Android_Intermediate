package com.example.ale.nearbylocations;

public class Place {
    private String id;
    private String name;
    private double lat;
    private double lng;
    private int distance;

    public Place(String id, String name, double lat, double lng){
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        distance = 0;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

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
}
