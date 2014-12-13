package com.example.project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
//import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.CameraUpdate;
import android.content.Context;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
//import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;


public class DisplayActivity extends FragmentActivity implements OnClickListener{
	
	   static LatLng myMarker;
	   private GoogleMap googleMap;
	   InputStream iStream = null;
	   List<PredictiveLocations> pLocations=new ArrayList<PredictiveLocations>();
	   List<PlaceDetails> places = new ArrayList<PlaceDetails>();
	   int flag =1;
	   String responseStr;
	   
	   double currLatitude;
	   double currLongitude;
	   String keyword;
	   EditText editText;
	   String data;
	   
	   
	   @Override
	   protected void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.activity_main);
	      
	      Intent i = getIntent();
	      Bundle extras = i.getExtras();
	      
	      //asyncLoad.execute();
	           
	      currLatitude = Double.parseDouble(extras.getString("LATI"));
	      currLongitude = Double.parseDouble(extras.getString("LONGI"));
	      keyword=extras.getString("KEYWORD").replace(" ", "+");
	      
	      
	      Log.d("sdj","sdjka");
	      
	      editText = (EditText)findViewById(R.id.keyword);
	      editText.setText(keyword.replace("+", " "),EditText.BufferType.EDITABLE);
	      editText.setOnClickListener(this);
	      	      
	      //myMarker = new LatLng(currLatitude,currLongitude);
	      	      
	      int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
	      if(status!=ConnectionResult.SUCCESS){ // Google Play Services are not available
	    	  int requestCode = 10;
	    	  Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
	    	  dialog.show();
	      }else {
	      
		      try { 
		            
		         // Creating a new non-UI thread task to download JSON data
		         PlacesTask placesTask = new PlacesTask();
		         
		         // Invokes the "doInBackground()" method of the class PlaceTask
		         placesTask.execute();
		        	         
		         
		      } catch (Exception e) {
		         e.printStackTrace();
		      }
	      }
	   }
	   
	   private String makeRequestUrl(double latitude, double longitude, String keyword)
	   {
		   StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
	         sb.append("location="+latitude+","+longitude);
	         sb.append("&rankby=distance");
	         sb.append("&keyword="+keyword);
	         sb.append("&sensor=true");
	         sb.append("&key=AIzaSyD0aUG39odlbpJE3p6Xy6LjPChFDbDfJ84");
	         return sb.toString();
	   }
	   
	   private String downloadUrl(String strUrl) throws IOException{
		   String data = "";
		   InputStream iStream = null;
		   HttpURLConnection urlConnection = null;
		   try{
			   URL url = new URL(strUrl);
			   	
			   // Creating an HTTP connection to communicate with URL
			   urlConnection = (HttpURLConnection) url.openConnection();
			 
			   // Connecting to URL
			   urlConnection.connect();
			   	
			   // Reading data from URL
			   iStream = urlConnection.getInputStream();
			   BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
			   StringBuffer sb = new StringBuffer();
			   String line = "";
			   while( ( line = br.readLine())!= null){
			   sb.append(line);
			   }
			   data = sb.toString();
			   br.close();
		   }catch(Exception e){
		   Log.d("Exception while downloading url", e.toString());
		   }finally{
		   iStream.close();
		   urlConnection.disconnect();
		   }
		   return data;
	   }
	   
	   	   
	   	   
	   /** A class, to download Google Places */
	   private class PlacesTask extends AsyncTask<String, Integer, List<String>>{
		   //String data = null; 
		   List <String> data = new ArrayList<String>();
		   String pandaURL="http://140.142.218.200:8080/projectone/seconservlet";
		   String pandaResp;
		   
		   // Invoked by execute() method of this object
		   @Override
		   protected List<String> doInBackground(String... url) {
			   try{
				   pandaResp=downloadUrl(pandaURL); 
				   				   
				   Log.d("Response", pandaResp);
				   JSONArray retrievejsonArray = new JSONArray(pandaResp);
				   
				   PredictiveLocations pl;
				   for (int a = 0; a < 3; ++a) 
				   {
					    JSONObject rec = retrievejsonArray.getJSONObject(a);
					    double retrievedlat = rec.getDouble("lat");
					    double retrievedlong = rec.getDouble("long");
					    double retrievedprob = rec.getDouble("prob");
					    pl=new PredictiveLocations(retrievedlat,retrievedlong,retrievedprob);
					    Log.d("P1",Double.toString(pl.latitude)+Double.toString(pl.longitude));
					    pLocations.add(pl);
					    Log.d("Response", "from retrieving : at a=="+a+"lat=="+retrievedlat+",long=="+retrievedlong+",prob=="+retrievedprob);
				   }
				   
				   Reranking r=new Reranking();
			       pLocations=r.sendCount(pLocations);
			         
			         String [] urlrequests = new String[3];
			         
			         for (int j = 0; j < urlrequests.length; j++) {
			        	    PredictiveLocations pLoc=pLocations.get(j);
			        	    urlrequests[j] = makeRequestUrl(pLoc.latitude,pLoc.longitude,keyword);
			        	}
				   		   
			   
				   for(int i=0 ; i<urlrequests.length;i++)
				   {
					   data.add(downloadUrl(urlrequests[i]));
				   }
			   }catch(Exception e){
				   Log.d("Background Task",e.toString());
			   }
			   return data;
		   }

		   // Executed after the complete execution of doInBackground() method
		   @Override
		   protected void onPostExecute(List<String> result){
			   Log.d("Inside Background Task","::2:::;");
			   			   
			   ParserTask parserTask = new ParserTask();
			   
			   // Start parsing the Google places in JSON format
			   // Invokes the "doInBackground()" method of the class ParseTask
			   places=parserTask.parseExecute(result);
			   parserTask.showOnMap(places);
		   }
	   }
	   
	   
	   private class ParserTask {
		   List<JSONObject> jObjects = new ArrayList<JSONObject>();
		   	
		   	// Invoked by execute() method of this object
		   	protected List<PlaceDetails> parseExecute(List<String> jsonData) {
		   		Log.d("Inside Background Task",":::3::");
		   		
		   		List<PlaceDetails> lPlaces;
		   		List <PlaceDetails> finalplacesList = new ArrayList<PlaceDetails>();
		   		
		   		PlacesResponseJSONParser placeJsonParser = new PlacesResponseJSONParser();
		   		String g= "PARSE";
		   		
		   		try{
		   				for(int i=0;i<jsonData.size();i++)
		   				{
		   					jObjects.add(new JSONObject(jsonData.get(i)));
		   				}
		   				
		   				for(int i=0; i<jObjects.size();i++)
		   				{
		   					lPlaces = new ArrayList<PlaceDetails>();
		   					/** Getting the parsed data as a List construct */
		   					lPlaces = placeJsonParser.parse(jObjects.get(i),pLocations.get(i).count);
		   					finalplacesList.addAll(lPlaces);	
		   				}
		   		}catch(Exception e){
		   				Log.d("Exception",e.toString());
		   		}
		   		if(finalplacesList.size() != 0)
				   {
					   Log.d("DATA not NULL",g);  
				   }
				   else
				   {
					   Log.d("DATA NULL",g); 
				   }
		   		return finalplacesList;
		   	}

		   	
		   	// Executed after the complete execution of doInBackground() method
		   	
		   	protected void showOnMap(List<PlaceDetails> list){
		   	
		   	// Clears all the existing markers
		   		//googleMap.clear();
		   		
		   		Criteria criteria = new Criteria();
		   		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		   	    String provider = locationManager.getBestProvider(criteria, false);
		   	    //Location location = locationManager.getLastKnownLocation(provider);
		   	    		   	    
		   		myMarker = new LatLng(currLatitude,currLongitude);
		   		if (googleMap == null) {
		               googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		            }
		         //googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		         googleMap.addMarker(new MarkerOptions().position(myMarker).title("Your Location").snippet("Your Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
		         
		         
		   		for(int i=0;i<list.size();i++){
		   		
			   		// Creating a marker
			   		// Getting a place from the places list
			   		PlaceDetails place = list.get(i);
			   		
			   		// Getting latitude of the place
			   		double lat = place.getLatitude();
			   		
			   		// Getting longitude of the place
			   		double lng = place.getLongitude();
			   		
			   		// Getting name
			   		String name = place.getName();
			   		Log.d("NAME::",name);
			   		
			   		// Getting vicinity
			   		String vicinity = place.getVicinity();
			   		
			   		LatLng latLng = new LatLng(lat, lng);
			   		
			   					   			
			   		// Setting the position for the marker
			   		//markerOptions.position(latLng);
			   		
			   		// Setting the title for the marker.
			   		//This will be displayed on taping the marker
		   		
			   		if (googleMap == null) {
			               googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
			            }
			   		// Placing a marker on the touched position
			   		//googleMap.addMarker(markerOptions);
			   					   		
			   		googleMap.addMarker(new MarkerOptions().position(latLng).title(name).snippet(vicinity).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
		   	}
		   	CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(myMarker, 10);
		   	googleMap.animateCamera(yourLocation);
		}
	 }
	   
	   public void sendvalues(View v)
	    {
	    	Intent i = new Intent(DisplayActivity.this, DisplayActivity.class);
	    	Bundle extras = new Bundle();
	    		    	
	    	
	    	//latitude = ((EditText)findViewById(R.id.latitude)).getText().toString();
		    //longitude = ((EditText)findViewById(R.id.longitude)).getText().toString();
	    	keyword = ((EditText) findViewById(R.id.keyword)).getText().toString();
		    
		    extras.putString("LATI",String.valueOf(currLatitude));
	    	extras.putString("LONGI",String.valueOf(currLongitude));
	    	extras.putString("KEYWORD",keyword);
	    	
	    	i.putExtras(extras);
	    	startActivity(i);
	    }
	   
	   @Override
	   public void onClick(View v) {
	       // TODO Auto-generated method stub
	       editText.setText("");
	   }
}
