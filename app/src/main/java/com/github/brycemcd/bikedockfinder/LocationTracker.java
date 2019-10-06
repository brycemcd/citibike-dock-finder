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
        public void onLocationChanged(Location myLocation) {
//            Log.d(LOG_TAG, "onLocationChanged " + myLocation.toString());
//            Log.d(LOG_TAG, location.toString());
            lastLocation = myLocation;

//            MainActivity.mapLocationUpdateToBikeStations(myLocation);
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
