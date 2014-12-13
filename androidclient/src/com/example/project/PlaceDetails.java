package com.example.project;

/*  
 * Class to store location related information from Google Place API.
 */

public class PlaceDetails {
	
	String pName;
	String pVicinity;
		
	double pLatitude;
	double pLongitude;
	double pProbability;
	
	PlaceDetails()
	{
	
	}
	
	PlaceDetails(String name, String vicinity,  
			double lat, double longi, double probability)
	{
		this.pLatitude = lat;
		this.pLongitude = longi;
		this.pName = name;
		this.pVicinity = vicinity;
		this.pProbability = probability;		
	}
	
	void setName(String name)
	{
		this.pName =name;
	}
	
	String getName()
	{
		return this.pName;
	}
	
	void setVicinity(String vicinity)
	{
		this.pVicinity=vicinity;
	}
	
	String getVicinity()
	{
		return this.pVicinity;
	}
	
	void setLatitude(double lat)
	{
		this.pLatitude=lat;
	}
	
	double getLatitude()
	{
		return this.pLatitude;
	}
	
	void setLongitude(double longi)
	{
		this.pLongitude=longi;
	}
	
	double getLongitude()
	{
		return this.pLongitude;
	}
	
	void setProbability(double prob)
	{
		this.pProbability=prob;
	}
	
	double getProbability()
	{
		return this.pLongitude;
	}
	
}
