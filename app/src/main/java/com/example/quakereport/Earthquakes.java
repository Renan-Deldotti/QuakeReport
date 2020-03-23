package com.example.quakereport;

public class Earthquakes {
    private double magnitude;
    private String location;
    private long date;

    public Earthquakes(double magnitude, String location, long date) {
        this.magnitude = magnitude;
        this.location = location;
        this.date = date;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public String getLocation() {
        return location;
    }

    public long getDate() {
        return date;
    }
}