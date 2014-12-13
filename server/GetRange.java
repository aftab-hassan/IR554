
import java.util.ArrayList;

public class GetRange {

	public static void getRange () throws Exception {
		String path = "E:/Ling_Ding/07062014/uber_gps_tsv/";
		String filename = "all.tsv";
		int timeinterval = 60;
		String pathandfile = path + filename;
		ArrayList<TimePositionReadFile> data = ReadFile.readfile(pathandfile, timeinterval);
		double MaxLatitude = data.get(0).get_Latitude();
		double MinLatitude = data.get(0).get_Latitude();
		double MaxLongitude = data.get(0).get_Longitude();
		double MinLongitude = data.get(0).get_Longitude();
		for (int i = 1; i < data.size(); i++) {
			double Latitude = data.get(i).get_Latitude();
			double Longitude = data.get(i).get_Longitude();
			if (Latitude > MaxLatitude) MaxLatitude = Latitude;
			if (Latitude < MinLatitude) MinLatitude = Latitude;
			if (Longitude > MaxLongitude) MaxLongitude = Longitude;
			if (Longitude < MinLongitude) MinLongitude = Longitude;
		}
		System.out.println("MaxLatitude = " + MaxLatitude);
		System.out.println("MinLatitude = " + MinLatitude);
		System.out.println("MaxLongitude = " + MaxLongitude);
		System.out.println("MinLongitude = " + MinLongitude);
	}
	
}
