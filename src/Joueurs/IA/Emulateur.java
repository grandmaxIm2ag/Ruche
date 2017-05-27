/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Joueurs.IA;

import Joueurs.Joueur;
import Modele.Arbitres.Arbitre;
import static Modele.Arbitres.Arbitre.J1;
import static Modele.Arbitres.Arbitre.J2;
import Modele.Coup;
import Modele.Deplacement;
import Modele.Depot;
import Modele.FabriqueInsecte;
import Modele.Insecte;
import Modele.Plateau;
import Modele.Point;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author lies
 */
public class Emulateur {
    long temps;
    long temps_ecoule;
    Plateau m;
    int[] nbCoup;
    Joueur[] joueurs;
    Stack<Coup> historique;
    int jCourant;
    
    public Emulateur(Arbitre tmp){
        joueurs = new Joueur[2];
        nbCoup = new int[2];
        nbCoup[0] = tmp.nbcoups(0);
        nbCoup[1] = tmp.nbcoups(1);
        joueurs[0] = tmp.joueur(0).clone();
        joueurs[1] = tmp.joueur(1).clone();
        jCourant = tmp.jCourant();
        m = tmp.plateau().clone();
        historique = (Stack<Coup>) tmp.historique().clone();
        temps = System.nanoTime();
        temps_ecoule = 0;
    }
    
    public Plateau getPlateau(){
        return m;
    }
    
    public void next(){
        jCourant++;
    }
    
    public int GetValue(int type){
        switch(type){
            case Insecte.REINE:
                return -10000;
            case Insecte.ARAI:
                return 0;
            case Insecte.CLOP:
                return 0;
            case Insecte.COCC:
                return 0;
            case Insecte.FOUR:
                return -10;
            case Insecte.MOUS:
                return 0;
            case Insecte.SAUT:
                return 0;
            case Insecte.SCAR:
                return 0;
        }
        return -1;
    }

    public Emulateur( int[] nbCp, Joueur[] jr,int jC,Plateau pl,Stack<Coup> hist ){
        
        joueurs = new Joueur[2];
        nbCoup = new int[2];
        nbCoup[0] = nbCp[0];
        nbCoup[1] = nbCp[1];
        joueurs[0] = jr[0].clone();
        joueurs[1] = jr[1].clone();
        jCourant = jC;
        m = pl.clone();
        historique = (Stack<Coup>) hist.clone();
        temps = System.nanoTime();
        temps_ecoule = 0;
    }

    public void joue(Coup d){
        if(d instanceof Deplacement)
            joue((Deplacement)d);
        else if(d instanceof Depot)
            joue((Depot)d);
        else
            System.err.println("EMU  "+"Coup Inconnu "+d);
        jCourant = ++jCourant % 2;
    }
    
    public void joue(Deplacement d){
                historique.add(d);
                nbCoup[jCourant]++;
                m.deplacePion(d);
             //   System.err.println("EMU  "+d+" déplacement effectué par "+jCourant);     
    }

    public void joue(Depot d){
        if(nbCoup[jCourant]==0 && jCourant == J1){
            joueurs[jCourant].jouer(d.type());
            m.premierPion(FabriqueInsecte.creer(d.type(), jCourant, new Point(0,0)));
            nbCoup[jCourant]++;
            historique.add(d);
           // System.err.println("EMU  "+jCourant + " - 1st Dépot effectué "+d);
        }else if(nbCoup[jCourant]==0 && jCourant == J2){
            if(m.premierPionValide(d)){
                joueurs[jCourant].jouer(d.type());
                m.deposePion(d);
                nbCoup[jCourant]++;
                historique.add(d);
              //  System.err.println("EMU  "+jCourant + " - 1st Dépot effectué "+d);
            }else{
                System.err.println("EMU  "+jCourant + " - Depot invalide");
            }
        }else if(m.deposePionValide(d) && joueurs[jCourant].pion(d.type())>0){          
            joueurs[jCourant].jouer(d.type());
            m.deposePion(d);
            nbCoup[jCourant]++;
            historique.add(d);
           // System.err.println("EMU  "+jCourant + " - Dépot effectué "+d);
        }else{
            System.err.println("EMU  "+jCourant + " - Depot impossible "+d);
        }
    }
    
    public void precedent(){
        if(!historique.isEmpty()){
            jCourant = ++jCourant % 2;
            nbCoup[jCourant]--;
            Coup c = historique.pop();
            if(c instanceof Deplacement){
                Deplacement d = (Deplacement) c;
                m.deplacePion(new Deplacement(d.joueur(),d.destination(), d.source()));
            }else if(c instanceof Depot){
                Depot d = (Depot) c;
                m.retirerPion(d.destination());
                joueurs[jCourant].pred(d.type());
            }
        }else{
            System.err.println("EMU  "+"Aucun coup précedent");
        }
    }
    
    public int jCourant(){
        return jCourant;
    }
    
    public Joueur joueur(int i){
        return joueurs[i];
    }
    
    public Coup[] PossibleMoves() {
        //System.out.println("possible moves for "+jCourant);
        List<Coup[]> tab = new LinkedList();
            for(int i=0; i<joueurs[jCourant].pions().length; i++){
                if(joueurs[jCourant].pions()[i]!=0){
                    Coup[] tmp = m.depotPossible(jCourant, i);
                    if(tmp!=null)
                        tab.add(tmp);
                }
            }

            Coup[] tmp;
            if((tmp=m.deplacementPossible(jCourant))!=null)
                tab.add(tmp);

            int taille= 0;
            Iterator<Coup[]> it = tab.iterator();
            while(it.hasNext())
                taille+=it.next().length;
            it = tab.iterator();

            Coup[] coups = new Coup[taille];
            int i=0;
            while(it.hasNext()){
                Coup[] x = it.next();
                int j;
                for(j=0; j<x.length; j++){
                    coups[i+j]=x[j];
                }
                 i+=j;
            }
        return coups;
    }

    @Override
    public Emulateur clone(){
        Emulateur mm = new Emulateur(nbCoup, joueurs,jCourant,m,historique );
        return mm;
    }
}
