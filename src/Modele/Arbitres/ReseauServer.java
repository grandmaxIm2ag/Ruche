/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele.Arbitres;

import Joueurs.Humain;
import Joueurs.Ordinateur;
import static Modele.Arbitres.Arbitre.ATTENTE_COUP;
import static Modele.Arbitres.Arbitre.J1;
import static Modele.Arbitres.Arbitre.J2;
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
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.MouseEvent;
import ruche.Reglage;

/**
 *
 * @author maxence
 */
public class ReseauServer extends Arbitre{
    private int port;
    private ServerSocket serverSocket;
    Socket client;
    private PrintWriter out;
    private BufferedReader in;
    File[] actions;
    Producteur prod;
    Consommateur cons;
    Thread[] threads;
    /**
     *
     * @param p
     */
    public ReseauServer(Properties p,String n1, String n2 ) {
        super(p, n1, n2);
        port = 8000;
        jCourant = 0;
        actions = new File[2];
        actions[J1]=new File();
        actions[J2]=new File();
        threads = new Thread[2];
    }

    /**
     *
     */
    @Override
    public void init() {
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            client = serverSocket.accept();
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader (new InputStreamReader(client.getInputStream()));
            
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
            tabPieces2[0]=(int)Reglage.lis("nbReine");
            tabPieces2[1]=(int)Reglage.lis("nbScarabee");
            tabPieces2[2]=(int)Reglage.lis("nbSauterelle");
            tabPieces2[3]=(int)Reglage.lis("nbFourmi");
            tabPieces2[4]=(int)Reglage.lis("nbAraignee");
            tabPieces2[5]=(int)Reglage.lis("nbCoccinelle");
            tabPieces2[6]=(int)Reglage.lis("nbMoustique");  
            tabPieces2[7]=(int)Reglage.lis("nbCloporte");
            
            
            prod = new Producteur(actions, out);
            cons = new Consommateur(actions, in);
            threads[0] = new Thread(prod);
            threads[0].start();
            threads[1] = new Thread(cons);
            threads[1].start();
            
            actions[J1].inserer(nom1);
            nom2 = actions[J2].extraire();
            
            
            joueurs[J1] = new Humain(true, prop, tabPieces, J1, nom1);
            joueurs[J2] = new Humain(true, prop, tabPieces2, J2, nom2);
            
            etat = INITIALISATION;
            go();
        }catch(IOException e){
            System.err.println(e);
            etat = FIN;
        }
        
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
                    actions[J1].inserer(d.toString());
                }
                System.err.println(d+" déplacement effectué "+enCours);
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
                actions[J1].inserer(d.toString());
            }
            System.err.println("1- Dépot effectué "+d);
        }else if(nbCoup[jCourant]==0){
            if(plateau.premierPionValide(d)){
                joueurs[jCourant].jouer(d.type());
                deposePion(d);
                nbCoup[jCourant]++;
                refaire.clear();
                historique.add(d);
                etat=A_JOUER;
                System.err.println("2- Dépot effectué "+d);
                if(jCourant == J1){
                    actions[J1].inserer(d.toString());
                }
            }else{
                System.err.println("Depot impossible");
            }
        }else if(deposePionValide(d) && joueurs[jCourant].pion(d.type())>0){
            
            if((plateau.reine(jCourant)==null && (d.type()==Insecte.REINE || nbCoup[jCourant]<3)) || plateau.reine(jCourant)!=null){
                joueurs[jCourant].jouer(d.type());
                deposePion(d);
                nbCoup[jCourant]++;
                etat=A_JOUER;
                refaire.clear();
                historique.add(d);
                System.err.println("3- Dépot effectué "+d);
                if(jCourant == J1){
                    actions[J1].inserer(d.toString());
                }
            }else{
                System.err.println("Vous devez déposé une reine "+jCourant);
            }
        }else{
            System.err.println("Depot impossible");
        }
        
        

    }

    /**
     *
     */
    @Override
    public void prochainJoueur() {
        etat = ATTENTE_COUP;
        PaneToken.getInstance(this).update();
        jCourant = ++jCourant % 2;

        if(plateau.estEncerclee(jCourant)){
            etat=FIN;
            System.err.println(jCourant+" à perdu");
        }else{
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
                System.err.println("Match nul");
            }else{
                
            }
        }
    }
    
    @Override
    public void maj(long t){
        if(jCourant==J1)
            if(Interface.pointeur().event()!=null){
                boolean b = this.accept(Interface.pointeur());
                if(b)
                    plateau.clearAide();
                    if(Interface.pointeur().event().getEventType() == MouseEvent.MOUSE_CLICKED && etat == AIDE){
                        etat = ATTENTE_COUP;
                        aide = false;
                    }
                Interface.pointeur().traiter();
            }
        long nouv = t-temps;
        temps=t;
        System.out.println("JOue en cours ? "+(JOUE_EN_COURS==etat));
        switch(etat){
            case AIDE:
                temps_ecoule+=nouv;
                if(temps_ecoule>=1000000000){
                    temps_ecoule=0;
                    aide = !aide;
                }
                break;
            case INITIALISATION:
                break;
            case ATTENTE_COUP:
                if(jCourant == J2){
                    if(!actions[J2].estVide()){
                        String line = actions[J2].extraire();
                        if(line.equals("Abandon" )){
                            etat=FIN;
                            actions[J1].inserer("Fin");
                        }else if(line.charAt(0)=='(')
                            joue(new Deplacement(J2,line));
                        else
                            joue(new Depot(J2,line));
                        }
                }
                break;
            case JOUE_EN_COURS:
                temps_ecoule+=nouv;
                if(temps_ecoule>=100000000){
                    System.out.println("Joue déplacement "+enCours);
                    temps_ecoule=0;
                    if(enCours!=null){
                        plateau.deplacePion(enCours);
                        
                        if(!enCoursIt.hasNext()){
                            enCours = null;
                            etat=A_JOUER;
                        }else{
                            Point p = enCoursIt.next();
                            Point src = enCours.destination().clone();
                            enCours = new Deplacement(enCours.joueur(),src, p );
                        }
                    }else{
                        enCours = null;
                        etat=A_JOUER;
                    }
                }
                break;
            case A_JOUER:
                prochainJoueur();
                plateau.setJoueur(jCourant);
                plateau.clearAide();
                break;
            case FIN:
                actions[J1].inserer("Fin");
                
                Interface.goTest();
                out.close();
        
                try {
                    in.close();
                } catch (IOException ex) {
                    Logger.getLogger(ReseauServer.class.getName()).log(Level.SEVERE, null, ex);
                }

                break;
        }
    }
    
    @Override
    public void abandon(){
        actions[J1].inserer("Abandon");
        etat=FIN;
    }
}
