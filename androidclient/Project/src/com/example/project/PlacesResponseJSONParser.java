package com.example.project;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PlacesResponseJSONParser {
	
	public List<PlaceDetails> parse(JSONObject jObject, int count){
		 
        JSONArray jPlaces = null;
        try {
            /** Retrieves all the elements in the 'places' array */
            jPlaces = jObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /** Invoking getPlaces with the array of json object
        * where each json object represent a place
        */
        return getPlaces(jPlaces,count);
    }
	
	// Each place is one list entry
	private List<PlaceDetails> getPlaces(JSONArray jPlaces, int count){
        
        int pCount = (jPlaces.length()<count) ? jPlaces.length() : count;
        
        List<PlaceDetails> placesList = new ArrayList<PlaceDetails>();
        PlaceDetails place=null;
        
        /** Taking each place, parses and adds to list object */
        for(int i=0; i<pCount;i++){
            try {
                /** Call getPlace with place JSON object to parse the place */
                place = getPlace((JSONObject)jPlaces.get(i));
                placesList.add(place);
 
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
 
        return placesList;
    }
	
	
	// Parsing response for place detail. One JSON result.
	private PlaceDetails getPlace(JSONObject jPlace){
		 
        //HashMap<String, String> place = new HashMap<String, String>();
                
        String placename = "-NA-";
        String vicinity="-NA-";
        double latitude=0.0;
        double longitude=0.0;
        //double rating=0;
        double probability=0;
        
        PlaceDetails pDetails=new PlaceDetails();
 
        try {
            // Extracting Place name, if available
            if(!jPlace.isNull("name")){
                placename = jPlace.getString("name");
            }
 
            // Extracting Place Vicinity, if available
            if(!jPlace.isNull("vicinity")){
                vicinity = jPlace.getString("vicinity");
            }
 
            latitude = Double.parseDouble(jPlace.getJSONObject("geometry").getJSONObject("location").getString("lat"));
            longitude = Double.parseDouble(jPlace.getJSONObject("geometry").getJSONObject("location").getString("lng"));
            //rating = Double.parseDouble(jPlace.getString("rating"));
 
            /*place.put("place_name", placeName);
            place.put("vicinity", vicinity);
            place.put("lat", latitude);
            place.put("lng", longitude);*/
            
            //pDetails= new PlaceDetails(placename, vicinity, rating, latitude, longitude,0);    
            pDetails.setName(placename);
            pDetails.setVicinity(vicinity);
            //pDetails.setRating(rating);
            pDetails.setLatitude(latitude);
            pDetails.setLongitude(longitude);
            pDetails.setProbability(probability);            
            
 
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pDetails;
    }

}
