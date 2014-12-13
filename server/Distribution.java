

import java.util.ArrayList;
import java.util.HashMap;

public class Distribution {

	public static HashMap<Integer, ArrayList<TimePositionReadFile>> carIDtoData (ArrayList<TimePositionReadFile> data, int timeinterval) {
		HashMap<Integer, ArrayList<TimePositionReadFile>> result = new HashMap<Integer, ArrayList<TimePositionReadFile>>();
		HashMap<String, HashMap<Integer, ArrayList<ArrayList<TimePositionReadFile>>>> distributedbyhour = new HashMap<String, HashMap<Integer, ArrayList<ArrayList<TimePositionReadFile>>>>();
		int onehour = 3600 / timeinterval;
		String thisdate = data.get(0).get_date();
		int thishour = data.get(0).get_time() / onehour;
		ArrayList<TimePositionReadFile> continuedata = new ArrayList<TimePositionReadFile>();
		for (int i = 0; i < data.size(); i++) {
			TimePositionReadFile onedata = data.get(i);
			String date = data.get(i).get_date();
			int hour = data.get(i).get_time() / onehour;
			if (thisdate.equals(date) && thishour == hour) {
				continuedata.add(onedata);
			}
			else {
				if (distributedbyhour.containsKey(thisdate)) {
					HashMap<Integer, ArrayList<ArrayList<TimePositionReadFile>>> tmp = distributedbyhour.get(thisdate);
					if (tmp.containsKey(thishour)) {
						ArrayList<ArrayList<TimePositionReadFile>> tmp1 = tmp.get(thishour);
						tmp1.add(continuedata);
					}
					else {
						ArrayList<ArrayList<TimePositionReadFile>> tmp1 = new ArrayList<ArrayList<TimePositionReadFile>>();
						tmp1.add(continuedata);
						tmp.put(thishour, tmp1);
					}
				}
				else {
					HashMap<Integer, ArrayList<ArrayList<TimePositionReadFile>>> tmp = new HashMap<Integer, ArrayList<ArrayList<TimePositionReadFile>>>();
					ArrayList<ArrayList<TimePositionReadFile>> tmp1 = new ArrayList<ArrayList<TimePositionReadFile>>();
					tmp1.add(continuedata);
					tmp.put(thishour, tmp1);
					distributedbyhour.put(thisdate, tmp);
				}
				thisdate = onedata.get_date();
				thishour = onedata.get_time() / onehour;
				continuedata = new ArrayList<TimePositionReadFile>();
				continuedata.add(onedata);
			}
		}
		if (distributedbyhour.containsKey(thisdate)) {
			HashMap<Integer, ArrayList<ArrayList<TimePositionReadFile>>> tmp = distributedbyhour.get(thisdate);
			if (tmp.containsKey(thishour)) {
				ArrayList<ArrayList<TimePositionReadFile>> tmp1 = tmp.get(thishour);
				tmp1.add(continuedata);
			}
			else {
				ArrayList<ArrayList<TimePositionReadFile>> tmp1 = new ArrayList<ArrayList<TimePositionReadFile>>();
				tmp1.add(continuedata);
				tmp.put(thishour, tmp1);
			}
		}
		else {
			HashMap<Integer, ArrayList<ArrayList<TimePositionReadFile>>> tmp = new HashMap<Integer, ArrayList<ArrayList<TimePositionReadFile>>>();
			ArrayList<ArrayList<TimePositionReadFile>> tmp1 = new ArrayList<ArrayList<TimePositionReadFile>>();
			tmp1.add(continuedata);
			tmp.put(thishour, tmp1);
			distributedbyhour.put(thisdate, tmp);
		}
		for (String date : distributedbyhour.keySet()) {
			for (int hour : distributedbyhour.get(date).keySet()) {
				ArrayList<ArrayList<TimePositionReadFile>> byhour = distributedbyhour.get(date).get(hour);
				for (int i = 0; i < byhour.size(); i++) {
					ArrayList<TimePositionReadFile> onelist = byhour.get(i);
					if (result.containsKey(i)) {
						result.get(i).addAll(onelist);
					}
					else {
						result.put(i, onelist);
					}
				}
			}
		}
		return result;
	}
	
}
