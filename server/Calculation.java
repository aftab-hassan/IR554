

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Calculation {

	public static HashMap<String, Double> predictedvectorbytrajectory (
			ArrayList<String> trajectory, 
			double p,
			int LatitudeGridNum,
			int LongitudeGridNum
			) {
		HashMap<String, Double> result = new HashMap<String, Double>();
		HashSet<String> adjacent = TimePosition.adjacent(trajectory.get(trajectory.size() - 1), LatitudeGridNum, LongitudeGridNum);
		for (String positionID : adjacent) {
			double pr = 1.0;
			double lastdistance = TimePosition.distance(positionID, trajectory.get(0));
			for (int i = 1; i < trajectory.size(); i++) {
				double thisdistance = TimePosition.distance(positionID, trajectory.get(i));
				if (thisdistance >= lastdistance) {
					pr = pr * p;
				}
				else {
					pr = pr * (1.0 - p);
				}
				lastdistance = thisdistance;
			}
			result.put(positionID, pr);
		}
		double sum = 0.0;
		for (String positionID : result.keySet()) {
			sum = sum + result.get(positionID);
		}
		for (String positionID : result.keySet()) {
			result.put(positionID, result.get(positionID) / sum);
		}		
		return result;
	}
	
	public static HashMap<String, Double> predictedvector (
			ArrayList<HashMap<String, Double>> knownpr, 
			double p,
			int LatitudeGridNum,
			int LongitudeGridNum
			) {
		HashMap<String, Double> result = new HashMap<String, Double>();
		HashMap<String, Double> kpower = new HashMap<String, Double>();
		kpower.put("", 1.0);
		for (int i = 0; i < knownpr.size(); i++) {
			HashMap<String, Double> tmp = new HashMap<String, Double>();
			HashMap<String, Double> thisvec = knownpr.get(i);
			for (String s1 : kpower.keySet()) {
				for (String s2 : thisvec.keySet()) {
					tmp.put(s1 + s2, kpower.get(s1) * thisvec.get(s2));
				}
			}
			kpower = tmp;
		}
		for (String start : kpower.keySet()) {
			double thispr = kpower.get(start);
			ArrayList<String> trajectory = new ArrayList<String>();
			for (int i = 0; i < knownpr.size(); i++) {
				trajectory.add(start.substring(i * 6, i * 6 + 6));
			}
			HashMap<String, Double> prvec = Calculation.predictedvectorbytrajectory(trajectory, p, LatitudeGridNum, LongitudeGridNum);
			for (String positionID : prvec.keySet()) {
				if (result.containsKey(positionID)) {
					result.put(positionID, result.get(positionID) + prvec.get(positionID) * thispr);
				}
				else {
					result.put(positionID, prvec.get(positionID) * thispr);
				}
			}
		}
		return result;
	}
	
	public static ArrayList<HashMap<String, Double>> nprvectors (
			ArrayList<HashMap<String, Double>> knownpr, 
			double p,
			int n,
			int LatitudeGridNum,
			int LongitudeGridNum
			) {
		ArrayList<HashMap<String, Double>> result = new ArrayList<HashMap<String, Double>>();
		for (int i = 0; i < n; i++) {
			HashMap<String, Double> onevec = Calculation.predictedvector(knownpr, p, LatitudeGridNum, LongitudeGridNum);
			result.add(onevec);
			knownpr.remove(0);
			knownpr.add(onevec);
		}
		return result;
	}
	
}
