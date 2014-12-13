

import java.io.IOException;

import java.io.PrintWriter;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

//import sun.misc.IOUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONTokener;*/

//import readFile.MainClassReadFile;


/**
 * Servlet implementation class seconservlet
 */
@WebServlet("/seconservlet")
public class seconservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public seconservlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");

	      // Actual logic goes here.
		double [][] predictiveresult = new double [3][3];
		
		/* call readfile here */
		try {
			predictiveresult = MainClassReadFile.pandaalgorithm();
			System.out.print("came back to seconservlet");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JSONObject obj1 = new JSONObject();
	      JSONObject obj2 = new JSONObject();
	      JSONObject obj3 = new JSONObject();
	      JSONArray myjsonarray = new JSONArray();
		
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
      response.setHeader("Cache-Control", "nocache");
      response.setCharacterEncoding("utf-8");
	    //out.println("<h1>hellow</h1>");
	
		/* JSONObject code */
        /* obj1 */
	      try {
	    	  obj1.put("lat", "57.24");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      try {
	    	  obj1.put("long", "-127.24");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      try {
	    	  obj1.put("prob", "0.6");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
	      /* obj2 */
	      try {
	    	  obj2.put("lat", "56.71");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      try {
	    	  obj2.put("long", "-176.22");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      try {
	    	  obj2.put("prob", "0.3");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
	      /* obj3 */
	      try {
	    	  obj3.put("lat", "49.87");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      try {
	    	  obj3.put("long", "-145.23");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      try {
	    	  obj3.put("prob", "0.1");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
	      myjsonarray.put(obj1);
	      myjsonarray.put(obj2);
	      myjsonarray.put(obj3);
	      
	      out.print(myjsonarray);
	      
	      /* retrieving from the JSON array */
	      /* now trying to retrieve */
	      /*for (int i = 0; i < myjsonarray.length(); i++) {
	          try {
				JSONObject retobj = (JSONObject) myjsonarray.get(i);
			    String retlat = retobj.getString("lat");
			    String retlong = retobj.getString("long");
			    String retprob = retobj.getString("prob");
			    
			    out.print("retlat ==" + retlat + " " + "retlong=="+retlong + " " + "retprob==" + retprob);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	          //Access data with obj.get("item_name")
	      } */
	      
	      /* trying to access from URL : new JSONObject(IOUtils.toString(new URL("http://localhost:8080/secondproject/seconservlet"), Charset.forName("UTF-8")));*/
	      /*for (int i = 0; i < myjsonarray.length(); i++) {
	          try {
				//JSONObject retobj = new JSONObject(IOUtils.toString(new URL("http://localhost:8080/secondproject/seconservlet"), Charset.forName("UTF-8")));
	        	JSONObject retobj = (JSONObject) new JSONTokener(IOUtils.toString(new URL("http://localhost:8080/secondproject/seconservlet"))).nextValue();
			    String retlat = retobj.getString("lat");
			    String retlong = retobj.getString("long");
			    String retprob = retobj.getString("prob");
			    
			    out.print("retlat ==" + retlat + " " + "retlong=="+retlong + " " + "retprob==" + retprob);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	          //Access data with obj.get("item_name")
	      }*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
