
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class PrintOutVector {
	
	public static double[][] printOutVector(
			String date,
			String timebySecond,
			int k,
			double p,
			int adjacentNum
			) throws Exception {
		System.out.println("k = " + k + "   p = " + p);
		System.out.println("date = " + date + "  timebySecond = " + timebySecond);
		
		BufferedReader br = new BufferedReader(new FileReader("/home/aftab/Desktop/shanghai_taxi/positionIDbyCar/00105.txt"));
		String oneline = br.readLine();
		ArrayList<String> dateList = new ArrayList<String>();
		ArrayList<String> timeList = new ArrayList<String>();
		ArrayList<String> data = new ArrayList<String>();
		while (oneline != null && oneline.length() > 0) {
			String[] s = oneline.split(" ");
			dateList.add(s[0]);
			timeList.add(s[1]);
			data.add(s[2]);
			oneline = br.readLine();
		}
		br.close();
		
		br = new BufferedReader(new FileReader("/home/aftab/Desktop/shanghai_taxi/positionIDbyCar/GridNum.txt"));
		oneline = br.readLine();
		int LatitudeGridNum = Integer.parseInt(oneline.split(" ")[1]);
		oneline = br.readLine();
		int LongitudeGridNum = Integer.parseInt(oneline.split(" ")[1]);
		br.close();
		System.out.println("LatitudeGridNum = " + LatitudeGridNum + "  LongitudeGridNum = " + LongitudeGridNum +"\n");
		
		double MaxLatitude = 32.0;
		double MinLatitude = 30.0;
		double MaxLongitude = 122.0;
		double MinLongitude = 120.0;
		double [][] result = new double[3][3];
		
		for (int i = k; i < data.size(); i++) {
			if (!dateList.get(i).equals(date) || !timeList.get(i).equals(timebySecond)) continue;
			ArrayList<String> trajectory = new ArrayList<String>();
			for (int j = 0; j < i; j++){
				trajectory.add(data.get(j));
			}
			HashMap<String, Double> prvector = ComplicatedCalculation.predictedvectorbytrajectory(trajectory, p, LatitudeGridNum, LongitudeGridNum, adjacentNum);
			for (String positionID : prvector.keySet()){
				
				//convertToLatLong.writefile(positionID, prvector);
				
				/* calculate the latitude and longtitude from cell number*/
				int cellNumber = Integer.parseInt(positionID);
				int latitudeNo = convertToLatLong.cropCellNumber(cellNumber, 0, 3);
				int longitudeNo = convertToLatLong.cropCellNumber(cellNumber, 3, 6);
				double latitude = convertToLatLong.converterToLatitude(latitudeNo, MaxLatitude, MinLatitude, LatitudeGridNum);
				double longtitude = convertToLatLong.converterToLatitude(longitudeNo, MaxLongitude, MinLongitude, LongitudeGridNum);
				for(int h = 0 ; h < 3; h++){
					result[h][0] = latitude;
					result[h][1] = longtitude;
					result[h][2] = prvector.get(positionID);					
				}
				//System.out.println(positionID + " " + prvector.get(positionID));
				System.out.println("For positionID = " + positionID +":");
				System.out.println("Probability =  " + prvector.get(positionID));
				System.out.println("Latitude = " + latitude + "\n" + "Longtitude = " + longtitude + "\n");
				
			}
			break;
		}
	  return result;
	}
}
