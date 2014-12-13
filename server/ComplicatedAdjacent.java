

import java.util.HashSet;

public class ComplicatedAdjacent {

	public static HashSet<String> adjacent_3 (String lastpositionID, String positionID, int LatitudeGridNum, int LongitudeGridNum) {
		HashSet<String> result = new HashSet<String>();
		int Latitude = Integer.parseInt(positionID.substring(0, 3));
		int Longitude = Integer.parseInt(positionID.substring(3, 6));
		
		for (int x = Latitude - 1; x <= Latitude + 1; x = x + 2) {
			int y = Longitude;
			if (x >= 0 && x <= LatitudeGridNum && y >= 0 && y <= LongitudeGridNum) {
				String xs = Integer.toString(x);
				String ys = Integer.toString(y);
				int xzero = 3 - xs.length();
				int yzero = 3 - ys.length();
				for (int i = 0; i < xzero; i++) xs = "0" + xs;
				for (int i = 0; i < yzero; i++) ys = "0" + ys;
				if (!lastpositionID.equals(xs + ys)) result.add(xs + ys);
			}
		}
		for (int y = Longitude - 1; y <= Longitude + 1; y = y + 2) {
			int x = Latitude;
			if (x >= 0 && x <= LatitudeGridNum && y >= 0 && y <= LongitudeGridNum) {
				String xs = Integer.toString(x);
				String ys = Integer.toString(y);
				int xzero = 3 - xs.length();
				int yzero = 3 - ys.length();
				for (int i = 0; i < xzero; i++) xs = "0" + xs;
				for (int i = 0; i < yzero; i++) ys = "0" + ys;
				if (!lastpositionID.equals(xs + ys)) result.add(xs + ys);
			}
		}
		
		return result;
	}
	
	public static HashSet<String> adjacent_4 (String positionID, int LatitudeGridNum, int LongitudeGridNum) {
		HashSet<String> result = new HashSet<String>();
		int Latitude = Integer.parseInt(positionID.substring(0, 3));
		int Longitude = Integer.parseInt(positionID.substring(3, 6));
		
		for (int x = Latitude - 1; x <= Latitude + 1; x = x + 2) {
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
				
		return result;
	}
	
	public static HashSet<String> adjacent_8 (String positionID, int LatitudeGridNum, int LongitudeGridNum) {
		HashSet<String> result = new HashSet<String>();
		int Latitude = Integer.parseInt(positionID.substring(0, 3));
		int Longitude = Integer.parseInt(positionID.substring(3, 6));
		
		for (int x = Latitude - 1; x <= Latitude + 1; x++) {
			for (int y = Longitude - 1; y <= Longitude + 1; y++) {
				if (!(x == Latitude && y == Longitude) && x >= 0 && x <= LatitudeGridNum && y >= 0 && y <= LongitudeGridNum) {
					String xs = Integer.toString(x);
					String ys = Integer.toString(y);
					int xzero = 3 - xs.length();
					int yzero = 3 - ys.length();
					for (int i = 0; i < xzero; i++) xs = "0" + xs;
					for (int i = 0; i < yzero; i++) ys = "0" + ys;
					result.add(xs + ys);
				}
			}
		}
		
		return result;
	}
	
	public static HashSet<String> adjacent (int adjacentNum, String lastpositionID, String positionID, int LatitudeGridNum, int LongitudeGridNum) {
		HashSet<String> result = new HashSet<String>();
		if (adjacentNum == 3) {
			result = ComplicatedAdjacent.adjacent_3(lastpositionID, positionID, LatitudeGridNum, LongitudeGridNum);
		}
		else if (adjacentNum == 4) {
			result = ComplicatedAdjacent.adjacent_4(positionID, LatitudeGridNum, LongitudeGridNum);
		}
		else if (adjacentNum == 8) {
			result = ComplicatedAdjacent.adjacent_8(positionID, LatitudeGridNum, LongitudeGridNum);
		}
		return result;
	}
	
}
