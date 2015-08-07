package com.example.ale.nearbylocations;

import com.google.android.gms.maps.model.Marker;
import java.util.ArrayList;

public class MarkerManager {
    private ArrayList<Marker> markers;

    public MarkerManager(){
        markers = new ArrayList<Marker>();
    }

    public void add(Marker marker){
        markers.add(marker);
    }

    public void hideMarkers(){
        for(int i=0; i<markers.size(); i++){
            markers.get(i).setVisible(false);
        }
    }

    public void showMarkers(){
        for(int i=0; i<markers.size(); i++){
            markers.get(i).setVisible(true);
        }
    }

}
