
import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;

public class MainClassReadFile {

	public static void test1 () throws Exception {
		String path = "C:/uber_gps_tsv/";
		String filename = "all.tsv";
		int timeinterval = 60;
		String pathandfile = path + filename;
		ArrayList<TimePositionReadFile> data = ReadFile.readfile(pathandfile, timeinterval);
		for (int i = 0; i < data.size(); i++) {
			data.get(i).print_out();
		}
	}

	public static void RegroupData () throws Exception {
		String path = "C:/uber_gps_tsv/";
		String filename = "all.tsv";
		int timeinterval = 60;
		String pathandfile = path + filename;
		ArrayList<TimePositionReadFile> data = ReadFile.readfile(pathandfile, timeinterval);
		HashMap<Integer, ArrayList<TimePositionReadFile>> dataByCar = Distribution.carIDtoData(data, timeinterval);
		for (int i : dataByCar.keySet()) {
			StringBuffer sb = new StringBuffer();
			ArrayList<TimePositionReadFile> oneCarData = dataByCar.get(i);
			for (int j = 0; j < oneCarData.size(); j++) {
				oneCarData.get(j).to_file(sb);
			}
			String resultfile =  Integer.toString(i);
			int zero = 4 - resultfile.length();
			for (int k = 0; k < zero; k++) resultfile = "0" + resultfile;
			resultfile = resultfile + ".txt";
			WriteFile.writefile(path + "dataByCar/" + resultfile, sb);
		}
	}
	
	public static void fromShanghai (
			String root,
			String inputdirectory,
			String mediandirectory
			) throws Exception {
		int timeinterval = 180;
		File[] inputfiles = new File(root + inputdirectory).listFiles();
		StringBuffer sbcorresponding = new StringBuffer();
//		double MaxLatitude = 31.220800;
//		double MinLatitude = 31.220800;
//		double MaxLongitude = 121.466600;
//		double MinLongitude = 121.466600;
		for (int i = 0; i < inputfiles.length; i++) {
			String inputfilename = inputfiles[i].getName();
			String outputfilename = inputfilename.substring(1, inputfilename.length() - 4);
			int zero = 5 - outputfilename.length();
			for (int j = 0; j < zero; j++) outputfilename = "0" + outputfilename;
			outputfilename = outputfilename + ".txt";
			sbcorresponding.append(outputfilename + " " + inputfilename + "\r\n");
			StringBuffer sb = new StringBuffer();
			BufferedReader br = new BufferedReader(new FileReader(root + inputdirectory + inputfilename));
			String oneline = br.readLine();
			oneline = br.readLine();
			int lasttime = -1;
			while (oneline != null && oneline.length() > 0) {
				int index = 0;
				while (oneline.charAt(index) != ',') index++;
				index++;
				String date = oneline.substring(index, index + 10);
				String clock = oneline.substring(index + 11, index + 19);
				double Longitude = Double.parseDouble(oneline.substring(index + 20, index + 30));
				double Latitude = Double.parseDouble(oneline.substring(index + 31, index + 40));
				int hour = Integer.parseInt(clock.substring(0, 2));
				int minute = Integer.parseInt(clock.substring(3, 5));
				int second = Integer.parseInt(clock.substring(6, 8));
				int time = (hour * 3600 + minute * 60 + second) / timeinterval;
				if (lasttime == -1) {
					lasttime = time;
				}
				else if (lasttime == time) {
					oneline = br.readLine();
					continue;
				}
				lasttime = time;
				TimePositionReadFile onedata = new TimePositionReadFile(date, time, Latitude, Longitude);
//				if (Latitude > MaxLatitude) MaxLatitude = Latitude;
//				if (Latitude < MinLatitude) MinLatitude = Latitude;
//				if (Longitude > MaxLongitude) MaxLongitude = Longitude;
//				if (Longitude < MinLongitude) MinLongitude = Longitude;
				onedata.to_file(sb);
				oneline = br.readLine();
			}
			br.close();
			WriteFile.writefile(root + mediandirectory + outputfilename, sb);
		}
//		sbcorresponding.append("MaxLatitude = " + MaxLatitude + "\r\n");
//		sbcorresponding.append("MinLatitude = " + MinLatitude + "\r\n");
//		sbcorresponding.append("MaxLongitude = " + MaxLongitude + "\r\n");
//		sbcorresponding.append("MinLongitude = " + MinLongitude + "\r\n");
		WriteFile.writefile(root + mediandirectory + "corresponding.txt", sbcorresponding);
	}
	
