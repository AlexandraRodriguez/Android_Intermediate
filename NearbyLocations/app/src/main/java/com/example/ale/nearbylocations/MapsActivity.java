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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends FragmentActivity implements LocationProvider.LocationCallback {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private LocationProvider mLocationProvider;

    private TextView txtLatitude;
    private TextView txtLongitude;
    private double currentLatitude;
    private double currentLongitude;
    private Spinner spinner;
    private HashMap<String, MarkerManager> hashMap;
    private String currentType;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_maps);
        txtLatitude = (TextView) findViewById(R.id.latitude);
        txtLongitude = (TextView) findViewById(R.id.longitude);
        hashMap = new HashMap<String, MarkerManager>(10);
        setUpMapIfNeeded();
        setSpinner();
        mLocationProvider = new LocationProvider(this, this);
        //nameTest = (TextView)findViewById(R.id.name);

        //ParseAnalytics.trackAppOpenedInBackground(getIntent());
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
                show(category);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void show(String type) {
        if (currentType != type) {

            if (hashMap.containsKey(type)) {
                hashMap.get(currentType).hideMarkers();
                hashMap.get(type).showMarkers();
                currentType = type;
            } else {
                currentType = type;
                new PlaceFinder().execute(currentType);
            }
        }


        /*ParseQuery<ParseObject> query = ParseQuery.getQuery("Place");
        if (type != "Todos") {
            query.whereEqualTo("type", type);
        }
        Log.e("tag", "llamada");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    Log.e("tag", "lista");
                    for (int i = 0; i < list.size(); i++) {
                        Log.e("tag", "inside the for");
                        ParseObject place = list.get(i);
                        double lat = place.getDouble("latitude");
                        double lng = place.getDouble("longitude");
                        mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title(place.getString("name")));
                    }

                } else {
                    Log.e("tag", "exception");
                }
            }
        });*/
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
        Log.e("tag", "inside setupMapIfNeeded");
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            Log.e("tag", "map = null");
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            Log.e("tag", "map setted");
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                Log.e("tag", "map wasn't null");
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
        Log.e("tag", "inside setupMap");
        LatLng current = new LatLng(currentLatitude, currentLongitude);
        mMap.addMarker(new MarkerOptions().position(current).title("I'm here!"));
        //marker.setVisible(true);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current, 16f));
    }

    public void handleNewLocation(Location location) {
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();
        txtLatitude.setText("Latitude: " + String.valueOf(location.getLatitude()));
        txtLongitude.setText("Longitude: " + String.valueOf(location.getLongitude()));
        setUpMap();

    }


    public double getCurrentLatitude() {
        return currentLatitude;
    }

    public double getCurrentLongitude() {
        return currentLongitude;
    }

    private class PlaceFinder extends AsyncTask<String, Void, ArrayList<Place>>{

        @Override
        protected ArrayList<Place> doInBackground(String... params) {
            PlacesManager placesManager = new PlacesManager();
            ArrayList<Place> places = placesManager.findPlaces(currentLatitude, currentLongitude, params[0]);

            return places;
        }

        @Override
        protected void onPostExecute(ArrayList<Place> places){
            MarkerManager markerManager = new MarkerManager();

            for(int i = 0; i<places.size(); i++) {
                Place place = places.get(i);
                Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(place.getLat(), place.getLng()))
                        .title(place.getName()));
                markerManager.add(marker);
            }

            hashMap.put(currentType, markerManager);
        }
    }

}
