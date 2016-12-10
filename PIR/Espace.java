import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import com.google.gson.*;

import com.google.gson.Gson;

public class Espace {

    double ecran [][];
    int height,width;
    BufferedImage graphismes;
    int resolution;
    

    
    public Espace (int aheight, int awidth, int aresolution) {
        height=aheight;
        width=awidth;
    	resolution = aresolution;
        ecran = new double [width][height];
        for(int i=0; i<width;i++) {
            for(int j=0; j<height;j++) {
                ecran[i][j] = 0.0;
            }
        }
        graphismes = new BufferedImage(resolution*height,resolution*width,BufferedImage.TYPE_INT_RGB);
    }

    public void raffraichirbuffer () {
        for(int i=0; i<ecran.length;i++) {
            for(int j=0; j<ecran[0].length;j++) {
                if (ecran[i][j]>0) {
                    int red = (int) ((ecran[i][j] / 100)* 255);
                    int blue = (int) (((-(ecran[i][j] / 100)) + 1) * 255);
                    Color couleur = new Color(red,0,blue);
                    for (int k=0; k<resolution;k++){
                        for (int l=0;l<resolution;l++){
                            graphismes.setRGB((resolution*j)+k,(resolution*i)+l,couleur.getRGB());
                        }
                       
                    }
                    
                }
               
                else {
                    int blue = (int) ((((ecran[i][j] / 100))+1)* 255);
                    int green = (int) ((-(ecran[i][j] / 100)) * 255);
                    Color couleur = new Color(0,green,blue);
                    for (int k=0; k<resolution;k++){
                        for (int l=0;l<resolution;l++){
                            graphismes.setRGB((resolution*j)+k,(resolution*i)+l,couleur.getRGB());
                        }
                       
                    }
                }
            }
        }
    }

    public void raffraichir (LinkedList<Source> a, int time) {
    	JsonExport data=new JsonExport(width,height,time,a);
    	Gson gson = new Gson();
        String json=gson.toJson(data);
        System.out.println(json);
        double t[][]= new double [width][height];
        t=postHTTP(json);
           	
        for(int i=0; i<ecran.length;i++) {
            for(int j=0; j<ecran[0].length;j++) {
                ecran[i][j]=t[i][j];
            }
        }       
    }
    
public double[][] postHTTP(String data) {
        
		double rep[][]=new double [width][height];
    
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
            
            Gson gson = new Gson();
            JsonArray jsonArray = gson.fromJson(sb.toString(),JsonArray.class);
            
            System.out.println("Buffer : "+sb.toString());
            
            for(int i=0; i<width; i++) {
            	for(int j=0; j<height; j++) {
            		rep[i][j] = jsonArray.ecran[i][j];
            	}
            }
            
            reader.close();
            connection.disconnect();
            
            /**StringBuffer buffer = new StringBuffer();
            
            //URLConnection yc = url.openConnection();

            BufferedReader in = new BufferedReader(new InputStreamReader(
                                        connection.getInputStream()));
            String inputLine = null;
            
            while ( (inputLine = in.readLine()) != null) {
            	//lecture de la valeur renvoyée
            	buffer.append(new String(inputLine.getBytes(),"GBK"));
            }
            
            Gson gson = new Gson();
            JsonArray jsonArray = gson.fromJson(buffer.toString(),JsonArray.class);
            
            System.out.println("Buffer : "+buffer.toString());
            
            for(int i=0; i<width; i++) {
            	for(int j=0; j<height; j++) {
            		rep[i][j] = jsonArray.ecran[i][j];
            	}
            }
            
            in.close();
            reader.close();
            connection.disconnect();*/
            
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return rep;
        
    }

/**
    public double[][] getHTTP(){
        StringBuffer buffer = new StringBuffer();
        double rep[][]=new double [width][height];
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
            
            Gson json = new Gson();
            String b = json.toJson(buffer);
            rep=(double[][]) json.fromJson(b,double[][].class);
            
            in.close();
            
        } catch (Exception e)  {
            e.printStackTrace();
        }
        return rep;
    }
*/

}