	public static void toPositionID (
			double GridLength1, 
			double GridLength2,
			String root,
			String mediandirectory,
			String outputdirectory
			) throws Exception {
		double MaxLatitude = 32.0;
		double MinLatitude = 30.0;
		double MaxLongitude = 122.0;
		double MinLongitude = 120.0;
		double LatitudeGrid = 0.0090 * GridLength1;
		double LongitudeGrid = 0.0104 * GridLength2;
		int LatitudeGridNum = (int) ((MaxLatitude - MinLatitude) / LatitudeGrid);
		int LongitudeGridNum = (int) ((MaxLongitude - MinLongitude) / LongitudeGrid);
		System.out.println("Latitude Grid Num = " + LatitudeGridNum);
		System.out.println("Longitude Grid Num = " + LongitudeGridNum);
		
		File[] files = new File(root + mediandirectory).listFiles();
		String[] filenames = new String[files.length];
		for (int i = 0; i < files.length; i++) filenames[i] = files[i].getName();
		for (int i = 0; i < filenames.length; i++) {
			if (filenames[i].equals("corresponding.txt")) continue;
			StringBuffer sb = new StringBuffer();
			BufferedReader br = new BufferedReader(new FileReader(root + mediandirectory + filenames[i]));
			String oneline = br.readLine();
			while (oneline != null && oneline.length() > 0) {
				StringTokenizer st = new StringTokenizer(oneline);
				String s1 = st.nextToken();
				String s2 = st.nextToken();
				double Latitude = Double.parseDouble(st.nextToken());
				double Longitude = Double.parseDouble(st.nextToken());
				String positionID = TimePositionReadFile.to_positionID(Latitude, Longitude, MaxLatitude, MinLatitude, LatitudeGrid, MaxLongitude, MinLongitude, LongitudeGrid);
				sb.append(s1 + " " + s2 + " " + positionID + "\r\n");
				oneline = br.readLine();
			}
			WriteFile.writefile(root + outputdirectory + filenames[i], sb);
			br.close();
		}
		StringBuffer sb = new StringBuffer();
		sb.append("LatitudeGridNum " + LatitudeGridNum + "\r\n");
		sb.append("LongitudeGridNum " + LongitudeGridNum + "\r\n");
		WriteFile.writefile(root + outputdirectory + "GridNum.txt", sb);
	}
	
	public static void builddirectory (String path) throws Exception {
		File directory = new File(path);
		if (!directory.exists()) {
			directory.mkdir();
		}
	}
	
	public static void oneCar (
			int carID,
			String root,
			String mediandirectory,
			int timeinterval
			) throws Exception {
		StringBuffer sb = new StringBuffer();
		String carFilename = "t" + Integer.toString(carID) + ".ogd";
		File[] files = new File(root).listFiles();
		for (int i = 0; i < files.length; i++) {
			String inputdirectory = files[i].getName();
			if (inputdirectory.length() != 6) continue;
			inputdirectory = inputdirectory + "/";
			String pathandfilename = root + inputdirectory + carFilename;
			
			System.out.println(pathandfilename);
			
			File thisfile = new File(pathandfilename);
			if (!thisfile.exists()) continue;
			
//			System.out.println(pathandfilename);
			
			BufferedReader br = new BufferedReader(new FileReader(pathandfilename));
			String oneline = br.readLine();
			oneline = br.readLine();
			int lasttime = -1;
			while (oneline != null && oneline.length() > 0) {
				int index = 0;
				while (oneline.charAt(index) != ',') index++;
				index++;
				String date = oneline.substring(index, index + 10);
				String clock = oneline.substring(index + 11, index + 19);
				double Longitude = Double.parseDouble(oneline.substring(index + 20, index + 30));
				double Latitude = Double.parseDouble(oneline.substring(index + 31, index + 40));
				int hour = Integer.parseInt(clock.substring(0, 2));
				int minute = Integer.parseInt(clock.substring(3, 5));
				int second = Integer.parseInt(clock.substring(6, 8));
				int time = (hour * 3600 + minute * 60 + second) / timeinterval;
				if (lasttime == -1) {
					lasttime = time;
				}
				else if (lasttime == time) {
					oneline = br.readLine();
					continue;
				}
				lasttime = time;
				TimePositionReadFile onedata = new TimePositionReadFile(date, time, Latitude, Longitude);
				onedata.to_file(sb);
				oneline = br.readLine();
			}
			br.close();
			
		}
		String outputfilename = Integer.toString(carID);
		int zero = 5 - outputfilename.length();
		for (int j = 0; j < zero; j++) outputfilename = "0" + outputfilename;
		outputfilename = outputfilename + ".txt";
		WriteFile.writefile(root + mediandirectory + outputfilename, sb);
	}
	
