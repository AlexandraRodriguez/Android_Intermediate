package com.example.ale.nearbylocations;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

public class MarkerManager {
    private ArrayList<Marker> markers;
    private int index;
    private int maxIndex;

    public MarkerManager() {
        markers = new ArrayList<Marker>();
        index = 0;
        maxIndex = 0;
    }

    public void add(Marker marker) {
        markers.add(marker);
        maxIndex++;
    }

    public void hideMarkers() {
        for (int i = 0; i < markers.size(); i++) {
            markers.get(i).setVisible(false);
        }
    }

    public void showMarkers() {
        for (int i = 0; i < markers.size(); i++) {
            markers.get(i).setVisible(true);
        }
    }

    public LatLng next() {
        LatLng ll = markers.get(index).getPosition();
        if (index < maxIndex - 1) {
            index++;
        }
        return ll;
    }

    public LatLng previous() {
        if (index > 0) {
            index--;
        }
        return markers.get(index).getPosition();

    }

}
