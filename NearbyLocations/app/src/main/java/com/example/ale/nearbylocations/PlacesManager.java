package com.example.ale.nearbylocations;

import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.parse.ParseAnalytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class PlacesManager implements Constants {

    private ArrayList<Place> places;

    public PlacesManager() {
        places = new ArrayList<Place>();
    }

    public ArrayList<Place> findPlaces(double lat, double lng, String placeType) {
        Log.e("tag", "inside findPlaces, type: " + placeType);
        String url = makeURL(lat, lng, placeType);
        Log.e("tag", "url: "+ url);
        try {
            String json = getJson(url);
            JSONArray venues = new JSONObject(json).getJSONObject("response").getJSONArray("venues");
            Log.e("tag", "venues length: "+venues.length());
            for (int i = 0; i < venues.length(); i++) {
                Log.e("tag", "inside for, time: "+i);
                JSONObject newPlace = venues.getJSONObject(i);
                double latitude = newPlace.getJSONObject("location").getDouble("lat");
                double longitude = newPlace.getJSONObject("location").getDouble("lng");
                int distance = newPlace.getJSONObject("location").getInt("distance");
                Place place = new Place(newPlace.getString("id"), newPlace.getString("name"), latitude, longitude);
                place.setDistance(distance);
                addOrderedByDistance(place);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return places;
    }

    private String makeURL(double lat, double lng, String type) {
        StringBuilder url = new StringBuilder(URL_FIRST_PART);
        url.append("ll=" + lat + "," + lng);
        url.append("&client_id=" + CLIENT_ID);
        url.append("&client_secret=" + CLIENT_SECRET);
        url.append("&v=20130815%20");
        url.append("&query=" + type);

        return url.toString();
    }

    private String getJson(String mUrl) {
        StringBuilder content = new StringBuilder();
        try {
            URL url = new URL(mUrl);
            URLConnection urlConnection = url.openConnection();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = buffer.readLine()) != null) {
                content.append(line + "\n");
            }
            buffer.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    private void addOrderedByDistance(Place place) {
        int dist = place.getDistance();
        int i = 0;
        boolean added = false;
        while (i < places.size() && added == false) {
            int currDist = places.get(i).getDistance();
            if (dist <= currDist) {
                places.add(i, place);
                added = true;
            }
            i++;
        }
        if (added == false) {
            places.add(place);
        }
    }
}