	public static void fromShanghai02072014 (
			HashSet<Integer> carIDSet,
			String root,
			String mediandirectory,
			int timeinterval
			) throws Exception {
		for (int carID : carIDSet) {
			MainClassReadFile.oneCar(carID, root, mediandirectory, timeinterval);
		}
	}
	
	public static void buildcarIDSet (
			String root,
			HashSet<Integer> carIDSet,
			int carNum
			) {
		HashSet<Integer> checked = new HashSet<Integer>();
		File[] dirctorys = new File(root).listFiles();
		String path = root + dirctorys[0].getName() + "/";
		File[] files = new File(path).listFiles();
		for (int i = 0; i < carNum; i++) {
			int randnum = (int) (Math.random() * files.length);
			while (checked.contains(randnum)) randnum = (int) (Math.random() * files.length);
			checked.add(randnum);
			String filename = files[randnum].getName();
			carIDSet.add(Integer.parseInt(filename.substring(1, filename.length() - 4)));
		}
	}
	
	public static void buildAllcarIDSet(
			String root,
			HashSet<Integer> carIDSet
			) {
		File[] dirctorys = new File(root).listFiles();
		String dirctoryName = new String();
		for (int i = 0; i < dirctorys.length; i++) {
			if (dirctorys[i].getName().length() == 6 && dirctorys[i].getName().charAt(0) >= '0' && dirctorys[i].getName().charAt(0) <= '9') {
				dirctoryName = dirctorys[i].getName();
				break;
			}
		}
		String path = root + dirctoryName + "/";
		File[] files = new File(path).listFiles();
		for (int i = 0; i < files.length; i++) {
			String filename = files[i].getName();
			carIDSet.add(Integer.parseInt(filename.substring(1, filename.length() - 4)));
		}
	}
	
	public static double [][] pandaalgorithm() throws Exception
	{
		double GridLength1 = 0.3 * Math.sqrt(2.0);
		double GridLength2 = 0.3 * Math.sqrt(2.0);
		int timeinterval = 60;
		HashSet<Integer> carIDSet = new HashSet<Integer>();
//		int carNum = 400;
//		String root = "C:/Users/adfa/Desktop/data";
		String root = "/home/aftab/Desktop/shanghai_taxi/";
//		MainClass.buildcarIDSet(root, carIDSet, carNum);
		MainClassReadFile.buildAllcarIDSet(root, carIDSet);
		String mediandirectory = "dataByCar/";
		String outputdirectory = "positionIDbyCar/";
		MainClassReadFile.builddirectory(root + mediandirectory);
		MainClassReadFile.builddirectory(root + outputdirectory);
		MainClassReadFile.fromShanghai02072014(carIDSet, root, mediandirectory, timeinterval);
		MainClassReadFile.toPositionID(GridLength1, GridLength2, root, mediandirectory, outputdirectory);
		double [][] result = new double [3][3];
		result = PrintOutVector.printOutVector("2007-02-01", "100", 3, 1.0, 3);
		System.out.print("came inside pandaalgorithm function");
		return result;
	}
	
	public static void main (String[] args) throws Exception {
		/*double GridLength1 = 0.3 * Math.sqrt(2.0);
		double GridLength2 = 0.3 * Math.sqrt(2.0);
		int timeinterval = 60;
		HashSet<Integer> carIDSet = new HashSet<Integer>();
//		int carNum = 400;
//		String root = "C:/Users/adfa/Desktop/data";
		String root = "/home/aftab/Desktop/shanghai_taxi/";
//		MainClass.buildcarIDSet(root, carIDSet, carNum);
		MainClassReadFile.buildAllcarIDSet(root, carIDSet);
		String mediandirectory = "dataByCar/";
		String outputdirectory = "positionIDbyCar/";
		MainClassReadFile.builddirectory(root + mediandirectory);
		MainClassReadFile.builddirectory(root + outputdirectory);
		MainClassReadFile.fromShanghai02072014(carIDSet, root, mediandirectory, timeinterval);
		MainClassReadFile.toPositionID(GridLength1, GridLength2, root, mediandirectory, outputdirectory);
		double [][] result = new double [3][3];
		result = PrintOutVector.printOutVector("2007-02-01", "100", 3, 1.0, 3);*/
	}
	
}
