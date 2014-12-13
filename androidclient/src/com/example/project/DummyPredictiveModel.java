package com.example.project;

import java.util.ArrayList;
import java.util.List;



public class DummyPredictiveModel {
	
	List<PredictiveLocations> pandaAlgorithm(double lat, double longi)
	{
		PredictiveLocations plc1 = new PredictiveLocations(47.1830515,-122.2862785,0.6); // payallup
		PredictiveLocations plc2 = new PredictiveLocations(47.1667,-122.5333,0.3);//lakewood
		PredictiveLocations plc3 = new PredictiveLocations(47.3078924,-122.3450087,0.1);//federal way
		
		//PredictiveLocations plc1 = new PredictiveLocations(31.236800,121.437100,0.6); // payallup
		//PredictiveLocations plc2 = new PredictiveLocations(31.246300,121.440000,0.3);//lakewood
		//PredictiveLocations plc3 = new PredictiveLocations(31.277000,121.473600,0.1);//federal way
				
		
		//PredictiveLocations plc1 = new PredictiveLocations(47.6097,-122.3331,0.5); // seattle
		//PredictiveLocations plc2 = new PredictiveLocations(47.6858,-122.1917,0.2);//kirkland
		//PredictiveLocations plc3 = new PredictiveLocations(47.4815449,-122.1935124,0.3);//renton
		
		List<PredictiveLocations> locs = new ArrayList<PredictiveLocations>();
		locs.add(plc1);
		locs.add(plc2);
		locs.add(plc3);
		
		return locs;
		
	}

}
