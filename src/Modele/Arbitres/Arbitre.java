/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele.Arbitres;

import Joueurs.Joueur;
import java.io.File;
import java.io.*;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Stack;
import ruche.Reglage;
import Modele.*;
import Vue.Interface;
import Vue.PaneToken;
import Vue.Pointeur;
import java.util.ArrayList;

/**
 *
 * @author grandmax
 */
public abstract class Arbitre {
            
    /**
     *
     */
    public final static int J1 = 0;

    /**
     *
     */
    public final static int J2 = 1;
    
    final static int ATTENTE_COUP = 0;
    final static int JOUE_EN_COURS = 1;
    final static int A_JOUER = 2;
    final static int FIN = 3;
    
    int etat;
    long temps;
    long temps_ecoule;
    
    Deplacement enCours;
    Iterator<Point> enCoursIt;
    
    /**
     *
     */
    public Properties prop;
    Joueur[] joueurs;
    int jCourant, type, difficulte;
    Plateau plateau;
    Chargeur chargeur;
    Stack<Coup> historique;
    Stack<Coup> refaire;
    
    int[] nbCoup;
    
    Coup[] deplacements;
    Coup[] depots;
    Coup[] coups;
    boolean aucun;
    boolean precAucun;
    
    Insecte initDepl;
    int initDepot;
    
    /**
     *
     * @param p
     */
    public Arbitre(Properties p){
        Reglage.init(p);
        prop = p;
        joueurs = new Joueur[2];
        jCourant = J1;
        historique = new Stack();
        refaire = new Stack();
        plateau = new Plateau(0,0,Reglage.lis("nbPiece"),Reglage.lis("nbPiece"),p);
        
        chargeur = new Chargeur();
        
        nbCoup = new int[2];
        nbCoup[0]=0; nbCoup[1]=0;
        etat = ATTENTE_COUP;
        aucun = false;
        temps = System.nanoTime();
        temps_ecoule = 0;
        precAucun = false;
    }
    
    /**
     *
     */
    public abstract void init();
    
    /**
     *
     * @return
     */
    public Plateau plateau(){
        return plateau;
    }

    /**
     *
     * @param i
     * @return
     */
    public Joueur joueur(int i){
        return joueurs[i];
    }
    
    /**
     *
     * @param d
     * @return
     */
    public boolean deposePionValide(Depot d){
        return plateau.deposePionValide(d);
    }

    /**
     *
     * @param d
     * @return
     */
    public boolean deplacePionValide(Deplacement d){
        boolean b = false;
        
        for (Coup deplacement : deplacements) {
            b |= d.equals(deplacement);
        }
        
        return b;
    }

    /**
     *
     * @param d
     */
    public void deplacePion(Deplacement d){
        plateau.deplacePion(d);
    }

    /**
     *
     * @param d
     */
    public void deposePion(Depot d){
        plateau.deposePion(d);
    }
    
    /**
     *
     * @param j
     * @return
     */
    public Coup[] deplacementPossible(int j){
        if(plateau.reine(jCourant)==null)
            return null;
        else
            return plateau.deplacementPossible(jCourant);
    }
    
    /**
     *
     * @param j
     * @param t
     * @return
     */
    public Coup[] depotPossible(int j, int t){
        if((plateau.reine(jCourant)==null && t!=Insecte.REINE && nbCoup[j]>=3) ){
            return null;
        }else if ( joueurs[jCourant].pion(t)<=0)
            return null;
        else
            return plateau.depotPossible(jCourant, t);
    }
    
    /**
     *
     * @param e
     * @return
     */
    public Coup[] deplacementPossible(Insecte e){
        if(plateau.reine(jCourant)==null)
            return null;
        else{
            List<Coup> l = plateau.deplacementPossible(e);
            Coup[] res = new Coup[l.size()];
            for(int i=0; i<l.size(); i++)
                res[i]=l.get(i);
            return res;
        }
            
    }
    
