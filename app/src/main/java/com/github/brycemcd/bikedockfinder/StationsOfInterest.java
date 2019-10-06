package com.github.brycemcd.bikedockfinder;

import android.location.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This is available via the API but it's a lot of BS just to get these details for just
 * the stations I'm interested in
 */
public class StationsOfInterest {

    // NOTE: this is pretty dumb and Java < 8-y
    public static HashMap<String, String> stationMap;
    static {
        stationMap = new HashMap<>();
        stationMap.put("161", "LaGuardia Pl & W 3 St");
        stationMap.put("229", "Great Jones St");
        stationMap.put("303", "Mercer St & Bleecker St");
    }

    public static List<Location> allStations() {
        ArrayList<Location> stations = new ArrayList<>();

        // station_id = 161
        // name = LaGuardia & W. 3 ST
        Location secondChoice = new Location("161");
        secondChoice.setLongitude(-73.99810231);
        secondChoice.setLatitude(40.72917025);

        // station_id = 229
        // name = Great Jones St
        Location thirdChoice = new Location("229");
        thirdChoice.setLongitude(-73.99379025);
        thirdChoice.setLatitude(40.72743423);

        // These are the stations I care about for docking:
        // station_id = 303
        // name = Mercer & Bleeker
        Location firstChoice = new Location("303");
        firstChoice.setLongitude(-73.99662137031554);
        firstChoice.setLatitude(40.72706363348306);

        stations.add(firstChoice);
        stations.add(secondChoice);
        stations.add(thirdChoice);

        return stations;
    }
}
