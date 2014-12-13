package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity{
	
	Button sendbutton;
	GPSTracker gps;
	String latitude;
	String longitude;
	private GoogleMap googleMap;
	
	
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);       
        setContentView(R.layout.activity_main);
        gps = new GPSTracker(this);
        LatLng latLng = new LatLng(/*31.229000,121.467800*/gps.getLatitude(), gps.getLongitude());
        if (googleMap == null) {
            googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
         }
        //googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        googleMap.addMarker(new MarkerOptions().position(latLng));
        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(latLng, 10);
	   	googleMap.animateCamera(yourLocation);
        
    }
    
    public void sendvalues(View v)
    {
    	Intent i = new Intent(this, DisplayActivity.class);
    	Bundle extras = new Bundle();
    	
    	//gps = new GPSTracker(this);

    	if (gps.canGetLocation()) {
    		latitude = String.valueOf(gps.getLatitude());
    		longitude = String.valueOf(gps.getLongitude());
            Log.d("Your Location", "latitude:" + gps.getLatitude() + ", longitude: " + gps.getLongitude());
        } else {
            // Can't get user's current location
        	Log.d("Couldn't get location information","Please enable GPS");
            // stop executing code by return
            return;
        }
    	
    	
    	//latitude = ((EditText)findViewById(R.id.latitude)).getText().toString();
	    //longitude = ((EditText)findViewById(R.id.longitude)).getText().toString();
	    String keyword = ((EditText) findViewById(R.id.keyword)).getText().toString();
	    
	    extras.putString("LATI",latitude);
    	extras.putString("LONGI",longitude);
    	extras.putString("KEYWORD",keyword);
    	//Log.d("LAT {0}, LON {1}, Key {2}",latitude);
    	i.putExtras(extras);
    	startActivity(i);
    }
}
