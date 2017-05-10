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
        
        int[] tabPieces2 = new int[8];
        for(int i=0; i<tabPieces2.length; i++)
            tabPieces2[i]=tabPieces[i];
        
        joueurs[J1] = new Ordinateur(true,Ordinateur.FACILE_ALEATOIRE, prop, tabPieces, (int)Reglage.lis("nbPiece"), J1);
        joueurs[J2] = new Ordinateur(true,Ordinateur.FACILE_ALEATOIRE, prop, tabPieces2, (int)Reglage.lis("nbPiece"), J2);
        
        go();
    }
    
    void go(){
        if(joueurs[J1] instanceof Ordinateur){
            Ordinateur o = (Ordinateur) joueurs[J1];
            joue(o.coup(this, null));
        }
    }
    
    @Override
    public void prochainJoueur() {
        if(nbCoup[J1] <= 100 && nbCoup[J2]<= 100){
            jCourant = ++jCourant % 2;

            if(plateau.estEncerclee(jCourant)){
                System.err.println(jCourant+" à perdu");
            }else if(nul())
                System.err.println("Match null");
            else{
                boolean b = true;
            for(int i=0; i<joueurs[jCourant].pions().length; i++)
                b &= joueurs[jCourant].pions()[i]==0;
            
                deplacements = deplacementPossible(jCourant);
                aucun = deplacements==null || deplacements.length<=0;
                if(plateau.aucunCoup(jCourant) && b){
                    prochainJoueur();
                }else{
                    if(joueurs[jCourant] instanceof Ordinateur){
                        Ordinateur o = (Ordinateur) joueurs[jCourant];
                        joue(o.coup(this, deplacements));
                    }
                }
            }
        }
    }
    
    public void joue(Deplacement d){
                deplacePion(d);
                nbCoup[jCourant]++;
                refaire.clear();
                historique.add(d);
                System.err.println(d+" déplacement effectué");
                prochainJoueur();
         
    }
    public void joue(Depot d){
        if(nbCoup[jCourant]==0 && jCourant == J1){
            joueurs[jCourant].jouer(d.type());
            plateau.premierPion(FabriqueInsecte.creer(d.type(), jCourant, new Point(0,0)));
            nbCoup[jCourant]++;
            refaire.clear();
            historique.add(d);
            System.err.println("1- Dépot effectué "+d);
            prochainJoueur();
        }else if(nbCoup[jCourant]==0 && jCourant == J2){
            if(plateau.premierPionValide(d)){
            joueurs[jCourant].jouer(d.type());
                deposePion(d);
                nbCoup[jCourant]++;
                refaire.clear();
                historique.add(d);
                joueurs[jCourant].jouer(d.type());
                System.err.println("2- Dépot effectué "+d);
                prochainJoueur();
            }else{
                System.err.println("Depot impossible");
            }
        }else if(deposePionValide(d) && joueurs[jCourant].pion(d.type())>0){
            
            joueurs[jCourant].jouer(d.type());
            deposePion(d);
            nbCoup[jCourant]++;
            refaire.clear();
            historique.add(d);
            System.err.println("3- Dépot effectué "+d);
            prochainJoueur();
            
        }else{
            System.err.println("Depot impossible");
        }
    }
}
