import java.awt.Dimension;
import java.awt.Graphics;

import java.awt.Image;

//import java.io.File;

import java.util.LinkedList;

//import javax.imageio.ImageIO;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class CadreGauche extends JPanel {

    Espace canvas;
    LinkedList<Source> liste;
    Image IconeSource;
    int resolution;
    
    public CadreGauche (int hauteur, int largeur,int aresolution,LinkedList<Source> aliste) {
        liste = aliste;
        resolution = aresolution;
        setPreferredSize(new Dimension(hauteur, largeur));
        canvas = new Espace(largeur, hauteur, resolution); 
        try {
            IconeSource = new ImageIcon("IconeSource.ico").getImage();
         }
        catch(Exception err) {            System.out.println(err.toString());
            System.out.println("IconeSource.ico"+" introuvable !");
            System.out.println("Mettre les images dans le repertoire :"+getClass().getClassLoader().getResource("IconeSource.ico"));
            System.exit(0);
         }
    }

    public void raffraichircadreGauche(LinkedList<Source> liste, int time) {
        canvas.raffraichir(liste, time);
        canvas.raffraichirbuffer();
    }

    public void paintComponent (Graphics g) {
        g.drawImage(canvas.graphismes,0,0,this);
        for (int i=0;i<liste.size();i++){
            g.drawImage(IconeSource,100,100,this);
        }
    }
}
