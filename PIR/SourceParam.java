import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SourceParam extends JPanel{

    JTextField x,y,coeffTemps,coeffEspace,amp;
    JLabel index,tx,ty,w,k,tamp;
    
    public SourceParam (int aindex, Source source) {
        this.setLayout(new FlowLayout());
        
        index = new JLabel(Integer.toString(aindex)+ " : ");
        
        tx = new JLabel("X = ");
        x = new JTextField(Integer.toString(source.getX()));
        
        ty = new JLabel("Y = ");
        y = new JTextField(Integer.toString(source.getY()));
        
        w = new JLabel("w = ");
        coeffTemps = new JTextField(Double.toString(source.coeffTemps));
        
        k = new JLabel("k = ");
        coeffEspace = new JTextField(Double.toString(source.coeffEspace));
        
        tamp = new JLabel("Amp = ");
        amp = new JTextField(Double.toString(source.Amplitude));
        
        this.add(index);
        this.add(tx);
        this.add(x);
        this.add(ty);
        this.add(y);
        this.add(w);
        this.add(coeffTemps);
        this.add(k);
        this.add(coeffEspace);
        this.add(tamp);
        this.add(amp);
    }
}
