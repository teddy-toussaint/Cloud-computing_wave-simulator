

// Import required java libraries
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.google.gson.*;
import java.util.LinkedList;

// Extend HttpServlet class
public class EssaiServlet extends HttpServlet {
    
  private String message;

  public void init() throws ServletException
  {
      // Do required initialization
  }
 
  public void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, java.io.IOException
  {
      // Set response content type
      response.setContentType("application/json");
      
      
      
      Gson gson = new Gson();
      
      String body = getBody(request);
      
      PrintWriter out = response.getWriter();
      //System.out.println(body);
      JsonExport fromHTTP = gson.fromJson(body, JsonExport.class);
      
      JsonArray export = new JsonArray(fromHTTP.width,fromHTTP.height);
      
      for(int i=0; i<fromHTTP.width;i++) {
            for(int j=0; j<fromHTTP.height;j++) {
                export.ecran[i][j]=calcAmp(i, j, fromHTTP.sourceList, fromHTTP.time);
            }
      }  
      
      //System.out.println(fromHTTP);
      
      //System.out.println(result);
      
      //Result sent in JSON
      out.print(gson.toJson(export));
      //Result sent in raw format
      //out.print();
      
  }
  
  public void destroy()
  {
      // do nothing.
  }
  
    public static String getBody(HttpServletRequest request) throws IOException {

        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }

        body = stringBuilder.toString();
        return body;
    }
    
    public double calcAmp (int x, int y, LinkedList<Source> a, int time) {
        double Amp=0;
        for (int i=0;i<a.size();i++){
            double Ampbis;
            double distance;
            distance = Math.sqrt(Math.pow(x-a.get(i).getX(),2)+Math.pow(y-a.get(i).getY(), 2));
            Ampbis = a.get(i).Amplitude*Math.cos((a.get(i).coeffTemps*time)-(a.get(i).coeffEspace*distance));
            Amp=Amp+Ampbis;
        }
        return Amp;
    }
}