

import java.util.LinkedList;

public class EspaceElem {

    int x,y;
    double Amp;
    
    
    public EspaceElem (int ax, int ay) {
        x=ax;
        y=ay;
    }

    public double calcAmp (LinkedList<Source> a, int time) {
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