

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class ComplicatedCalculation {
	
	public static HashMap<String, Double> predictedvectorbytrajectory (
			ArrayList<String> trajectory, 
			double p,
			int LatitudeGridNum,
			int LongitudeGridNum,
			int adjacentNum
			) {
		HashMap<String, Double> result = new HashMap<String, Double>();
		HashSet<String> adjacent = ComplicatedAdjacent.adjacent(adjacentNum, trajectory.get(trajectory.size() - 2), trajectory.get(trajectory.size() - 1), LatitudeGridNum, LongitudeGridNum);
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
			if (sum > 0.0) {
				result.put(positionID, result.get(positionID) / sum);
			}
			else {
				result.put(positionID, 1.0 / result.size());
			}
		}		
		return result;
	}
	
}
