package com.example.ale.nearbylocations;

import android.location.Location;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

public class MapsActivity extends FragmentActivity implements LocationProvider.LocationCallback {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private LocationProvider mLocationProvider;
    private TextView txtLoc;
    private double currentLatitude;
    private double currentLongitude;
    private Spinner spinner;
    private HashMap<String, MarkerManager> hashMap;
    private String currentType;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_maps);
        txtLoc = (TextView) findViewById(R.id.latlng);
        currentType = "none";
        hashMap = new HashMap<String, MarkerManager>(10);
        setUpMapIfNeeded();
        mLocationProvider = new LocationProvider(this, this);
        setSpinner();

    }

    public void onPreviousPressed(View view){
        MarkerManager manager = hashMap.get(currentType);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(manager.previous(), 16f));
    }

    public void onNextPressed(View view){
        MarkerManager manager = hashMap.get(currentType);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(manager.next(), 16f));
    }

    private void setSpinner() {
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(this,
                        R.array.array_values,
                        android.R.layout.simple_spinner_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String category = parent.getItemAtPosition(position).toString();
                Log.e("tag", "item selected");
                showMarkers(category);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void showMarkers(String type) {
        Log.e("tag", "inside showMarkers");
        Log.e("tag", "type: " + type);
        Log.e("tag", "currentType: " + currentType);
        if (!currentType.equals(type)) {
            Log.e("tag", "inside first if");
            if (hashMap.containsKey(type)) {
                Log.e("tag", "type alredy was in hashmap");
                hashMap.get(currentType).hideMarkers();
                hashMap.get(type).showMarkers();
                currentType = type;
            } else {
                Log.e("tag", "new type");
                if(hashMap.containsKey(currentType)){
                    hashMap.get(currentType).hideMarkers();
                }
                currentType = type;
                Log.e("tag", "currentType changed to: "+currentType);
                new PlaceFinder().execute(currentType);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        mLocationProvider.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationProvider.disconnect();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        LatLng current = new LatLng(currentLatitude, currentLongitude);
        mMap.addMarker(new MarkerOptions().position(current).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).title("Estoy aquí!"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current, 14f));
    }

    public void handleNewLocation(Location location) {
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();
        txtLoc.setText("Ubicación actual: "+ String.valueOf(location.getLatitude()) + ", " + String.valueOf(location.getLongitude()));
        setUpMap();

    }

    public double getCurrentLatitude() {
        return currentLatitude;
    }

    public double getCurrentLongitude() {
        return currentLongitude;
    }

    private class PlaceFinder extends AsyncTask<String, Void, ArrayList<Place>> {

        @Override
        protected ArrayList<Place> doInBackground(String... params) {
            PlacesManager placesManager = new PlacesManager();
            Log.e("tag", "currLat:"+currentLatitude + ", currLng:"+currentLongitude);
            ArrayList<Place> places = placesManager.findPlaces(currentLatitude, currentLongitude, params[0]);
            Log.e("tag", "inside doInBackground, places size: "+places.size());
            return places;
        }

        @Override
        protected void onPostExecute(ArrayList<Place> places) {
            MarkerManager markerManager = new MarkerManager();
            Log.e("tag", "inside onPostExecute, places size: "+ places.size());
            for (int i = 0; i < places.size(); i++) {
                Log.e("tag", "inside the for: " + i);
                Place place = places.get(i);
                Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(place.getLat(), place.getLng()))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                        .title(place.getName()));
                markerManager.add(marker);
            }

            hashMap.put(currentType, markerManager);
            Log.e("tag", "new type added to hashmap");
        }
    }
}
