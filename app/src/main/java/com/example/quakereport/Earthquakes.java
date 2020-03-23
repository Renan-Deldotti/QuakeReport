package com.example.quakereport;

public class Earthquakes {
    private String magnitude;
    private String location;
    private String date;

    public Earthquakes(String magnitude, String location, String date){
        this.magnitude = magnitude;
        this.location = location;
        this.date = date;
    }

    public String getMagnitude() {
        return magnitude;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }
}
