/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Joueurs.IA;

import Joueurs.Joueur;
import Joueurs.Ordinateur;
import Modele.Arbitres.Arbitre;
import static Modele.Arbitres.Arbitre.J1;
import static Modele.Arbitres.Arbitre.J2;
import Modele.Coup;
import Modele.Deplacement;
import Modele.Depot;
import Modele.FabriqueInsecte;
import Modele.Plateau;
import Modele.Point;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 *
 * @author lies
 */
public class Emulateur implements Runnable{
    long temps;
    long temps_ecoule;
    Plateau m;
    int[] nbCoup;
    Joueur[] joueurs;
    Stack<Coup> historique;
    int jCourant;
    Map<Plateau, Integer> configMin;
    Map<Plateau, Integer> configMax;
    boolean min;
    Heuristique heurs;
    int me;
    int searchDepth;
    HeurPartage h;
    int idx;
    Coup[] coups;
    
    public Emulateur(Arbitre tmp, int j){
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
        me = j;
    }
    public Emulateur(boolean b, int i, int pronf, HeurPartage h2, Coup[] d2, int[] nbCp, Joueur[] jr,int jC,Plateau pl,Stack<Coup> hist, int j, Heuristique h3){
        this(nbCp, jr, jC,pl,hist);
        idx = i;
        searchDepth = pronf;
        h = h2;
        coups = d2;
        min = b;
        me = j;
        heurs = h3;
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
            System.err.println("Coup Inconnu "+d);
        jCourant = ++jCourant % 2;
    }
    
    public void joue(Deplacement d){
                historique.add(d);
                nbCoup[jCourant]++;
                m.deplacePion(d);
               // System.err.println(d+" déplacement effectué par "+jCourant);     
    }

    public void joue(Depot d){
        if(nbCoup[jCourant]==0 && jCourant == J1){
            joueurs[jCourant].jouer(d.type());
            m.premierPion(FabriqueInsecte.creer(d.type(), jCourant, new Point(0,0)));
            nbCoup[jCourant]++;
            historique.add(d);
           // System.err.println(jCourant + " - 1st Dépot effectué "+d);
        }else if(nbCoup[jCourant]==0 && jCourant == J2){
            if(m.premierPionValide(d)){
                joueurs[jCourant].jouer(d.type());
                m.deposePion(d);
                nbCoup[jCourant]++;
                historique.add(d);
                joueurs[jCourant].jouer(d.type());
               // System.err.println(jCourant + " - 1st Dépot effectué "+d);
            }else{
                System.err.println(jCourant + " - Depot invalide");
            }
        }else if(m.deposePionValide(d) && joueurs[jCourant].pion(d.type())>0){          
            joueurs[jCourant].jouer(d.type());
            m.deposePion(d);
            nbCoup[jCourant]++;
            historique.add(d);
          //  System.err.println(jCourant + " - Dépot effectué "+d);
        }else{
          //  System.err.println(jCourant + " - Depot impossible "+d);
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
            System.err.println("Aucun coup précedent");
        }
    }
    
    public int jCourant(){
        return jCourant;
    }
    
    public Joueur joueur(int i){
        return joueurs[i];
    }
    
    public Coup[] PossibleMoves() {
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
    
    public void SetConf(Map<Plateau, Integer> conf){
        conf.putAll(configMin);
        conf.putAll(configMax);
    }
    
    @Override
    public Emulateur clone(){
        Emulateur mm = new Emulateur(nbCoup, joueurs,jCourant,m,historique );
        return mm;
    }
    
    public int Max(int profondeur, Coup[] d){
        if(searchDepth - profondeur <= 0)
            return heurs.EvalPlateau(this, d, (Ordinateur)joueurs[me]);
        
        int max_poids = AI.MIN;
        profondeur++;
        for(int i=0;i < d.length;i++){
            //System.out.println("max "+i+" "+d[i]);
            joue(d[i]);
            Coup [] cpt = PossibleMoves();
            if(cpt != null && cpt.length != 0){
                int tmp = Min(profondeur, cpt);
                if(tmp > max_poids){
                    max_poids = tmp;
                }
            }
            precedent();
        }
        return max_poids;
    }
    
    public int Min(int profondeur, Coup[] d){
       // System.out.println("appel min : "+ profondeur);
        if(searchDepth - profondeur <= 0)
            return heurs.EvalPlateau(this, d,(Ordinateur)joueurs[me]);
        int min_poids = AI.MAX;
        //profondeur++;
        for(int i=0;i < d.length;i++){
            //System.out.println("min "+i+" "+d[i]);
            joue(d[i]);
            Coup [] cpt = PossibleMoves();
            if(cpt != null && cpt.length != 0){
                int tmp = Max(profondeur+1,cpt);
                if(tmp < min_poids){
                    min_poids = tmp;
                } 
            }
            precedent();
        }
        return min_poids;
    }
    

    @Override
    public void run() {
        System.out.println(idx+" commence");
        if(min){
            h.ajout(idx, Max(1, coups));
        }else{
            h.ajout(idx, Max(1, coups));
        }
        System.out.println(idx+" fini");
    }
}
