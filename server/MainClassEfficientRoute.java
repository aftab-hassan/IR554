
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class MainClassEfficientRoute {

	public static void test1 () throws Exception {
		String path = "/home/aftab/Desktop/shanghai_taxi/positionIDbyCar";
		String filename = "00105.txt";
		ArrayList<ArrayList<TimePosition>> History = new ArrayList<ArrayList<TimePosition>>(); 
		ArrayList<ArrayList<TimePosition>> noHistory = new ArrayList<ArrayList<TimePosition>>();
		String noHistoryDate = "2007-02-01";
		int noHistoryTimestart = 0;
		int noHistoryTimeend = 120;
		String pathandfile = path + filename;
		
		ReadFileEfficientRoute.divide(History, noHistory, noHistoryDate, noHistoryTimestart, noHistoryTimeend, pathandfile);
		
		System.out.println("History:");
		for (int i = 0; i < History.size(); i++) {
			for (int j = 0; j < History.get(i).size(); j++) {
				History.get(i).get(j).print_out();
			}
			System.out.println();
		}
		System.out.println("noHistory:");
		for (int i = 0; i < noHistory.size(); i++) {
			for (int j = 0; j < noHistory.get(i).size(); j++) {
				noHistory.get(i).get(j).print_out();
			}
			System.out.println();
		}
		
		int k = 3;
		double p = 0.9;
		
		Runtime runtime = Runtime.getRuntime();
		double sumaccuracy = 0.0;
		int count = 0;
		double begintime = System.currentTimeMillis();
		
		for (int i = 0; i < noHistory.size(); i++) {
			ArrayList<TimePosition> continuedata = noHistory.get(i);
			if (continuedata.size() > k) {
				for (int j = k; j < continuedata.size(); j++) {
					String realDestination = continuedata.get(j).get_positionID();
					ArrayList<HashMap<String, Double>> knownpr = new ArrayList<HashMap<String, Double>>();
					for (int l = j - k; l < j; l++) {
						String thisposition = continuedata.get(l).get_positionID();
						knownpr.add(TimePosition.fixedPosition(thisposition));
					}
					HashMap<String, Double> predictedvector = Calculation.predictedvector(knownpr, p, 55, 87);
					double thisaccuracy = 0.0;
					if (predictedvector.containsKey(realDestination)) thisaccuracy = predictedvector.get(realDestination);
					sumaccuracy = sumaccuracy + thisaccuracy;
					count++;
				}
			}
		}
		
		double avgaccuracy = sumaccuracy / count;
		double endtime = System.currentTimeMillis();
		double avgtime = (endtime - begintime) / count;
		double memory = runtime.totalMemory() - runtime.freeMemory();
		System.out.println("avgaccuracy = " + avgaccuracy);
		System.out.println("avgtime = " + avgtime);
		System.out.println("memory = " + memory);
		System.out.println("count = " + count);
	}

	public static void test2 () throws Exception {
		String path = "C:/uber_gps_tsv/positionIDbyCar/";
		String filename = "0000.txt";
		ArrayList<ArrayList<TimePosition>> History = new ArrayList<ArrayList<TimePosition>>(); 
		ArrayList<ArrayList<TimePosition>> noHistory = new ArrayList<ArrayList<TimePosition>>();
		String noHistoryDate = "2007-01-07";
		int noHistoryTimestart = 0;
		int noHistoryTimeend = 1440;
		String pathandfile = path + filename;
		
		ReadFileEfficientRoute.divide(History, noHistory, noHistoryDate, noHistoryTimestart, noHistoryTimeend, pathandfile);
		
		
		int k = 3;
		double p = 0.9;
		int n = 3;
		
		
		for (int i = 0; i < noHistory.size(); i++) {
			ArrayList<TimePosition> continuedata = noHistory.get(i);
			if (continuedata.size() > k) {
				for (int j = k; j < continuedata.size(); j++) {
					ArrayList<HashMap<String, Double>> knownpr = new ArrayList<HashMap<String, Double>>();
					for (int l = j - k; l < j; l++) {
						String thisposition = continuedata.get(l).get_positionID();
						knownpr.add(TimePosition.fixedPosition(thisposition));
					}
					ArrayList<HashMap<String, Double>> nprvectors = Calculation.nprvectors(knownpr, p, n, 55, 87);
					
					for (int l = 0; l < nprvectors.size(); l++) {
						System.out.println("time = " + l);
						HashMap<String, Double> vector = nprvectors.get(l);
						for (String positionID : vector.keySet()) {
							double pr = vector.get(positionID);
							System.out.println(positionID + " " + pr);
						}
					}
					
				}
			}
		}
		
	}
	
	public static void test_new (
			double GridLength1,
			double GridLength2,
			int k,
			double p,
			int adjacentNum
			) throws Exception {
		
		System.out.println("The grid is " + GridLength1 + " * " + GridLength2 + "   k = " + k + "   p = " + p);
		
		BufferedReader br = new BufferedReader(new FileReader("/home/aftab/Desktop/shanghai_taxi/positionIDbyCar/00105.txt"));
		String oneline = br.readLine();
		ArrayList<String> data = new ArrayList<String>();
		
		double MaxLatitude = 32.0;
		double MinLatitude = 30.0;
		double MaxLongitude = 122.0;
		double MinLongitude = 120.0;
		double LatitudeGrid = 0.0090 * GridLength1;
		double LongitudeGrid = 0.0104 * GridLength2;
		int LatitudeGridNum = (int) ((MaxLatitude - MinLatitude) / LatitudeGrid);
		int LongitudeGridNum = (int) ((MaxLongitude - MinLongitude) / LongitudeGrid);
		
//		while (oneline != null && oneline.length() > 0) {
//			StringTokenizer st = new StringTokenizer(oneline);
//			st.nextToken();
//			st.nextToken();
//			st.nextToken();
//			double Latitude = Double.parseDouble(st.nextToken());
//			double Longitude = Double.parseDouble(st.nextToken());
//			String positionID = readFile.TimePosition.to_positionID(Latitude, Longitude, MaxLatitude, MinLatitude, LatitudeGrid, MaxLongitude, MinLongitude, LongitudeGrid);
//			if (data.size() == 0 || !data.get(data.size() - 1).equals(positionID)) data.add(positionID);
//			oneline = br.readLine();
//		}
		
		while (oneline != null && oneline.length() > 0) {
			String[] s = oneline.split(" ");
			data.add(s[2]);
			oneline = br.readLine();
		}
		
		br.close();
		
//		for (int i = 0; i < data.size(); i++) {
//			System.out.println(data.get(i));
//		}
		
		double sumaccuracy = 0.0;
		int count = 0;
		double begintime = System.currentTimeMillis();
		Runtime runtime = Runtime.getRuntime();
		
		for (int i = k; i < data.size(); i++) {
			String realDestination = data.get(i);
			if (!ComplicatedAdjacent.adjacent_8(data.get(i - 1), LatitudeGridNum, LongitudeGridNum).contains(realDestination)) continue;
			ArrayList<String> trajectory = new ArrayList<String>();
			for (int j = i - k; j < i; j++) trajectory.add(data.get(j));
			HashMap<String, Double> prvector = ComplicatedCalculation.predictedvectorbytrajectory(trajectory, p, LatitudeGridNum, LongitudeGridNum, adjacentNum);
			double accuracy = 0.0;
			if (prvector.containsKey(realDestination)) accuracy = prvector.get(realDestination);
			sumaccuracy = sumaccuracy + accuracy;
			count++;
		}
		
		double avgaccuracy = sumaccuracy / count;
		double endtime = System.currentTimeMillis();
		double avgtime = (endtime - begintime) / count;
		double memory = runtime.totalMemory() - runtime.freeMemory();
		System.out.println("The average accuracy is " + avgaccuracy);
		System.out.println("The average time is " + avgtime);
		System.out.println("The used memory is " + memory);
		System.out.println("The number of data is " + count);

	}
	
	public static void RouteAlgorithm (
			HashMap<Integer, String> time_position,
			HashMap<Integer, ArrayList<HashMap<String, Double>>> time_nprvectors,
			String path,
			String carID,
			int k,
			int n,
			double p,
			String noHistoryDate,
			int noHistoryTimestart,
			int noHistoryTimeend
			) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(path + "GridNum.txt"));
		String oneline = br.readLine();
		StringTokenizer st = new StringTokenizer(oneline);
		st.nextToken();
		int LatitudeGridNum = Integer.parseInt(st.nextToken());
		oneline = br.readLine();
		st = new StringTokenizer(oneline);
		st.nextToken();
		int LongitudeGridNum = Integer.parseInt(st.nextToken());
		br.close();
		
		ArrayList<ArrayList<TimePosition>> History = new ArrayList<ArrayList<TimePosition>>(); 
		ArrayList<ArrayList<TimePosition>> noHistory = new ArrayList<ArrayList<TimePosition>>();
		String filename = carID + ".txt";
		String pathandfile = path + filename;
		
		ReadFileEfficientRoute.divide(History, noHistory, noHistoryDate, noHistoryTimestart, noHistoryTimeend, pathandfile);
		
		for (int i = 0; i < noHistory.size(); i++) {
			ArrayList<TimePosition> continuedata = noHistory.get(i);
			if (continuedata.size() > k) {
				for (int j = k; j < continuedata.size(); j++) {
					ArrayList<HashMap<String, Double>> knownpr = new ArrayList<HashMap<String, Double>>();
					for (int l = j - k; l < j; l++) {
						String thisposition = continuedata.get(l).get_positionID();
						knownpr.add(TimePosition.fixedPosition(thisposition));
					}
					ArrayList<HashMap<String, Double>> nprvectors = Calculation.nprvectors(knownpr, p, n, LatitudeGridNum, LongitudeGridNum);
					int time = continuedata.get(j - 1).get_time();
					String positionID = continuedata.get(j - 1).get_positionID();
					time_position.put(time, positionID);
					time_nprvectors.put(time, nprvectors);
				}
			}
		}
	}
	
	public static void egyptNewData () throws Exception {
		double[] GridLengthSet = {1.0};
		int[] kSet = {2, 3, 4};
		double[] pSet = {0.7, 0.8, 0.9, 1.0};
		int[] adjacentNumSet = {3, 4, 8};
		for (int i = 0; i < GridLengthSet.length; i++) {
			double GridLength1 = GridLengthSet[i];
			double GridLength2 = GridLengthSet[i];
			for (int j = 0; j < kSet.length; j++) {
				int k = kSet[j];
				for (int l = 0; l < pSet.length; l++) {
					double p = pSet[l];
					for (int m = 0; m < adjacentNumSet.length; m++) {
						int adjacentNum = adjacentNumSet[m];
						System.out.println("GridLength1 = " + GridLength1 +"  GridLength2 = " + GridLength2 + "  k = " + k + "  p = " + p + "  adjacentNum = " + adjacentNum);
						MainClassEfficientRoute.test_new(GridLength1, GridLength2, k, p, adjacentNum);
						System.out.println();
					}
				}
			}
		}
	}
	
	public static void main (String[] args) throws Exception {
//		MainClass.egyptNewData();
		
		double [][] result = PrintOutVector.printOutVector("2007-02-01", "100", 3, 1.0, 3);
		
//        for (int i=0; i < result.length; i++) {   
//            for (int j=0 ; j < result[i].length; j++) {   
////                for(int k=0; k< result[i][j].length; k++) {  
//                    System.out.println(result[i][j]);  
////                }   
//            }   
//        }
//	    String info[][][] = new String[3][4][5];  
//        for (int i=0; i < info.length; i++) {   
//            for (int j=0 ; j < info[i].length; j++) {   
//                for(int k=0; k< info[i][j].length; k++) {  
//                    info[i][j][k] = "String[" + i + "," + j + "," + k + "]";   
//                    System.out.println(info[i][j][k]);  
//                }   
//            }   
//        }
	}
}
