/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import Joueurs.Humain;
import Joueurs.Ordinateur;
import static Modele.Arbitre.J1;
import static Modele.Arbitre.J2;
import static Modele.Arbitre.JvIA;
import static Modele.Arbitre.JvJ;
import java.util.Properties;
import ruche.Reglage;

/**
 *
 * @author grandmax
 */
public class SimulationIA extends Arbitre {

    public SimulationIA(Properties p) {
        super(p);
    }
    
    @Override
    public void init(){
        
        int[] tabPieces = new int[8];
        tabPieces[0]=(int)Reglage.lis("nbReine");
        tabPieces[1]=(int)Reglage.lis("nbScarabee");
        tabPieces[2]=(int)Reglage.lis("nbSauterelle");
        tabPieces[3]=(int)Reglage.lis("nbFourmi");
        tabPieces[4]=(int)Reglage.lis("nbAraignee");
        tabPieces[5]=(int)Reglage.lis("nbCoccinelle");
        tabPieces[6]=(int)Reglage.lis("nbMoustique");  
        tabPieces[7]=(int)Reglage.lis("nbCloporte");
        
        joueurs[J1] = new Ordinateur(true,Ordinateur.FACILE_ALEATOIRE, prop, tabPieces, (int)Reglage.lis("nbPiece"));
        joueurs[J2] = new Ordinateur(true,Ordinateur.FACILE_ALEATOIRE, prop, tabPieces, (int)Reglage.lis("nbPiece"));
        
        go();
    }
    
    void go(){
        if(joueurs[J1] instanceof Ordinateur){
            Ordinateur o = (Ordinateur) joueurs[J1];
            joue(o.coup(this));
        }
    }
    
    @Override
    public void prochainJoueur() {
        
        if(nbCoup[J1] <= 5 && nbCoup[J2]<=5){
            jCourant = ++jCourant % 2;

            if(plateau.estEncerclee(jCourant)){
                System.err.println(jCourant+" à perdu");
            }else{
                boolean b = true;
                for(int i=0; i<joueurs[jCourant].pions().length; i++)
                    b &= joueurs[jCourant].pions()[i]==0;
                if(plateau.aucunCoup(jCourant) && b){
                    prochainJoueur();
                }else{
                    if(joueurs[jCourant] instanceof Ordinateur){
                        Ordinateur o = (Ordinateur) joueurs[jCourant];
                        joue(o.coup(this));
                    }
                }
            }
        }
    }
}
