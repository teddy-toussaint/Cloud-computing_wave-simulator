import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.util.concurrent.ConcurrentHashMap;

public class JAVAURL {

    /** * @param args */
    public static void main(String[] args) {
        String url = "http://192.168.1.1:8080/*.controller.do/*.action";
           System.out.println("URL��"+url);
           StringBuffer json = new StringBuffer();
            try {

                URL oracle = new URL(url);
               
                URLConnection yc = oracle.openConnection();

                BufferedReader in = new BufferedReader(new InputStreamReader(
                                            yc.getInputStream()));
                String inputLine = null;

                while ( (inputLine = in.readLine()) != null) {
                    json.append(new String(inputLine.getBytes(),"GBK"));
                }
           
                in.close();
            } catch (Exception e)  {
                e.printStackTrace();
            }
            
        /*try {
            JSONArray jn =  JSONArray.fromObject(json.toString());
            if(jn.size()>0){
            for (int i = 0; i < jn.size(); i++) {
                JSONObject jo = (JSONObject) jn.get(i);
                System.out.println(jo.get("id"));
                System.out.println(jo.get("fdName"));
            }
            System.out.println(jn);
            }
            System.out.println("data size "+jn.size());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("timeout ");
        }*/
    }

}
