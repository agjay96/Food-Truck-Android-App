package com.example.foodtruck;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//public class MapActivity extends AppCompatActivity {
//    String json_string;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.map_activity);
//
//        json_string=getIntent().getExtras().getString("json_data");
//        try {
//            JSONArray jsonArray = new JSONArray(json_string);
//            String lat, lng;
//            for(int i=0; i<10; i++){
//                JSONObject jsonObject= jsonArray.getJSONObject(i);
//
//                lat = jsonObject.getString("latitude");
//                lng = jsonObject.getString("longitude");
//
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//    }
//}






/**
 * A demo class that stores and retrieves data objects with each marker.
 */
public class MapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {


    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);

        SupportMapFragment supportMapFragment= (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);

    }

    /** Called when the map is ready. */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setOnMarkerClickListener(this);
        String extras = getIntent().getExtras().getString("json_data");
        System.out.println("Intennnnnnnnnnnnnnnnntttttttttttttttttttttt"+extras);
        map = googleMap;
        String[] each=extras.split("\\)");
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for(int i=0; i<each.length; i++){
            System.out.println("Eachhhhhhhhhhhhhhhhhhhh   "+each[i]);
            String[] elements= each[i].split("\\(");
            LatLng latLng = new LatLng(Double.valueOf(elements[4]), Double.valueOf(elements[5]));
            googleMap.addMarker(new MarkerOptions().position(latLng).title(elements[0])).setTag(each[i]);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

            builder.include(latLng);
        }
        LatLngBounds bounds = builder.build();
        int padding = 0; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        googleMap.moveCamera(cu);

    }

    /** Called when the user clicks a marker. */
    @Override
    public boolean onMarkerClick(Marker marker) {
//
//        System.out.println("Detailsssssssssssssssssss");
//        // Retrieve the data from the marker.
        String details = (String) marker.getTag();
        System.out.println("Details"+details);
        String[] window= details.split("\\(");

        TextView tv1 = (TextView)findViewById(R.id.applicant);
        TextView tv2 = (TextView)findViewById(R.id.address);
        TextView tv3 = (TextView)findViewById(R.id.items);
        TextView tv4 = (TextView)findViewById(R.id.timing);
        tv1.setText(window[0]);
        tv2.setText(window[1]);
        tv3.setText(window[2]);
        tv4.setText(window[3]);

//
//        // Check if a click count was set, then display the click count.
//        if (clickCount != null) {
//            clickCount = clickCount + 1;
//            marker.setTag(clickCount);
//            Toast.makeText(this,
//                    marker.getTitle() +
//                            " has been clicked " + clickCount + " times.",
//                    Toast.LENGTH_SHORT).show();
//        }
//
//        // Return false to indicate that we have not consumed the event and that we wish
//        // for the default behavior to occur (which is for the camera to move such that the
//        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }
}