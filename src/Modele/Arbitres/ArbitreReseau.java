/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele.Arbitres;

import static Modele.Arbitres.Arbitre.JOUE_EN_COURS;
import Modele.Arbitres.producteurConsommateur.Consommateur;
import Modele.Arbitres.producteurConsommateur.File;
import Modele.Arbitres.producteurConsommateur.Producteur;
import Modele.Coup;
import Modele.Deplacement;
import Modele.Depot;
import Modele.FabriqueInsecte;
import Modele.Insecte;
import Modele.Point;
import Vue.Interface;
import Vue.PaneToken;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;


/**
 *
 * @author maxence
 */
public abstract class ArbitreReseau extends Arbitre{
    public final static int MESSAGE = 0;
    final static int DEPLACEMENT = 1;
    final static int DEPOT=2;
    final static int PARTIE=3;
    
    PrintWriter out;
    BufferedReader in;
    File[] actions;
    Producteur prod;
    Consommateur cons;
    Thread[] threads;
    Queue<Coup> aFaire;
     
    public ArbitreReseau(Properties p, String n1, String n2) {
        super(p, n1, n2);
        actions = new File[2];
        actions[J1]=new File();
        actions[J2]=new File();
        threads = new Thread[2];
        aFaire = new LinkedBlockingQueue();
    }

    /**
     *
     * @param d
     */
    @Override
    public void joue(Deplacement d){
        if(plateau().reine(jCourant)!=null){
            //if(deplacePionValide(d)){
                enCoursIt = d.route().iterator();
                enCours = new Deplacement(d.joueur(), enCoursIt.next(),enCoursIt.next());
                nbCoup[jCourant]++;
                refaire.clear();
                historique.add(d);
                etat = JOUE_EN_COURS;
                if(jCourant == J1){
                    actions[J1].inserer(DEPLACEMENT+d.toString());
                }
                //System.err.println(d+" déplacement effectué "+enCours);
            //}else{
                //System.err.println("Deplacement impossible "+d);
            //}
        }else{
            System.err.println("Déplacement impossible tant que la reine n'a pas été déposée "+jCourant);
        }
    }

    /**
     *
     * @param d
     */
    @Override
    public void joue(Depot d){
        if(nbCoup[jCourant] + nbCoup[(jCourant+1)%2]==0){
            joueurs[d.joueur()].jouer(d.type());
            plateau.premierPion(FabriqueInsecte.creer(d.type(), jCourant, new Point(0,0)));
            etat=A_JOUER;
            nbCoup[jCourant]++;
            refaire.clear();
            historique.add(d);
            if(jCourant == J1){
                actions[J1].inserer(DEPOT+d.toString());
            }
            System.err.println("1- Dépot effectué "+d);
            System.out.println(plateau.toString());
        }else if(nbCoup[jCourant]==0){
            if(plateau.premierPionValide(d)){
                joueurs[jCourant].jouer(d.type());
                deposePion(d);
                nbCoup[jCourant]++;
                refaire.clear();
                historique.add(d);
                etat=A_JOUER;
                //System.err.println("2- Dépot effectué "+d);
                if(jCourant == J1){
                    actions[J1].inserer(DEPOT+d.toString());
                }
            }else{
                //System.err.println("Depot impossible");
            }
        }else if(deposePionValide(d) && joueurs[jCourant].pion(d.type())>0){
            
            if((plateau.reine(jCourant)==null && (d.type()==Insecte.REINE || nbCoup[jCourant]<3)) || plateau.reine(jCourant)!=null){
                joueurs[jCourant].jouer(d.type());
                deposePion(d);
                nbCoup[jCourant]++;
                etat=A_JOUER;
                refaire.clear();
                historique.add(d);
                //System.err.println("3- Dépot effectué "+d);
                if(jCourant == J1){
                    actions[J1].inserer(DEPOT+d.toString());
                }
            }else{
                System.err.println("Vous devez déposé une reine "+jCourant);
            }
        }else{
            //System.err.println("Depot impossible");
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
        }else if(configurations.containsKey(plateau.hashCode()) && configurations.get(plateau.hashCode())>2 ){
            etat=FIN;
            //System.out.println(configurations.toString()+" "+plateau.hashCode());
            Interface.goFin(nom1, NUL);
            System.err.println("Match nul");
        }else{
            if(configurations.containsKey(plateau.hashCode()))
                configurations.put(plateau.hashCode(), configurations.get(plateau.hashCode())+1 );
            else
                configurations.put(plateau.hashCode(), 1 );
            
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
            System.out.println(nbCoup[J1]+" "+nbCoup[J2]);
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
                
            }
        }
    }
    
    
    
    @Override
    public void abandon(){
        actions[J1].inserer(PARTIE+"Abandon");
        etat=FIN;
    }
    
    public void newMessage(String mess){
        actions[J1].inserer(MESSAGE+mess);
    }
    
    
}
