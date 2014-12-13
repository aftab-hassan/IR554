
import java.util.HashMap;
import java.util.HashSet;

public class TimePosition {

	private String date;
	private int time;
	private String positionID;
	
	public TimePosition (String date, int time, String positionID) {
		this.date = date;
		this.time = time;
		this.positionID = positionID;
	}
	
	public String get_date () {
		return this.date;
	}
	
	public int get_time () {
		return this.time;
	}
	
	public String get_positionID () {
		return this.positionID;
	}
	
	public void print_out () {
		System.out.println("date = " + this.date + "  time = " + this.time + "  positionID = " + this.positionID);
	}
	
	public static HashSet<String> adjacent (String positionID, int LatitudeGridNum, int LongitudeGridNum) {
		HashSet<String> result = new HashSet<String>();
		int Latitude = Integer.parseInt(positionID.substring(0, 3));
		int Longitude = Integer.parseInt(positionID.substring(3, 6));
		
		for (int x = Latitude - 1; x <= Latitude + 1; x = x + 1) {
			int y = Longitude;
			if (x >= 0 && x <= LatitudeGridNum && y >= 0 && y <= LongitudeGridNum) {
				String xs = Integer.toString(x);
				String ys = Integer.toString(y);
				int xzero = 3 - xs.length();
				int yzero = 3 - ys.length();
				for (int i = 0; i < xzero; i++) xs = "0" + xs;
				for (int i = 0; i < yzero; i++) ys = "0" + ys;
				result.add(xs + ys);
			}
		}
		for (int y = Longitude - 1; y <= Longitude + 1; y = y + 2) {
			int x = Latitude;
			if (x >= 0 && x <= 55 && y >= 0 && y <= 87) {
				String xs = Integer.toString(x);
				String ys = Integer.toString(y);
				int xzero = 3 - xs.length();
				int yzero = 3 - ys.length();
				for (int i = 0; i < xzero; i++) xs = "0" + xs;
				for (int i = 0; i < yzero; i++) ys = "0" + ys;
				result.add(xs + ys);
			}
		}
		
//		for (int x = Latitude - 1; x <= Latitude + 1; x++) {
//			for (int y = Longitude - 1; y <= Longitude + 1; y++) {
//				if (!(x == Latitude && y == Longitude) && x >= 0 && x <= 55 && y >= 0 && y <= 87) {
//					String xs = Integer.toString(x);
//					String ys = Integer.toString(y);
//					int xzero = 3 - xs.length();
//					int yzero = 3 - ys.length();
//					for (int i = 0; i < xzero; i++) xs = "0" + xs;
//					for (int i = 0; i < yzero; i++) ys = "0" + ys;
//					result.add(xs + ys);
//				}
//			}
//		}
		
		return result;
	}
	
	public static double distance (String positionID1, String positionID2) {
		int x1 = Integer.parseInt(positionID1.substring(0, 3));
		int y1 = Integer.parseInt(positionID1.substring(3, 6));
		int x2 = Integer.parseInt(positionID2.substring(0, 3));
		int y2 = Integer.parseInt(positionID2.substring(3, 6));
		double distance = Math.sqrt((double) ((x1 - x2) ^ 2 + (y1 - y2) ^ 2));
		return distance;
	}
	
	public static HashMap<String, Double> fixedPosition (String positionID) {
		HashMap<String, Double> result = new HashMap<String, Double>();
		result.put(positionID, 1.0);
		return result;
	}
	
}
