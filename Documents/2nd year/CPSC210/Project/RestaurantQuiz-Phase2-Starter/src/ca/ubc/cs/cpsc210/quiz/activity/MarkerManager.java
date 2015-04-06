package ca.ubc.cs.cpsc210.quiz.activity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pcarter on 14-11-06.
 *
 * Manager for markers plotted on map
 */
public class MarkerManager {
    private GoogleMap map;
    private List<Marker> markers;
    private Marker aMarker;


    /**
     * Constructor initializes manager with map for which markers are to be managed.
     * @param map  the map for which markers are to be managed
     */
    public MarkerManager(GoogleMap map) {
        this.map = map;
        this.markers = new ArrayList<Marker>();

    }

    /**
     * Get last marker added to map
     * @return  last marker added
     */
    public Marker getLastMarker() {
        return markers.get(markers.size()-1);
    }

    /**
     * Add green marker to show position of restaurant
     * @param point   the point at which to add the marker
     * @param title   the marker's title
     */
    public void addRestaurantMarker(LatLng point, String title) {
        map.addMarker(new MarkerOptions()
                .position(point)
                .title(title)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
    }


    /**
     * Add a marker to mark latest guess from user.  Only the most recent two positions selected
     * by the user are marked.  The distance from the restaurant is used to create the marker's title
     * of the form "xxxx m away" where xxxx is the distance from the restaurant in metres (truncated to
     * an integer).
     *
     * The colour of the marker is based on the distance from the restaurant:
     * - red, if the distance is 3km or more
     * - somewhere between red (at 3km) and green (at 0m) (on a linear scale) for other distances
     *
     * @param latLng
     * @param distance
     */
    public void addMarker(LatLng latLng, double distance) {
        int intDistance = ((int) Math.round(distance));
        aMarker= map.addMarker(new MarkerOptions()
                .position(latLng)
                .title(intDistance + "m away")
                .icon(BitmapDescriptorFactory.defaultMarker(this.getColour(distance))));
        markers.add(aMarker);
        if (markers.size()>2){
            markers.get(0).remove();
            markers.remove(0);
        }
    }

    /**
     * Remove markers that mark user guesses from the map
     */

    public void removeMarkers() {
        for (Marker aMarker: markers){
            aMarker.remove();
        }
    }

    /**
     * Produce a colour on a linear scale between red and green based on distance:
     *
     * - red, if distance is 3km or more
     * - somewhere between red (at 3km) and green (at 0m) (on a linear scale) for other distances
     * @param distance  distance from restaurant
     * @return  colour of marker
     */
    private float getColour(double distance) {
        float color = 0;
        if (distance >= 3000){
            color =0.0f;
        }
        else if (0<distance&&distance<3000){
            color=(((3000-((float) distance))/3000)*120);
        }
        return color;
    }


}
