

import java.awt.Color;
import java.awt.image.BufferedImage;

import java.util.LinkedList;

public class Espace {

    EspaceElem ecran [][];
    BufferedImage graphismes;
    int resolution;

    
    public Espace (int hauteur, int largeur, int aresolution) {
        resolution = aresolution;
        ecran = new EspaceElem [largeur][hauteur];
        for(int i=0; i<largeur;i++) {
            for(int j=0; j<hauteur;j++) {
                ecran[i][j] = new EspaceElem (i,j);
            }
        }
        graphismes = new BufferedImage(resolution*hauteur,resolution*largeur,BufferedImage.TYPE_INT_RGB);
    }

    public void raffraichirbuffer () {
        for(int i=0; i<ecran.length;i++) {
            for(int j=0; j<ecran[0].length;j++) {
                if (ecran[i][j].Amp>0) {
                    int red = (int) ((ecran[i][j].Amp / 100)* 255);
                    int blue = (int) (((-(ecran[i][j].Amp / 100)) + 1) * 255);
                    Color couleur = new Color(red,0,blue);
                    for (int k=0; k<resolution;k++){
                        for (int l=0;l<resolution;l++){
                            graphismes.setRGB((resolution*j)+k,(resolution*i)+l,couleur.getRGB());
                        }
                       
                    }
                    
                }
               
                else {
                    int blue = (int) ((((ecran[i][j].Amp / 100))+1)* 255);
                    int green = (int) ((-(ecran[i][j].Amp / 100)) * 255);
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
        for(int i=0; i<ecran.length;i++) {
            for(int j=0; j<ecran[0].length;j++) {
                ecran[i][j].Amp=ecran[i][j].calcAmp(a,time);
            }
        }       
    }


}
