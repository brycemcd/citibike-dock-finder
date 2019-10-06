package com.github.brycemcd.bikedockfinder;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

/**
 * NOTES
 * add implementation 'com.google.android.gms:play-services-location:16.0.0' too _app_ gradle file and rebuild
 * Found this useful: https://developer.android.com/training/location/geofencing
 * MUST have high GPS accuracy turned on: https://stackoverflow.com/questions/53996168/geofence-not-avaible-code-1000-while-trying-to-set-up-geofence
 */
public class MainActivity extends AppCompatActivity {

    private LocationManager locationManager;
    private LocationListener locationListener;
    public ProgressDialog pd;
    public TextView jsonResults;

    public static LocationTracker lt = new LocationTracker();
    public static Context activityContext;

    ArrayList<String> stations = new ArrayList<>();
    ListView stationList;
    private static final int LIST_VIEW_TEXT_SIZE_DP = 25;
    ArrayAdapter arrayAdapter;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Log.i("locationListener", "locationListener Permissions Result");

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Log.d("locationListener", "locationListener Permissions Result locman");
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            }
        } else {
            Log.d("LOCATION", "YOU DO NOT HAVE PERMISSIONS");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CitiBikeStation.populateStationsOfInterest();

        jsonResults = findViewById(R.id.jsonResults);

        activityContext = this;

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = lt.locationListener;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }

        stationList = findViewById(R.id.stationList);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, stations) {
            // NOTE: all this BS is needed to change the text size :(
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                /// Get the Item from ListView
                View view = super.getView(position, convertView, parent);

                TextView tv = (TextView) view.findViewById(android.R.id.text1);

                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, LIST_VIEW_TEXT_SIZE_DP);

                // Return the view
                return view;
            }
        };

        stationList.setAdapter(arrayAdapter);
    }

    public void updateWithoutFetch(View v) {
        updateUI();
    }

    public void updateUI() {
        stations.clear();
        arrayAdapter.notifyDataSetChanged();

        for(CitiBikeStation cbs : CitiBikeStation.interestingStations.values()) {
            stations.add(cbs.toDisplay());
        }
        arrayAdapter.notifyDataSetChanged();
    }

    public void startFencingBtn(View v) {

        String url = "https://gbfs.citibikenyc.com/gbfs/en/station_status.json";
        new JsonTask().execute(url);
    }

    public void updateStationDataTime() {
        Date timeNow = Calendar.getInstance().getTime();
        jsonResults.setText("Station data last updated: " + timeNow.toString());
    }
    public static void updateUIWithLocationDiff(ArrayList<ProximityInterest> proximityInterests) {
        TextView distanceText = ((Activity) activityContext).findViewById(R.id.distanceText);
        String txt = "";
        for(ProximityInterest pi : proximityInterests) {
            txt += "\n";
            txt += "Location: " + pi.getLocationOfInterest().getProvider();
            txt += "\n";
            txt += "Distance: " + pi.getDistance() + "\n";
            txt += "---";
            txt += "\n";
        }
        distanceText.setText(txt);
    }

    public static void mapLocationUpdateToBikeStations(Location myLocation) {
        for(CitiBikeStation cbs : CitiBikeStation.interestingStations.values()) {
            cbs.updateDistanceAway(myLocation);
        }
    }

    // https://stackoverflow.com/questions/33229869/get-json-data-from-url-using-android
    // NOTE: in clojure, this is just `open`
    private class JsonTask extends AsyncTask<String, String, JSONObject> {

        protected void onPreExecute() {
            super.onPreExecute();

            stations.clear();
            arrayAdapter.notifyDataSetChanged();

            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        protected JSONObject doInBackground(String... params) {
            return StationApiParser.getStationData();
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            // NOTE: this updates the UI with a butt load of JSON
//            jsonResults.setText(result.toString());
            updateStationDataTime();
            pd.hide();
            HashMap<Integer, CitiBikeStation> stationInfos = CitiBikeStation.parseStationInfo(result);
            ArrayList<CitiBikeStation> justStations = new ArrayList<>( stationInfos.values() );
            justStations.sort(new Comparator<CitiBikeStation>() {
                @Override
                public int compare(CitiBikeStation o1, CitiBikeStation o2) {
                    return o1.getStationName().compareTo(o2.getStationName());
                }
            });

            for(CitiBikeStation cbs : justStations) {
                stations.add(cbs.toDisplay());
                arrayAdapter.notifyDataSetChanged();
            }
        }
    }

}
