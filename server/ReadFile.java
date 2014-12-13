
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ReadFile {

	public static TimePositionReadFile onedata (String oneline, int timeinterval) {
		StringTokenizer st = new StringTokenizer(oneline);
		st.nextToken();
		String timestamp = st.nextToken();
		double Latitude = Double.parseDouble(st.nextToken());
		double Longitude = Double.parseDouble(st.nextToken());
		String date = timestamp.substring(0, 10);
		int hour = Integer.parseInt(timestamp.substring(11, 13));
		int minute = Integer.parseInt(timestamp.substring(14, 16));
		int second = Integer.parseInt(timestamp.substring(17, 19));
		second = second + minute * 60 + hour * 3600;
		int time = second / timeinterval;
		TimePositionReadFile result = new TimePositionReadFile(date, time, Latitude, Longitude);
		return result;
	}
	
	public static ArrayList<TimePositionReadFile> readfile (String pathandfile, int timeinterval) throws Exception {
		ArrayList<TimePositionReadFile> result = new ArrayList<TimePositionReadFile>();
		BufferedReader br = new BufferedReader(new FileReader(pathandfile));
		String oneline = br.readLine();
		String nowdate = "";
		int nowtime = -1;
		double sumLatitude = 0.0;
		double sumLongitude = 0.0;
		int count = 0;
		while (oneline != null && !oneline.isEmpty()) {
			TimePositionReadFile onedata = ReadFile.onedata(oneline, timeinterval);
			String date = onedata.get_date();
			int time = onedata.get_time();
			double Latitude = onedata.get_Latitude();
			double Longitude = onedata.get_Longitude();
			if (nowdate.equals(date) && nowtime == time) {
				sumLatitude = sumLatitude + Latitude;
				sumLongitude = sumLongitude + Longitude;
				count++;
			}
			else {
				if (nowtime != -1) {
					TimePositionReadFile oneinterval = new TimePositionReadFile(nowdate, nowtime, sumLatitude / count, sumLongitude/ count);
					result.add(oneinterval);
				}
				nowdate = date;
				nowtime = time;
				sumLatitude = Latitude;
				sumLongitude = Longitude;
				count = 1;
			}
			oneline = br.readLine();
		}
		TimePositionReadFile oneinterval = new TimePositionReadFile(nowdate, nowtime, sumLatitude / count, sumLongitude/ count);
		result.add(oneinterval);
		br.close();
		return result;
	}
	
}
