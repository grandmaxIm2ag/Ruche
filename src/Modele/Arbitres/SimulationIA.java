/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele.Arbitres;

import Joueurs.Ordinateur;
import Modele.Coup;
import Modele.Deplacement;
import Modele.Depot;
import Modele.FabriqueInsecte;
import Modele.Point;
import Vue.Interface;
import Vue.PaneToken;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import ruche.Reglage;

/**
 *
 * @author grandmax
 */
public class SimulationIA extends Arbitre {
    int diff1;
    int diff2;
    /**
     *
     * @param p
     * @param d
     */
    public SimulationIA(Properties p, int diff1, int diff2,String n1, String n2 ) {
        super(p, n1, n2);
        //difficulte = d;
        this.diff1 = diff1;
        this.diff2 = diff2;
    }
    
    /**
     *
     */
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
        
        joueurs[J1] = new Ordinateur(true,diff1, prop, tabPieces,J1, nom1);
        joueurs[J2] = new Ordinateur(true,diff2, prop, tabPieces2,J2, nom2);
        
        go();
    }
    
    /**
     *
     */
    public void go(){
        configurations.clear();
        Interface.goPartie();
        if(joueurs[J1] instanceof Ordinateur){
            Ordinateur o = (Ordinateur) joueurs[J1];
            List<Coup[]> tab = new LinkedList();
                for(int i=0; i<joueurs[jCourant].pions().length; i++){
                    if(joueurs[jCourant].pions()[i]!=0){
                        Coup[] tmp = depotPossible(jCourant, i);
                        if(tmp!=null)
                            tab.add(tmp);
                    }
                        
                }
                   
                Coup[] tmp;
                if((tmp=deplacementPossible(jCourant))!=null)
                    tab.add(tmp);

                int taille= 0;
                Iterator<Coup[]> it = tab.iterator();
                while(it.hasNext())
                    taille+=it.next().length;
                it = tab.iterator();
                coups= new Coup[taille];
                for(int i=0; i<taille;i++){
                    Coup[] x = it.next();
                    for(int j=0; j<x.length; j++)
                        coups[i+j]=x[j];
                }
                joue(o.coup(this, coups));
        }
    }

    /**
     *
     */
    @Override
    public void prochainJoueur() {
        etat = ATTENTE_COUP;
        PaneToken.getInstance(this).update();
        if(plateau.estEncerclee(jCourant)){
            etat=FIN;
            Interface.goFin(joueurs[jCourant].nom(), GAGNE);
        }else if(plateau.estEncerclee((jCourant+1)%2)){
            etat=FIN;
            Interface.goFin(joueurs[jCourant].nom(), PERDU);
        }else if(configurations.contains(plateau.hashCode())){
            etat=FIN;
            Interface.goFin(nom1, NUL);
        }else{
            configurations.add(plateau.hashCode());
            jCourant = ++jCourant % 2;
            plateau.setJoueur(jCourant);
            List<Coup[]> tab = new LinkedList();
            for(int i=0; i<joueurs[jCourant].pions().length; i++){
                if(joueurs[jCourant].pions()[i]!=0){
                    Coup[] tmp = depotPossible(jCourant, i);
                    if(tmp!=null)
                        tab.add(tmp);
                }
            }

            Coup[] tmp;
            if((tmp=deplacementPossible(jCourant))!=null)
                tab.add(tmp);

            int taille= 0;
            Iterator<Coup[]> it = tab.iterator();
            while(it.hasNext())
                taille+=it.next().length;
            it = tab.iterator();
            coups = new Coup[taille];
            int i=0;
            while(it.hasNext()){
                Coup[] x = it.next();
                int j;
                for(j=0; j<x.length; j++){
                    coups[i+j]=x[j];
                }
                 i+=j;
            }
            aucun = coups == null || coups.length<=0;
            if(aucun){
                prochainJoueur();
            }else if(precAucun && aucun){
                etat=FIN;
                Interface.goFin(nom1, NUL);
            }else{
                if(joueurs[jCourant] instanceof Ordinateur){
                    Ordinateur o = (Ordinateur) joueurs[jCourant];
                    precAucun = aucun;
                    joue(o.coup(this, coups));
                }
            }
        }
    }

    /**
     *
     * @param d
     */
    @Override
    public void joue(Deplacement d){
                enCoursIt = d.route().iterator();
                enCours = new Deplacement(d.joueur(), enCoursIt.next(),enCoursIt.next());
                nbCoup[jCourant]++;
                refaire.clear();
                historique.add(d);
                //System.err.println(d+" déplacement effectué par "+jCourant);
                etat=JOUE_EN_COURS;
         
    }

    /**
     *
     * @param d
     */
    @Override
    public void joue(Depot d){
        if(nbCoup[jCourant]==0 && jCourant == J1){
            joueurs[jCourant].jouer(d.type());
            plateau.premierPion(FabriqueInsecte.creer(d.type(), jCourant, new Point(0,0)));
            nbCoup[jCourant]++;
            refaire.clear();
            historique.add(d);
            //System.err.println("1- Dépot effectué par "+jCourant+" "+d);
            etat=JOUE_EN_COURS;
        }else if(nbCoup[jCourant]==0 && jCourant == J2){
            if(plateau.premierPionValide(d)){
                joueurs[jCourant].jouer(d.type());
                deposePion(d);
                nbCoup[jCourant]++;
                refaire.clear();
                historique.add(d);
                //System.err.println(jCourant + " - 1st Dépot effectué "+d);
                etat=JOUE_EN_COURS;
            }else{
                System.err.println("Depot impossible");
            }
        }else if(deposePionValide(d) && joueurs[jCourant].pion(d.type())>0){
            
            joueurs[jCourant].jouer(d.type());
            deposePion(d);
            nbCoup[jCourant]++;
            refaire.clear();
            historique.add(d);
            //System.err.println("3- Dépot effectué "+d);
            etat=JOUE_EN_COURS;
            
        }else{
            System.err.println("Depot impossible");
        }
    }
}
