import java.awt.FlowLayout;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;


public class CadreDroite extends JPanel {

BoxLayout layout;
JPanel boutonFixe;
JPanel boutonSource;
int nbrSources;

static JButton plus,stop;
JButton TEST;

    public CadreDroite (LinkedList<Source> liste) {
      
      //layout = new BoxLayout(this,BoxLayout.PAGE_AXIS);
      //this.setLayout(layout);
      
      plus = new JButton("+");
      //plus.addActionListener();
      stop = new JButton("Pause");
      
      boutonFixe = new JPanel(new FlowLayout());
      this.add(boutonFixe);
      System.out.println(boutonFixe.getSize());
      
      boutonFixe.add(plus);
      boutonFixe.add(stop);
      
      nbrSources = liste.size();
      boutonSource = new JPanel(new GridLayout(nbrSources,1));
      boutonSource.setBounds(0, 100, 500, 100);
      this.add(boutonSource);
      
      TEST = new JButton("TEST");
      boutonSource.add(TEST);
    }

}
