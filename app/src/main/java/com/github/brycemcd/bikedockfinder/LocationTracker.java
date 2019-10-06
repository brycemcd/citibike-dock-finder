package com.github.brycemcd.bikedockfinder;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class LocationTracker {
    private String LOG_TAG = LocationListener.class.toString();
    public Location lastLocation;

    public LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
//            Log.d(LOG_TAG, "onLocationChanged");
//            Log.d(LOG_TAG, location.toString());
            lastLocation = location;
            ArrayList<ProximityInterest> dists = ProximityInterest.calculate(location);
            for (ProximityInterest pi : dists ) {
                Log.d("LOCATION", "distance: " + pi.getLocationOfInterest().getProvider() + " " + pi.getDistance());
            }
            MainActivity.updateUIWithLocationDiff(ProximityInterest.calculate(location));
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d(LOG_TAG, "onStatusChanged");

        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.d(LOG_TAG, "onProviderEnabled");

        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.d(LOG_TAG, "PROVIDER DISABLED");
        }
    };
}
