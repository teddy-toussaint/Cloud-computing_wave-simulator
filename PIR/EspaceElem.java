import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import com.google.gson.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class EspaceElem {

    int x,y;
    double Amp;
    
    
    public EspaceElem (int ax, int ay) {
        x=ax;
        y=ay;
    }

    public double calcAmp (LinkedList<Source> a, int time) {
        //code sur le client, création du document JSON avec les sources, puis requete http POST, et attente du retour
    	double Amp=0;
    	JsonExport data=new JsonExport(x,y,time,a);
        Gson gson = new Gson();
        String json=gson.toJson(data);
        System.out.println(json);
        postHTTP(json);
        Amp=getHTTP();
    
        //code à exporter sur le serveur
        
        /*for (int i=0;i<a.size();i++){
            double coeff1,coeff2,Ampbis;
            coeff1=1;
            coeff2=0.2;
            double distance;
            distance = Math.sqrt(Math.pow(x-a.get(i).getX(),2)+Math.pow(y-a.get(i).getY(), 2));
            Ampbis = a.get(i).Amplitude*Math.cos((coeff1*time)-(coeff2*distance));
            Amp=Amp+Ampbis;
        }*/
        return Amp;
    }
    
    public static void postHTTP(String data) {
        
        try {
            URL url = new URL("http://192.168.43.20:8080/EssaiServlet");
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("connection", "Keep-Alive");
            //connection.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
            connection.connect();
            //request POST
            DataOutputStream out = new DataOutputStream(
                    connection.getOutputStream());

            out.writeBytes(data);
            out.flush();
            out.close();
            //read reaction
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String lines;
            StringBuffer sb = new StringBuffer("");
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
            System.out.println(sb);
            reader.close();
            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static double getHTTP(){
        StringBuffer buffer = new StringBuffer();
        double rep=0;
        try {
            URL url = new URL("http://192.168.43.20:8080/EssaiServlet");
            URLConnection yc = url.openConnection();

            BufferedReader in = new BufferedReader(new InputStreamReader(
                                        yc.getInputStream()));
            String inputLine = null;

            while ( (inputLine = in.readLine()) != null) {
            	//lecture de la valeur renvoyée
            	buffer.append(new String(inputLine.getBytes(),"GBK"));
            }
            rep=Double.parseDouble(buffer.toString());
            in.close();
            
        } catch (Exception e)  {
            e.printStackTrace();
        }
        System.out.println("Amp= "+rep);
        return rep;
    }

}
