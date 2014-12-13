


public class convertToLatLong {

//	public static void writefile (String positionID, HashMap<String, Double> prvector) throws Exception {
//		Double probability =  prvector.get(positionID);
//		BufferedWriter fout = new BufferedWriter(new FileWriter(positionID));
//		fout.write("cellNumber =" +  positionID +", " + "probability =  " +  probability.toString());
//		fout.close();
//	}
//	
	public static double converterToLatitude(double latitudeNo, double maxLatitude, double minLatitude, int latitudeGridNum){
		
		double latitude = latitudeNo*(maxLatitude - minLatitude)/latitudeGridNum + minLatitude;
		return latitude;
	}
	
   public static double converterToLongtitude(double longitudeNo, double maxLongitude, double minLongitude, int longitudeGridNum){
		
	   double longitude = longitudeNo*(maxLongitude - minLongitude)/longitudeGridNum + minLongitude;
	   return longitude;
	}
   public static int cropCellNumber(int number, int minLimit, int maxLimit){
	   
	int result = Integer.parseInt(Integer.toString(number).substring(minLimit, maxLimit));
	return result;	   
   }
   
}
