package com.github.brycemcd.bikedockfinder;

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

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

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

    public CitiBikeStation(String stationName, int stationId, int bikesAvailable, int docksAvailable, int lastReported) {
        this.stationName = stationName;
        this.stationId = stationId;
        this.bikesAvailable = bikesAvailable;
        this.docksAvailable = docksAvailable;
        this.lastReported = lastReported;
    }

    @Override
    public String toString() {
        return stationName + " Docks: " + Integer.toString(docksAvailable);
    }

    // HOLY CRAP IS THIS INEFFICIENT
    public static ArrayList<CitiBikeStation> parseStationInfo(JSONObject apiresults) {
        ArrayList<CitiBikeStation> stationResults = new ArrayList<>();

        HashSet<String> stationIdsOfInterest = new HashSet<>();
        stationIdsOfInterest.add("303");
        stationIdsOfInterest.add("161");
        stationIdsOfInterest.add("229");

        HashMap<String, String> stationMap = new HashMap<>();
        stationMap.put("161", "LaGuardia Pl & W 3 St");
        stationMap.put("229", "Great Jones St");
        stationMap.put("303", "Mercer St & Bleecker St");

        try {
            JSONObject data = apiresults.getJSONObject("data");
            JSONArray stations = data.getJSONArray("stations");

            for (int i = 0; i < stations.length(); i++) {
                JSONObject station = stations.getJSONObject(i);
                String stationId = station.getString("station_id");
                if ( stationIdsOfInterest.contains(stationId) ) {
                    Log.d("STATION", stationId);

                    CitiBikeStation cbs = new CitiBikeStation(
                            stationMap.get(stationId),
                            Integer.valueOf(stationId),
                            station.getInt("num_bikes_available"),
                            station.getInt("num_docks_available"),
                            station.getInt("last_reported")
                    );
                    stationResults.add(cbs);
                }
            }
        } catch(JSONException e) {
            Log.e("STATION", "EXCePTION", e);
        }

        return stationResults;
    }
}
