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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
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
    
    final static int ATTENTE_COUP = 0;
    final static int JOUE_EN_COURS = 1;
    final static int A_JOUER = 2;
    final static int FIN = 3;
    
    int etat;
    long temps;
    long temps_ecoule;
    
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
    
    Coup[] deplacements;
    Coup[] depots;
    Coup[] coups;
    boolean aucun;
    boolean precAucun;
    
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
        //diff[Ordinateur.FACILE_ALEATOIRE] = "Facile";
        //diff[Ordinateur.MOYEN] = "Normal";
        //diff[Ordinateur.DIFFICILE] = "Difficile";
        
        type = JvIA;
        types = new String[2];
        types[JvJ] = "Joueur vs Joueur";
        types[JvIA] = "Joueur vs IA";
        
        chargeur = new Chargeur();
        
        
        nbCoup = new int[2];
        nbCoup[0]=0; nbCoup[1]=0;
        etat = ATTENTE_COUP;
        aucun = false;
        temps = System.nanoTime();
        temps_ecoule = 0;
        precAucun = false;
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
                joueurs[J1] = new Humain(true, prop, tabPieces, J1);
                joueurs[J2] = new Humain(true, prop, tabPieces, J2);
                break;
            case JvIA:
                joueurs[J1] = new Humain(true, prop, tabPieces,  J1);
                joueurs[J2] = new Ordinateur(true,difficulte, prop, tabPieces,  J2);
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
        boolean b = false;
        
        for(int i=0; i<deplacements.length; i++)
            b|=d.equals(deplacements[i]);
        
        return b;
    }
    public void deplacePion(Deplacement d){
        plateau.deplacePion(d);
    }
    public void deposePion(Depot d){
        plateau.deposePion(d);
    }
    
    public Coup[] deplacementPossible(int j){
        if(plateau.reine(jCourant)==null)
            return null;
        else
            return plateau.deplacementPossible(jCourant);
    }
    
    public Coup[] depotPossible(int j, int t){
        if((plateau.reine(jCourant)==null && t!=Insecte.REINE && nbCoup[j]>=3) ){
            return null;
        }else if ( joueurs[jCourant].pion(t)<=0)
            return null;
        else
            return plateau.depotPossible(jCourant, t);
    }
    
    public void joue(Coup d){
        if(d instanceof Deplacement)
            joue((Deplacement)d);
        else if(d instanceof Depot)
            joue((Depot)d);
        else
            System.err.println("Coup Inconnu "+d);
    }
    public void joue(Deplacement d){
        if(plateau().reine(jCourant)!=null){
            if(deplacePionValide(d)){
                deplacePion(d);
                nbCoup[jCourant]++;
                refaire.clear();
                historique.add(d);
                System.err.println(d+" déplacement effectué");
                prochainJoueur();
            }else{
                System.err.println("Deplacement impossible "+d);
            }
        }else{
            System.err.println("Déplacement impossible tant que la reine n'a pas été déposée "+jCourant);
        }
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
            
            if((plateau.reine(jCourant)==null && (d.type()==Insecte.REINE || nbCoup[jCourant]<3)) || plateau.reine(jCourant)!=null){
                joueurs[jCourant].jouer(d.type());
                deposePion(d);
                nbCoup[jCourant]++;
                refaire.clear();
                historique.add(d);
                System.err.println("3- Dépot effectué "+d);
                prochainJoueur();
            }else{
                System.err.println("Vous devez déposé une reine "+jCourant);
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
    
    public void charger(String plateau){
        
        chargeur.init(prop, plateau);
        this.plateau = chargeur.charger();
        type = chargeur.type();
        difficulte = chargeur.difficulte();
        init();
        historique = chargeur.historique();
        refaire = chargeur.refaire();
        
        String[] str = chargeur.joueur();
        joueurs[J1].nom = str[J1].split("=")[0];
        String[] str2 = str[J1].split("=")[1].split(":");
        int[] tab = new int[str2.length-1];
        for(int i=1; i<str2.length; i++)
            tab[i-1]=Integer.parseInt(str2[i]);
        joueurs[J1].setPieces(tab);
        joueurs[J2].nom = str[J2].split("=")[0];
        str2 = str[J1].split("=")[1].split(":");
        tab = new int[str2.length-1];
        for(int i=1; i<str2.length; i++)
            tab[i-1]=Integer.parseInt(str2[i]);
        joueurs[J2].setPieces(tab);

    }
    public void sauvegarder(String nomSauv){
        String sauv = "";
        
        sauv += type+":"+difficulte+":"+nbCoup[J1]+":"+nbCoup[J1]+":"+jCourant+"\n";
        sauv += joueurs[J1]+"\n";
        sauv += joueurs[J2]+"\n";
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
        sauv += "Refaire en cours\n";
        while(!refaire.isEmpty()){
            Coup c = refaire.pop();
            sauv += c+"\n";
            tmp.push(c);
        }
        while(!tmp.isEmpty())
            refaire.push(tmp.pop());
        
        try{
            File f = new File("Ressources/Sauvegardes/"+nomSauv);
            f.createNewFile();
            FileWriter output = new FileWriter(f);
            output.write(sauv);
            output.close();
                //MaJ BDD
            
        }catch(FileNotFoundException e){
            System.err.println("Impossible de sauvegarder, fichier introuvable "+nomSauv);
        }catch(IOException e){
            System.err.println("Impossible de sauvegarder "+nomSauv);
        }
        
    }
    
    public void aide(){
        /*
        A faire dès que les IA seront opérationnels
        */
    }

    public void accept(Visiteur dessinateur) {
        plateau.accept(dessinateur);
    }

    public void prochainJoueur() {
        
        etat = 
        jCourant = ++jCourant % 2;
        
        if(plateau.estEncerclee(jCourant)){
            System.err.println(jCourant+" à perdu");
        }else{
            List<Coup[]> tab = new LinkedList();
            for(int i=0; i<joueurs[jCourant].pions().length; i++){
                if(joueurs[jCourant].pions()[i]!=0)
                    tab.add(depotPossible(jCourant, i));
            }
            
            tab.add(deplacementPossible(jCourant));
            
            int taille= 0;
            Iterator<Coup[]> it = tab.iterator();
            while(it.hasNext())
                taille+=it.next().length;
            it = tab.iterator();
            for(int i=0; i<taille;i++){
                Coup[] x = it.next();
                for(int j=0; j<x.length; j++)
                    coups[i+j]=x[j];
            }
            aucun = coups.length<=0;
            if(plateau.aucunCoup(jCourant)){
                prochainJoueur();
            }else{
                if(joueurs[jCourant] instanceof Ordinateur){
                    Ordinateur o = (Ordinateur) joueurs[jCourant];
                    joue(o.coup(this, coups));
                }
            }
        }
    }
    
    public int type(){
        return type;
    }
    public int jCourant(){
        return jCourant;
    }
    public int difficulte(){
        return difficulte;
    }
    
    public void maj(long t){
        long nouv = t-temps;
        temps=t;
        switch(etat){
            case ATTENTE_COUP:
                go();
                break;
            case JOUE_EN_COURS:
                temps_ecoule+=nouv;
                if(temps_ecoule>=100000000){
                    temps_ecoule=0;
                    etat=A_JOUER;
                }
                break;
            case A_JOUER:
                prochainJoueur();
                break;
            case FIN:
                break;
        }
    }
    boolean nul(){
        boolean b1 = true;
        for(int i=0; i<joueurs[J1].pions().length; i++)
                    b1 &= joueurs[J1].pions()[i]==0;
        boolean b2 = true;
        for(int i=0; i<joueurs[J2].pions().length; i++)
                    b2 &= joueurs[J2].pions()[i]==0;
        return plateau.aucunCoup(J1)&&plateau.aucunCoup(J2)&&b1&&b2;
    }
    
    public void go(){
        
    }
    
}
