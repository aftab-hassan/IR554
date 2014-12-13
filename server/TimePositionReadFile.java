
public class TimePositionReadFile {

	private String date;
	private int time;
	private double Latitude;
	private double Longitude;
	
	public TimePositionReadFile (String date, int time, double Latitude, double Longitude) {
		this.date = date;
		this.time = time;
		this.Latitude = Latitude;
		this.Longitude = Longitude;
	}
	
	public String get_date () {
		return this.date;
	}
	
	public int get_time () {
		return this.time;
	}
	
	public double get_Latitude () {
		return this.Latitude;
	}
	
	public double get_Longitude () {
		return this.Longitude;
	}
	
	public void print_out () {
		System.out.println(this.date + " " + this.time + " " + Math.round(this.Latitude * 100.0) / 100.0 + " " + Math.round(this.Longitude * 100.0) / 100.0);
	}
	
	public void to_file (StringBuffer sb) {
		sb.append(this.date + " " + this.time + " " + this.Latitude + " " + this.Longitude + "\r\n");
	}
	
	public static String to_positionID (
			double Latitude, double Longitude, 
			double MaxLatitude, double MinLatitude, double LatitudeGrid,
			double MaxLongitude, double MinLongitude, double LongitudeGrid
			) {
		String x = Integer.toString((int) ((Latitude - MinLatitude) / LatitudeGrid));
		String y = Integer.toString((int) ((Longitude - MinLongitude) / LongitudeGrid));
		int xzero = 3 - x.length();
		int yzero = 3 - y.length();
		for (int i = 0; i < xzero; i++) x = "0" + x;
		for (int i = 0; i < yzero; i++) y = "0" + y;
		String result = x + y;
		return result;
	}
	
}
