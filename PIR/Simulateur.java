//import com.sun.image.codec.jpeg.JPEGCodec;

//import com.sun.image.codec.jpeg.JPEGImageEncoder;


import javax.imageio.ImageIO;


import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.image.BufferedImage;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.File;

import java.util.LinkedList;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.*;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

//import sun.awt.image.codec.JPEGImageEncoderImpl;


public class Simulateur extends JFrame implements ActionListener {

    LinkedList<Source> liste;
    LinkedList<SourceParam> grille;
    Timer timer;
    int time = 0;
    JPanel cadre;
    CadreGauche canvas;
    JPanel cadreDroite;
    JPanel boutonFixe;
    JPanel boutonSource;
    GridLayout layout;
    JButton plus,stop,raffraichir;
    int hauteur,largeur,resolution;
    boolean stopped, magie;
    
    public Simulateur (int hauteur, int largeur, int resolution) {
        this.hauteur = hauteur;
        this.largeur = largeur;
        this.resolution = resolution;
        
        grille = new LinkedList<SourceParam>();
        liste = new LinkedList<Source>();
        
        layout = new GridLayout(1,2);
        cadre = new JPanel(layout);
        
        canvas = new CadreGauche(hauteur/resolution,largeur/resolution,resolution,liste);
        
        cadreDroite = new JPanel(new GridLayout(2,1));
        
        //menu 
        JMenuBar jmb = new JMenuBar();

        JMenu jmenu = new JMenu("Menu");
        JMenuItem jmnew = new JMenuItem("Nouveau");
        JMenuItem jmisave = new JMenuItem("Sauvegarder");
        JMenuItem jmiExit = new JMenuItem("Quitter");
        jmenu.add(jmnew);
        jmenu.add(jmisave);
        jmenu.add(jmiExit);
        jmb.add(jmenu);

        JMenu jopt = new JMenu("Option");
        JMenuItem reso = new JMenuItem("Resolution");
        JMenuItem vite = new JMenuItem("Vitesse de raffraichissement");
        jopt.add(reso);
        jopt.add(vite);
        jmb.add(jopt);

        JMenuItem Aide = new JMenuItem("Aide");
        jmb.add(Aide);
        
        JMenuItem Credit = new JMenuItem("Credit");
        jmb.add(Credit);
        
        jmnew.addActionListener(this);
        jmisave.addActionListener(this);
        jmiExit.addActionListener(this);
        reso.addActionListener(this);
        vite.addActionListener(this);
        Credit.addActionListener(this);
        Aide.addActionListener(this);
            
        // fin de menu

        cadre.add(canvas);
        cadre.add(cadreDroite);
        
        boutonFixe = new JPanel(new FlowLayout());
        boutonFixe.setBorder(new javax.swing.border.BevelBorder(BevelBorder.RAISED));
        plus = new JButton("+");
        plus.addActionListener(new GestionBouton());
        stop = new JButton("Pause");
        stop.addActionListener(new GestionBouton());
        raffraichir = new JButton("Raffraichir");
        raffraichir.addActionListener(new GestionBouton());
        boutonFixe.add(plus);
        boutonFixe.add(stop);
        boutonFixe.add(raffraichir);
        cadreDroite.add(boutonFixe);
        
        boutonSource = new JPanel(new GridLayout(liste.size(),1));
        cadreDroite.add(boutonSource);
        
        stopped = false;
        magie=false;
        
        timer = new Timer(40, new TimerAction());
        timer.start();
        this.setJMenuBar(jmb);
        this.setContentPane(cadre);
        this.pack();
        this.setSize((2*largeur),hauteur+61);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setTitle("Simulateur d'Ondes");
        

    }
    
