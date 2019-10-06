package com.github.brycemcd.bikedockfinder;

public class MainActivityBkp {
//    private LocationManager locationManager;
//    private LocationListener locationListener;
//    public static LocationTracker lt = new LocationTracker();
//
//    private GeofencingClient geofencingClient;
//
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        setGeofences();
//
//        Log.i("locationListener", "locationListener Permissions Result");
//
//        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                Log.d("locationListener", "locationListener Permissions Result locman");
//                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
//                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
//            }
//        } else {
//            Log.d("LOCATION", "YOU DO NOT HAVE PERMISSIONS");
//        }
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//
//        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//        locationListener = lt.locationListener;
//
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//        } else {
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
//            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
//        }
//
//        geofencingClient = LocationServices.getGeofencingClient(this);
//
//        // this is SO
//        setGeofences();
//        startFencing();
//    }
//
//    private List<Geofence> geofenceList = new ArrayList<>();
//    private static final int GEOFENCE_RADIUS_IN_METERS = 200;
////    private static final int GEOFENCE_EXPIRATION_IN_MILLISECONDS = 2 * 60 * 1000;
//
//    public void addGeoFence() {
//        geofenceList.add(new Geofence.Builder()
//                // Set the request ID of the geofence. This is a string to identify this
//                // geofence.
//                .setRequestId("foo")
//
//                .setCircularRegion(
//                        // This is Central Park
//                        73.9683,
//                        40.7851,
//                        GEOFENCE_RADIUS_IN_METERS
//                )
////                .setExpirationDuration(GEOFENCE_EXPIRATION_IN_MILLISECONDS)
//                .setExpirationDuration(-1L)
//                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
//                        Geofence.GEOFENCE_TRANSITION_EXIT)
//                .build());
//
//    }
//
//    private GeofencingRequest getGeofencingRequest() {
//        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
//        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
//        addGeoFence();
//        builder.addGeofences(geofenceList);
//        return builder.build();
//    }
//
//    private PendingIntent geofencePendingIntent;
//    private PendingIntent getGeofencePendingIntent() {
//        // Reuse the PendingIntent if we already have it.
//        if (geofencePendingIntent != null) {
//            return geofencePendingIntent;
//        }
//        Intent intent = new Intent(this, GeofenceBroadcastReceiver.class);
//        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when
//        // calling addGeofences() and removeGeofences().
//        geofencePendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.
//                FLAG_UPDATE_CURRENT);
//        return geofencePendingIntent;
//    }
//
//    public void startFencingBtn(View v) {
//        startFencing();
//    }
//
//    public void startFencing() {
//        geofencingClient.addGeofences(getGeofencingRequest(), getGeofencePendingIntent())
//                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        // Geofences added
//                        // ...
//                        Log.d("GEOFENCE", "GEO ADDED!");
//                    }
//                })
//                .addOnFailureListener(this, new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        // Failed to add geofences
//                        // ...
//                        Log.d("GEOFENCE", "GEO FAILED!");
//                        Log.d("GEOFENCE", "error: " + e.toString(), e);
//                    }
//                });
//    }
//
//    public void stopFencing(View v) {
//        geofencingClient.removeGeofences(getGeofencePendingIntent())
//                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        // Geofences removed
//                        // ...
//                        Log.d("GEOFENCE", "STOP SUCCESS");
//                    }
//                })
//                .addOnFailureListener(this, new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        // Failed to remove geofences
//                        // ...
//                        Log.d("GEOFENCE", "STOP FAIL");
//                    }
//                });
//
//    }
//
//    // This is all SO
//    private List<Geofence> mGeofenceList = new ArrayList<>();
//
//    private GeofencingClient gfc;
//
//    private void setGeofences(){
//        GeofencingRequest geofencingRequest = getGeofencingRequestToo();
//        PendingIntent pi = getGeofencePendingIntentToo();
//        gfc.addGeofences(geofencingRequest, pi)
//                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Log.d("Geofences", "geofencing set up succesfully");
//                        Toast.makeText(MainActivity.this, "Geofences set up", Toast.LENGTH_SHORT).show();
//
//                    }
//                })
//                .addOnFailureListener(MainActivity.this, new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.e("Geofence", e.toString());
//                        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//                        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
//                            Log.e("Provider", "Provider is not avaible");
//                        }
//                        if (!manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
//                            Log.e("Network Provider", "Provider is not avaible");
//                        }
//
//                    }
//                });
//    }
//
//    private GeofencingRequest getGeofencingRequestToo(){
//        if (mGeofenceList.isEmpty()){
//            return null;}
//        Log.v("mGeofenceList", mGeofenceList.toString());
//        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
//        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER |
//                GeofencingRequest.INITIAL_TRIGGER_EXIT);
//        builder.addGeofences(mGeofenceList);
//        return builder.build();
//    }
//
//    private PendingIntent mGeofencePendingIntent;
//
//    private PendingIntent getGeofencePendingIntentToo(){
//        if (mGeofencePendingIntent != null){
//            return mGeofencePendingIntent;
//        }
//        Intent intent = new Intent(getApplicationContext(), Object.class);
//        mGeofencePendingIntent =  PendingIntent.getService(getApplication(),
//                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        return mGeofencePendingIntent;
//    }
}
