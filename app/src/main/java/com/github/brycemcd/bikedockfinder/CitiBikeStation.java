package com.github.brycemcd.bikedockfinder;

import android.location.Location;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class CitiBikeStation {
    public int stationId;
    public String stationName;
    public int bikesAvailable;
    public int docksAvailable;
    public int lastReported;
    public double latitude;
    public double longitude;
    public Location stationLocation;
    public double distanceAway;
    public double distanceToDestination;

    public void setDistanceToDestination(double distanceToDestination) {
        this.distanceToDestination = distanceToDestination;
    }

    public double getDistanceToDestination() {
        return this.distanceToDestination;
    }

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }
    public Double getDistanceAway() { return distanceAway; }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public int getBikesAvailable() {
        return bikesAvailable;
    }

    public void setBikesAvailable(int bikesAvailable) {
        this.bikesAvailable = bikesAvailable;
    }

    public int getDocksAvailable() {
        return docksAvailable;
    }

    public void setDocksAvailable(int docksAvailable) {
        this.docksAvailable = docksAvailable;
    }

    public int getLastReported() {
        return lastReported;
    }

    public void setLastReported(int lastReported) {
        this.lastReported = lastReported;
    }

    public void setDistanceAway(float distanceAway) { this.distanceAway = distanceAway; }

    public CitiBikeStation(String stationName, int stationId, int bikesAvailable, int docksAvailable, int lastReported) {
        this.stationName = stationName;
        this.stationId = stationId;
        this.bikesAvailable = bikesAvailable;
        this.docksAvailable = docksAvailable;
        this.lastReported = lastReported;
    }

    public void addBikeDockInfo(JSONObject station) {
        try {
            setBikesAvailable(station.getInt("num_bikes_available"));
            setDocksAvailable(station.getInt("num_docks_available"));
            setLastReported(station.getInt("last_reported"));
        } catch (JSONException e) {
            Log.e("Add Bike Dock Info", e.getMessage(), e);
        }
    }

    public void updateDistanceAway(Location myLocation) {
        float distance = myLocation.distanceTo(stationLocation);
        Log.d("DISTANCE UPDATE", stationName + " " + Float.toString(distance) + this.toString());
        setDistanceAway(distance);
    }

    public static void updateRelativeLocationToStations(Location userLocation) {
        for(CitiBikeStation cbs : interestingStations.values()) {
            cbs.updateDistanceAway(userLocation);
        }
    }


    /**
     * Useful for pre-populating a list of stations with data that doesn't change for the session
     * @param location
     */
    public CitiBikeStation(Location location) {
        this.stationId = Integer.valueOf(location.getProvider());
        this.stationLocation = location;

        // NOTE: this is indexed by a String
        this.stationName = StationsOfInterest.stationMap.get(location.getProvider());


        this.latitude = location.getLongitude();
        this.longitude = location.getLatitude();

        double distanceFromDestination = location.distanceTo(StationsOfInterest.workLocation);
        setDistanceToDestination(distanceFromDestination);
    }

    @Override
    public String toString() {
        return stationId + " Docks: " + Integer.toString(docksAvailable) + " location: " + stationLocation.toString();
    }

    public String toDisplay() {
        return stationName + " \nDocks: " + Integer.toString(docksAvailable) + " Distance: " + Double.toString(Math.round(distanceAway));
    }

    public static String shortDockStatus() {
        int bleekerStationId = 437;
        return shortDockStatus(bleekerStationId);
    }

    public static String shortDockStatus(int stationIdOfInterest) {
        CitiBikeStation cbs = CitiBikeStation.interestingStations.get(stationIdOfInterest);
        if (cbs != null) {
            return cbs.stationName + ": Docks: " + cbs.getDocksAvailable();
        } else {
            return Integer.valueOf(stationIdOfInterest) + " station not available";
        }
    }

    public static HashMap<Integer, CitiBikeStation> interestingStations = new HashMap<>();

    /**
     * Designed to be called before populating stations with data
     */
    public static void populateStationsOfInterest() {
        for (Location station : StationsOfInterest.allStations()) {
            Log.d("populateStations", station.toString());

            CitiBikeStation citiBikeStation = new CitiBikeStation(station);

            interestingStations.put(citiBikeStation.stationId, citiBikeStation);
        }
    }

    // HOLY CRAP IS THIS INEFFICIENT
    public static HashMap<Integer, CitiBikeStation> parseStationInfo(JSONObject apiresults) {
        // We're enriching data in this operation. If the data is not there to enrich, it'll fail
        if (interestingStations.isEmpty() ) {
            populateStationsOfInterest();
        }

        try {
            JSONObject data = apiresults.getJSONObject("data");
            JSONArray stations = data.getJSONArray("stations");

            for (int i = 0; i < stations.length(); i++) {
                JSONObject station = stations.getJSONObject(i);
                int stationId = station.getInt("station_id");

                if ( interestingStations.keySet().contains(stationId) ) {
                    Log.d("STATION", Integer.toString(stationId));

                    CitiBikeStation cbs = interestingStations.get(stationId);
                    cbs.addBikeDockInfo(station);

                }
            }
        } catch(JSONException e) {
            Log.e("STATION", "EXCePTION", e);
        }

        return interestingStations;
    }
}