    public void actionPerformed(ActionEvent ae) {
            String S = ae.getActionCommand();
              switch (S){
                case "Nouveau":
                  this.dispose();
                  Simulateur echelle = new Simulateur(100, 100, 10);
                  break;

                case "Sauvegarder":
                  save(canvas);
                break;
              
                case  "Quitter":
                System.exit(0);
                break;
              
                case  "Credit":
                  JOptionPane.showMessageDialog(null, "    ASINSA Gr.97 \n    Paul Choisel \n    Yohan Douest \n    Ho Duc Duy","CREDITS", JOptionPane.PLAIN_MESSAGE);   
                break;
              
                  case  "Aide":
                    JOptionPane.showMessageDialog(null, "   lighe 1 \n    lighe 2 \n    ligne3","Aide", JOptionPane.PLAIN_MESSAGE);   
                  break;
              
                case "Resolution":
                  JOptionPane jop = new JOptionPane();
                  String nouvellereso = jop.showInputDialog(null, "Entrez la nouvelle resolution", "Modifier la resolution", JOptionPane.QUESTION_MESSAGE);
                  resolution = Integer.parseInt(nouvellereso);
                  cadre.remove(canvas);
                  cadre.remove(cadreDroite);
                  canvas = new CadreGauche(hauteur/resolution,largeur/resolution,resolution,liste);
                  cadre.add(canvas);
                  cadre.add(cadreDroite);
                  raffraichissementmagique();
                break;
                
                case "Vitesse de raffraichissement":
                  JOptionPane jop2 = new JOptionPane();
                  String nouvellevite = jop2.showInputDialog(null, "Entrez la nouvelle vitesse en millisecondes", "Modifier la vitesse", JOptionPane.QUESTION_MESSAGE);
                  timer.setDelay(Integer.parseInt(nouvellevite));
                break;
              
                default:

                  System.out.println(S);

              }
        }
    
    public static void main(String[] args) {
        Simulateur echelle = new Simulateur(500, 500, 5);
    }

    public void paint(Graphics g) {
        canvas.repaint();
        cadreDroite.repaint();
    }

    public void action(int time) {
        canvas.raffraichircadreGauche(liste, time);
        repaint();
    }
    
    public void addSource () {
        liste.add(new Source((hauteur/(2*resolution)),(largeur/(2*resolution)),100));
        grille.add(new SourceParam(liste.size(),liste.get(liste.size()-1))); 
        ((GridLayout) (boutonSource.getLayout())).setRows(liste.size());
        grille.get(liste.size()-1).setBorder(new javax.swing.border.BevelBorder(BevelBorder.RAISED));
        boutonSource.add(grille.get(liste.size()-1));
        raffraichissementmagique();
        checkSource();
    }

    public void checkSource() {
        double ampTotale=0;
        double diviseur;
        for (int i=0;i<liste.size();i++) {
            ampTotale= ampTotale + liste.get(i).Amplitude;
        }
        diviseur = ampTotale/100;
        for (int i=0;i<liste.size();i++) {
            liste.get(i).Amplitude = (liste.get(i).Amplitude/diviseur);
        }   
    }
    
    public void raffraichirParam (int i) {
        liste.get(i).setX(Integer.parseInt(grille.get(i).x.getText()));
        liste.get(i).setY(Integer.parseInt(grille.get(i).y.getText()));
        liste.get(i).coeffTemps = (Double.parseDouble(grille.get(i).coeffTemps.getText()));
        liste.get(i).coeffEspace = (Double.parseDouble(grille.get(i).coeffEspace.getText()));
        liste.get(i).Amplitude = (Double.parseDouble(grille.get(i).amp.getText()));
        checkSource();
    }
    
    public void raffraichissementmagique () {
        if (magie) {
            raffraichir.setText("Raffraichir");
            magie=false;
        }
        else {
            raffraichir.setText("Raffraichir ");
            magie=true;
        }
    }

    public class TimerAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            action(time);
            time++;
        }
    }
    
    public class GestionBouton implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource()==plus){
                addSource();
            }
            else if (e.getSource()==raffraichir){
                for (int i=0;i<liste.size();i++) {
                    raffraichirParam(i);
                }
            }
            else if (e.getSource()==stop){
                if (stopped){
                    timer.start();
                    stop.setText("Pause");
                    stopped = false;
                }
                else {
                    timer.stop();
                    stop.setText("Reprendre");
                    stopped = true;
                }
            }
        }
    }
    
    public void save(Component myComponent) {
               Dimension size = myComponent.getSize();
               BufferedImage myImage = 
                 new BufferedImage(size.width, size.height,
                 BufferedImage.TYPE_INT_RGB);
               Graphics2D g2 = myImage.createGraphics();
               myComponent.paint(g2);
               try {
            	 ImageIO.write(myImage, "JPEG", new File("snap.jpg"));
               } catch (Exception e) {
                 System.out.println(e); 
               }
   }

}
