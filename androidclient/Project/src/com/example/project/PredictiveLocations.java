package com.example.project;

public class PredictiveLocations {
	
	double latitude;
	double longitude;
	double probability;
	int count;
	
	PredictiveLocations()
	{
		
	}
	
	PredictiveLocations(double latitude, double longitude)
	{
		this.latitude = latitude;
		this.longitude = longitude;
		this.probability = 0;
	}
	
	PredictiveLocations(double latitude, double longitude, double prob)
	{
		this.latitude = latitude;
		this.longitude = longitude;
		this.probability = prob;
	}
	
	void setLatitude(double latitude)
	{
		this.latitude=latitude;
	}
	
	void setLongitude(double longitude)
	{
		this.longitude=longitude;
	}
	
	void setProbability(double probability)
	{
		this.probability=probability;
	}
	
	double getProbability()
	{
		return this.probability;
	}
	
	void setCount(int c)
	{
		this.count=c;
	}
	
	int getCount()
	{
		return this.count;
	}
}
