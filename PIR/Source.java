import com.google.gson.*;

public class Source {

    private int x,y;
    public double Amplitude;
    public double coeffTemps,coeffEspace;

    public Source (int ax, int ay, double aAmplitude) {
        coeffTemps=0.2;
        coeffEspace=0.2;
        x=ax;
        y=ay;
        Amplitude=aAmplitude;
    }
    
    public String jsonify() {
    	
        Gson gson = new Gson();
        return gson.toJson(this);
    	
    }

    public void setX (int X) {x=X;}
    
    public void setY (int Y) {y=Y;}
    
    public int getX () {return x;}
    
    public int getY () {return y;}

}
