/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import Joueurs.Humain;
import Joueurs.Joueur;
import Joueurs.Ordinateur;
import java.io.File;
import java.io.*;
import java.io.IOException;
import java.util.Properties;
import java.util.Stack;
import ruche.Reglage;

/**
 *
 * @author grandmax
 */
public class Arbitre {
    public final static int JvJ=0;
    public final static int JvIA=1;
            
    public final static int J1 = 0;
    public final static int J2 = 1;
    
    
    public Properties prop;
    Joueur[] joueurs;
    int jCourant, type, difficulte;
    Plateau plateau;
    Chargeur chargeur;
    Stack<Coup> historique;
    Stack<Coup> refaire;
    
    String[] diff;
    String[] types;
    String[] plateaux;
    
    int[] nbCoup;
    
    public Arbitre(Properties p){
        Reglage.init(p);
        prop = p;
        joueurs = new Joueur[2];
        jCourant = J1;
        historique = new Stack();
        refaire = new Stack();
        plateau = new Plateau(0,0,Reglage.lis("nbPiece"),Reglage.lis("nbPiece"),p);
        
        difficulte = Ordinateur.MOYEN;
        diff = new String[3];
        diff[Ordinateur.FACILE_ALEATOIRE] = "Facile";
        diff[Ordinateur.MOYEN] = "Normal";
        diff[Ordinateur.DIFFICILE] = "Difficile";
        
        type = JvIA;
        types = new String[2];
        types[JvJ] = "Joueur vs Joueur";
        types[JvIA] = "Joueur vs IA";
        
        chargeur = new Chargeur();
        
        nbCoup = new int[2];
        nbCoup[0]=0; nbCoup[1]=0;
    }
    
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
        
        
        switch(type){
            case JvJ:
                joueurs[J1] = new Humain(true, prop, tabPieces, (int)Reglage.lis("nbPiece"));
                joueurs[J2] = new Humain(true, prop, tabPieces, (int)Reglage.lis("nbPiece"));
                break;
            case JvIA:
                joueurs[J1] = new Humain(true, prop, tabPieces, (int)Reglage.lis("nbPiece"));
                joueurs[J2] = new Ordinateur(true,difficulte, prop, tabPieces, (int)Reglage.lis("nbPiece"));
                break;
        }
        
    }
    
    public Plateau plateau(){
        return plateau;
    }
    public Joueur joueur(int i){
        return joueurs[i];
    }
    
    public boolean deposePionValide(Depot d){
        return plateau.deposePionValide(d);
    }
    public boolean deplacePionValide(Deplacement d){
        return plateau.deplacePionValide(d);
    }
    public void deplacePion(Deplacement d){
        plateau.deplacePion(d);
    }
    public void deposePion(Depot d){
        plateau.deposePion(d);
    }
    
    public void joue(Coup d){
        if(d instanceof Deplacement)
            joue((Deplacement)d);
        else if(d instanceof Depot)
            joue((Depot)d);
        else
            System.err.println("Coup Inconnu");
    }
    public void joue(Deplacement d){
        if(deplacePionValide(d)){
            deplacePion(d);
            nbCoup[jCourant]++;
            prochainJoueur();
            refaire.clear();
            historique.add(d);
        }else{
            System.err.println("Deplacement impossible");
        }
    }
    public void joue(Depot d){
        if(deposePionValide(d) && joueurs[jCourant].pion(d.type())>0){
            if((plateau.reine(jCourant)==null && (d.type()!=Insecte.REINE || nbCoup[jCourant]<3))||plateau.reine(jCourant)!=null){
                deposePion(d);
                nbCoup[jCourant]++;
                prochainJoueur();
                refaire.clear();
                historique.add(d);
            }else{
                System.err.println("Vous devez déposé une reine");
            }
        }else{
            System.err.println("Depot impossible");
        }
    }
    
    public void nouvellePartie(){
        plateau = new Plateau(0,0,Reglage.lis("nbPiece"),Reglage.lis("nbPiece"),prop);
        nbCoup[0]=0; nbCoup[1]=0;
        jCourant = 0;
        init();
    }
    
    public void precedent(){
        if(!historique.isEmpty()){
            Coup c = historique.pop();
            refaire.add(c);
            if(c instanceof Deplacement){
                Deplacement d = (Deplacement) c;
                plateau.deplacePion(new Deplacement(d.joueur(),d.destination(), d.source()));
            }else if(c instanceof Depot){
                Depot d = (Depot) c;
                plateau.retirerPion(d.destination());
            }
        }else{
            System.err.println("Aucun coup précedent");
        }
    }
    public void refaire(){
        if(!refaire.isEmpty()){
            joue(refaire.pop());
        }else{
            System.out.println("Aucun coup à refaire");
        }
    }
    public void abandon(){
        
    }
    
    public Plateau charger(String plateau){
        
        return null;
    }
    public void sauvegarder(String nomSauv){
        String sauv = "";
        
        sauv += type+":"+difficulte+":"+nbCoup[J1]+":"+nbCoup[J1]+":"+jCourant+"\n";
        sauv += joueurs[J1]+"\n";
        sauv += joueurs[J2]+"\n";
        sauv += "PLateau en cours\n";
        sauv += plateau.toString();
        sauv += "Historique en cours\n";
        Stack<Coup> tmp = new Stack();
        while(!historique.isEmpty()){
            Coup c = historique.pop();
            sauv += c+"\n";
            tmp.push(c);
        }
        while(!tmp.isEmpty())
            historique.push(tmp.pop());
        sauv += "Refaire en cours \n";
        while(!refaire.isEmpty()){
            Coup c = refaire.pop();
            sauv += c+"\n";
            tmp.push(c);
        }
        while(!tmp.isEmpty())
            refaire.push(tmp.pop());
        
        try{
            File f = new File("Sauvegardes/"+nomSauv);
            try (FileWriter output = new FileWriter(f)) {
                output.write(sauv);
                //MaJ BDD
            }
        }catch(IOException e){
            System.err.println("Impossible de sauvegarder "+nomSauv);
        }
        
    }
    
    public void aide(){
        
    }

    public void accept(Visiteur dessinateur) {
        plateau.accept(dessinateur);
    }

    public void prochainJoueur() {
        
        jCourant = ++jCourant % 2;
        if(plateau.aucunCoup(jCourant)){
            prochainJoueur();
        }
    }
    
}
