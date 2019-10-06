package com.github.brycemcd.bikedockfinder;

import android.location.Location;

import java.util.ArrayList;
import java.util.List;

public class StationsOfInterest {
    public static List<Location> allStations() {
        ArrayList<Location> stations = new ArrayList<>();

        // These are the stations I care about for docking:
        // station_id = 303
        Location firstChoice = new Location("Mercer St & Bleecker St");
        firstChoice.setLongitude(-73.99662137031554);
        firstChoice.setLatitude(40.72706363348306);

        // station_id = 161
        Location secondChoice = new Location("LaGuardia Pl & W 3 St");
        secondChoice.setLongitude(-73.99810231);
        secondChoice.setLatitude(40.72917025);

        // station_id = 229
        Location thirdChoice = new Location("Great Jones St");
        thirdChoice.setLongitude(-73.99379025);
        thirdChoice.setLatitude(40.72743423);

        stations.add(firstChoice);
        stations.add(secondChoice);
        stations.add(thirdChoice);
        return stations;
    }
}
