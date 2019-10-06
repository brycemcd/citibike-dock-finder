package com.github.brycemcd.bikedockfinder;

import android.location.Location;

import java.util.ArrayList;

public class ProximityInterest {

    public Location locationOfInterest;
    public float distance;

    public Location getLocationOfInterest() {
        return locationOfInterest;
    }

    public void setLocationOfInterest(Location locationOfInterest) {
        this.locationOfInterest = locationOfInterest;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public ProximityInterest(Location location, float distance) {
        setLocationOfInterest(location);
        setDistance(distance);
    }



    /**
     * Calculates distance between current location and the station of interest
     * @param location
     * @return (int) meters to the location of interest
     */
    public static ArrayList<ProximityInterest> calculate(Location location) {

        ArrayList<ProximityInterest> distances = new ArrayList<>();

        for (Location stationLocation : StationsOfInterest.allStations()) {
            float distance = location.distanceTo(stationLocation);
            ProximityInterest proximityInterest = new ProximityInterest(stationLocation, distance);
            distances.add(proximityInterest);
        }
        return distances;
    }
}
