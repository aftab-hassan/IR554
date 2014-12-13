
import java.io.BufferedWriter;
import java.io.FileWriter;

public class WriteFile {

	public static void writefile (String pathandfile, StringBuffer sb) throws Exception {
		BufferedWriter fout = new BufferedWriter(new FileWriter(pathandfile));
		fout.write(sb.toString());
		fout.close();
	}
	
}
