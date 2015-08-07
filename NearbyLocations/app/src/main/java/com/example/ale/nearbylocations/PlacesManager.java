package com.example.ale.nearbylocations;

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

/**
 * Created by Ale on 31/07/2015.
 */
public class PlacesManager implements Constants {


    public PlacesManager(){    }

    public ArrayList<Place> findPlaces(double lat, double lng, String placeType){
        ArrayList<Place> places = new ArrayList<Place>();
        String url = makeURL(lat, lng, placeType);
        try{
            String json = getJson(url);
            //JSONObject object = new JSONObject(json);
            JSONArray venues = new JSONObject(json).getJSONObject("response").getJSONArray("venues");
            for(int i = 0; i < venues.length(); i++){
                JSONObject newPlace = venues.getJSONObject(i);
                double latitude = newPlace.getJSONObject("location").getDouble("lat");
                double longitude = newPlace.getJSONObject("location").getDouble("lng");
                Place place = new Place(newPlace.getString("id"), newPlace.getString("name"), latitude, longitude);
                places.add(place);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return places;
    }

    private String makeURL(double lat, double lng, String type){
        StringBuilder url = new StringBuilder(URL_FIRST_PART);

        url.append("ll="+ lat + "," + lng);
        url.append("&client_id=" +  CLIENT_ID);
        url.append("&client_secret=" + CLIENT_SECRET);
        url.append("&v=20130815%20");
        url.append("&query=" + type);

        return url.toString();
    }

    private String getJson(String mUrl){
        StringBuilder content = new StringBuilder();
        try{
            URL url = new URL(mUrl);
            URLConnection urlConnection = url.openConnection();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while((line=buffer.readLine()) != null){
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
}
