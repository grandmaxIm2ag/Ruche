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
import Modele.Arbitres.producteurConsommateur.ThreadServer;
import Modele.Coup;
import Modele.Deplacement;
import Modele.Depot;
import Modele.FabriqueInsecte;
import Modele.Insecte;
import Modele.Point;
import Vue.Chat;
import Vue.Interface;
import Vue.PaneToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import static java.lang.Thread.sleep;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.MouseEvent;
import ruche.Reglage;

/**
 *
 * @author maxence
 */
public class ReseauServer extends ArbitreReseau{
    public static final int ANNUL = 7;
    private int port;
    private ServerSocket serverSocket;
    Socket client;
    Thread accept;
   
    /**
     *
     * @param p
     */
    public ReseauServer(Properties p,String n1, String n2 ) {
        super(p, n1, n2);
        port = 8000;
        jCourant = 0;
        
    }

    /**
     *
     */
    @Override
    public void init() {
        try{
            System.out.println("Ici : "+(serverSocket==null));
            serverSocket = new ServerSocket(port);
            accept = new Thread(new ThreadServer(serverSocket, client, this));
            accept.start();
            
            System.out.println("Accept : C'est passé");
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

            joueurs[J1] = new Humain(true, prop, tabPieces, J1, nom1);
            joueurs[J2] = new Humain(true, prop, tabPieces2, J2, nom2);
        
            Interface.connexion();
            Interface.goPartie();
            etat = INITIALISATION;
        }catch(Exception e){
            System.err.println("Erreur init reseaux"+e);
            etat = FIN;
        }
        
    }
    
    public void  accept(Socket c){
        client = c;
    }
    
    public void annul() {
        try{
            serverSocket.close();
            Interface.goTest();
        }catch(IOException e){
            System.out.println("Bug ???");
        }
    }
    
    public void launch(){
        try{
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader (new InputStreamReader(client.getInputStream()));
            
        }catch(Exception e){
            System.out.println((in == null)+" "+(out==null)+" "+(client == null));
        }
        
        prod = new Producteur(actions, out);
        cons = new Consommateur(actions, in);
        threads[0] = new Thread(prod);
        threads[0].start();
        threads[1] = new Thread(cons);
        threads[1].start();
            
        nom2 = actions[J2].extraire();
        actions[J1].inserer(nom1);
        
        System.out.println(nom2);
        
        joueurs[J2].setNom(nom2);
        etat = INITIALISATION;
//go();
    }

    @Override
    public void maj(long t){
        if(!actions[J2].estVide()){
            String line = actions[J2].extraire();
            int option = Integer.parseInt(""+line.charAt(0));
            line = line.substring(1);
            switch(option){
                 case MESSAGE:
                     Chat.writeMessage(line, nom2);
                     break;
                 case DEPLACEMENT:
                     aFaire.add(new Deplacement(J2,line));
                     break;
                 case DEPOT:
                     aFaire.add(new Depot(J2,line));
                 case PARTIE:
                     if(line.equals("Abandon" )){
                         etat=FIN;
                         actions[J1].inserer("Fin");
                     }
                     break;
                 default:
                     break;
            }
        }
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
        switch(etat){
            case ANNUL:
                annul();
                Interface.fin();
                break;
            case AIDE:
                temps_ecoule+=nouv;
                if(temps_ecoule>=1000000000){
                    temps_ecoule=0;
                    aide = !aide;
                }
                break;
            case INITIALISATION:
                if(!accept.isAlive()){
                    try {
                        Interface.closeConnexion();
                        if(client==null)
                            etat = FIN;
                        //Interface.error("Echec d'bergement d'une nouvelle partie", "Aucun joueur ne us a rejoint");
                    } catch (Exception ex) {
                        Logger.getLogger(ReseauServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case ATTENTE_COUP:
                if(jCourant == J2){
                    if(!aFaire.isEmpty()){
                        Coup c = aFaire.poll();
                        System.out.println(c+" "+(c==null));
                        joue(c);
                    }
                }else{
                    if(nbCoup[J1]==0)
                        PaneToken.getInstance(this).update();
                }
                break;
            case JOUE_EN_COURS:
                temps_ecoule+=nouv;
                if(temps_ecoule>=100000000){
                    //System.out.println("Joue déplacement "+enCours);
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
                try{
                    if(client!=null)
                        client.close();
                    else{
                        Interface.fin();
                        Interface.goTest();
                        Interface.error("Echec d'hébergement de partie", "Aucun joueur ne vous a rejoint");
                        
                    }
                    serverSocket.close();
                    
                }catch(IOException e){
                    
                }
                
                /*
                if(in != null && out!=null){
                    actions[J1].inserer("Fin");
                    out.close();
                    try {
                        in.close();
                    } catch (IOException ex) {
                        Logger.getLogger(ReseauServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }*/
                
                break;
        }
    }
}
