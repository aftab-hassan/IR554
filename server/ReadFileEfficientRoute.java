
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ReadFileEfficientRoute {

	public static ArrayList<TimePosition> readfile (String pathandfile) throws Exception {
		ArrayList<TimePosition> result = new ArrayList<TimePosition>();
		BufferedReader br = new BufferedReader(new FileReader(pathandfile));
		String oneline = br.readLine();
		while (oneline != null && oneline.length() > 0) {
			StringTokenizer st = new StringTokenizer(oneline);
			String date = st.nextToken();
			int time = Integer.parseInt(st.nextToken());
			String positionID = st.nextToken();
			TimePosition onedata = new TimePosition(date, time, positionID);
			result.add(onedata);
			oneline = br.readLine();
		}
		br.close();
		return result;
	}
	
	public static void divide (
			ArrayList<ArrayList<TimePosition>> History, 
			ArrayList<ArrayList<TimePosition>> noHistory,
			String noHistoryDate,
			int noHistoryTimestart,
			int noHistoryTimeend,
			String pathandfile
			) throws Exception {
		ArrayList<TimePosition> his = new ArrayList<TimePosition>();
		ArrayList<TimePosition> nohis = new ArrayList<TimePosition>();
		ArrayList<TimePosition> origin = ReadFileEfficientRoute.readfile(pathandfile);
		for (int i = 0; i < origin.size(); i++) {
			TimePosition onedata = origin.get(i);
			String date = onedata.get_date();
			int time = onedata.get_time();
			if (noHistoryDate.equals(date) && time >= noHistoryTimestart && time <= noHistoryTimeend) {
				nohis.add(onedata);
			}
			else {
				his.add(onedata);
			}
		}
		
		ArrayList<TimePosition> continuedata = new ArrayList<TimePosition>();
		if (!his.isEmpty()) {
			continuedata.add(his.get(0));
			String thisdate = his.get(0).get_date();
			int thistime = his.get(0).get_time();
			for (int i = 1; i < his.size(); i++) {
				TimePosition onedata = his.get(i);
				String date = onedata.get_date();
				int time = onedata.get_time();
				if (thisdate.equals(date) && time == thistime + 1) {
					thistime++;
					continuedata.add(onedata);
				}
				else {
					History.add(continuedata);
					thisdate = date;
					thistime = time;
					continuedata = new ArrayList<TimePosition>();
					continuedata.add(onedata);
				}
			}
			History.add(continuedata);			
		}
		
		continuedata = new ArrayList<TimePosition>();
		if (!nohis.isEmpty()) {
			continuedata.add(nohis.get(0));
			String thisdate = nohis.get(0).get_date();
			int thistime = nohis.get(0).get_time();
			for (int i = 1; i < nohis.size(); i++) {
				TimePosition onedata = nohis.get(i);
				String date = onedata.get_date();
				int time = onedata.get_time();
				if (thisdate.equals(date) && time == thistime + 1) {
					thistime++;
					continuedata.add(onedata);
				}
				else {
					noHistory.add(continuedata);
					thisdate = date;
					thistime = time;
					continuedata = new ArrayList<TimePosition>();
					continuedata.add(onedata);
				}
			}
			noHistory.add(continuedata);			
		}
	}
	
	public static ArrayList<ArrayList<TimePosition>> deleteRepeat (
			ArrayList<ArrayList<TimePosition>> origindata
			) {
		ArrayList<ArrayList<TimePosition>> result = new ArrayList<ArrayList<TimePosition>>();
		for (int i = 0; i < origindata.size(); i++) {
			ArrayList<TimePosition> continuedata = origindata.get(i);
			ArrayList<TimePosition> onelist = new ArrayList<TimePosition>();
			onelist.add(continuedata.get(0));
			for (int j = 1; j < continuedata.size(); j++) {
				if (!onelist.get(onelist.size() - 1).get_positionID().equals(continuedata.get(j).get_positionID())) {
					onelist.add(continuedata.get(j));
				}
			}
			result.add(onelist);
		}
		return result;
	}

}