    /**
     *
     * @param d
     */
    public void joue(Coup d){
        
        if(d instanceof Deplacement)
            joue((Deplacement)d);
        else if(d instanceof Depot)
            joue((Depot)d);
        else
            System.err.println("Coup Inconnu "+d);
    }
    
    /**
     *
     * @param d
     */
    public abstract void joue(Deplacement d);
    
    /**
     *
     * @param d
     */
    public abstract void joue(Depot d);
    
    /**
     *
     */
    public void nouvellePartie(){
        plateau = new Plateau(0,0,Reglage.lis("nbPiece"),Reglage.lis("nbPiece"),prop);
        nbCoup[0]=0; nbCoup[1]=0;
        jCourant = 0;
        init();
    }
    
    /**
     *
     */
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

    /**
     *
     */
    public void refaire(){
        if(!refaire.isEmpty()){
            joue(refaire.pop());
        }else{
            System.out.println("Aucun coup à refaire");
        }
    }

    /**
     *
     */
    public void abandon(){
        etat = FIN;
        System.err.println(jCourant+" a abandonné");
    }
    
    /**
     *
     * @param plateau
     */
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

    /**
     *
     * @param nomSauv
     */
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
    
    /**
     *
     */
    public void aide(){
        /*
        A faire dès que les IA seront opérationnels
        */
    }

    /**
     *
     * @param dessinateur
     */
    public boolean accept(Visiteur dessinateur) {
        return plateau.accept(dessinateur);
    }
    
    /**
     *
     * @param buttonDrawer
     */
    public void acceptButton(Visiteur buttonDrawer) {
        buttonDrawer.visite(this);
    }
        
    /**
     *
     */
    public abstract void prochainJoueur();
    
    /**
     *
     * @return
     */
    public int type(){
        return type;
    }

    /**
     *
     * @return
     */
    public int jCourant(){
        return jCourant;
    }

    /**
     *
     * @return
     */
    public int difficulte(){
        return difficulte;
    }
    
    /**
     *
     * @param e
     */
    public void setEtat(int e){
        etat = e;
    }

    /**
     *
     * @return
     */
    public int etat(){
        return etat;
    }

    /**
     *
     * @param t
     */
    public void maj(long t){
        if(Interface.pointeur().event()!=null){
        boolean b = this.accept(Interface.pointeur());
        if(b)
            plateau.clearAide();
        Interface.pointeur().traiter();
        }
        long nouv = t-temps;
        temps=t;
        switch(etat){
            case ATTENTE_COUP:
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
    
    /**
     *
     * @param ins
     */
    public void initDepot(int ins){
        initDepot=ins;
        plateau.clearAide();
        dispo(ins);
    }

    /**
     *
     * @param ins
     */
    public void initDeplacement(Insecte ins){
        initDepl = ins.clone();
        plateau.clearAide();
        dispo(ins);
    }

    /**
     *
     * @param ins
     */
    public void dispo(int ins){
        Coup[] c = depotPossible(jCourant, ins);
        List<Case> l = new ArrayList();
        for(int i=0; i<c.length; i++){
            Case c2 = new Case(c[i].destination().x(), c[i].destination().y(), 1, 1);
            c2.jouable();
            l.add(c2);
        }
        plateau.setAide(l);
    }
    
    /**
     *
     * @return
     */
    public Insecte initDeplacement(){
        return initDepl;
    }

    /**
     *
     * @return
     */
    public int initDepot(){
        return initDepot;
    }
    
    /**
     *
     * @param ins
     */
    public void dispo(Insecte ins){
        Coup[] c = deplacementPossible(ins);
        List<Case> l = new ArrayList();
        System.out.println("Caca devans la porte" +c);
            if (c != null)
                for (Coup c1 : c) {
                System.out.println("Plein de petits cacas");
                Case c2 = new Case(c1.destination().x(), c1.destination().y(), 1, 1);
                c2.jouable();
                l.add(c2);
            }
        plateau.setAide(l);
    }

    /**
     *
     */
    public void go(){
        etat = ATTENTE_COUP;
    }
    /*
    @Override
    public abstract Arbitre clone();*/
}
