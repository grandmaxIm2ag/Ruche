/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele.Arbitres;

import Joueurs.Joueur;
import Joueurs.Ordinateur;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;
import javafx.scene.input.MouseEvent;

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
    
    final static int INITIALISATION = -1;
    final static int ATTENTE_COUP = 0;
    final static int JOUE_EN_COURS = 1;
    final static int A_JOUER = 2;
    public final static int FIN = 3;
    final static int AIDE = 4;
    final static int PAUSE = 5;
    
    boolean pause;
    int precEtat;
    
    public final static int GAGNE = 0;
    public final static int PERDU = 1;
    public final static int NUL = 2;
    
    int etat;
    boolean aide;
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
    Plateau plateauAide;
    String pla;
    Chargeur chargeur;
    Stack<Coup> historique;
    Stack<Coup> refaire;
    
    String nom1, nom2;
    
    int[] nbCoup;
    
    Coup[] deplacements;
    Coup[] depots;
    Coup[] coups;
    boolean aucun;
    boolean chargement = false;
    boolean precAucun;
    
    Insecte initDepl;
    Insecte initClopDepl;
    int initDepot;
    
    HashMap<Integer, Integer> configurations;
    
    /**
     *
     * @param p
     */
    public Arbitre(Properties p, String n1, String n2){
        Reglage.init(p);
        pause = false;
        prop = p;
        joueurs = new Joueur[2];
        jCourant = J1;
        historique = new Stack();
        refaire = new Stack();
        plateau = new Plateau(0,0,Reglage.lis("lPlateau")*Reglage.lis("nbPiece"),Reglage.lis("hPlateau")*Reglage.lis("nbPiece"),p);
        
        chargeur = new Chargeur();
        
        nbCoup = new int[2];
        nbCoup[0]=0; nbCoup[1]=0;
        etat = ATTENTE_COUP;
        aucun = false;
        temps = System.nanoTime();
        temps_ecoule = 0;
        precAucun = false;
        
        nom1 = n1;
        nom2 = n2;
        
        configurations = new HashMap();
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
            if(l==null)
                return null;
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
        //else
            //System.err.println("Coup Inconnu "+d);
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
        chargement = false;
        init();
    }
    
    /**
     *
     */
    public void precedent(){
        if(!historique.isEmpty()){
            configurations.remove(configurations.size()-1);
            Coup c = historique.pop();
            //System.out.println(c+" "+(c==null));
            refaire.push(c);
            if(c instanceof Deplacement){
                Deplacement d = (Deplacement) c;
                plateau.deplacePion(new Deplacement(d.joueur(),d.destination(), d.source()));
                nbCoup[d.joueur()]--;
            }else if(c instanceof Depot){
                Depot d = (Depot) c;
                plateau.retirerPion(d.destination());
                nbCoup[d.joueur()]--;
//                joueurs[d.joueur()].pred(d.type());
            }
            PaneToken.getInstance(this).update();
            jCourant = (jCourant+1)%2;
        }else{
            System.err.println("Aucun coup précedent");
        }
        
        
    }

    /**
     *
     */
    public void refaire(){
        if(!refaire.isEmpty()){
            Coup c = refaire.pop();
            //System.out.println(c+" "+(c==null));
            historique.push(c);
            joue(c);
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
        Chargeur.charger(plateau, this.plateau);
        this.plateau.initLime();
        System.out.println("->"+this.plateau.xMin+" "+this.plateau.yMax+" "+this.plateau.yMin+" "+this.plateau.xMax);
        type = Chargeur.type();
        historique = Chargeur.historique();
        refaire = Chargeur.refaire();
        
        String[] str = Chargeur.joueur();
        joueurs[J1].nom = str[J1].split("=")[0];
        String[] str2 = str[J1].split("=")[1].split(":");
        int[] tab = new int[str2.length];
        for(int i=0; i<str2.length; i++)
            tab[i]=Integer.parseInt(str2[i]);
        joueurs[J1].setPieces(tab);
        joueurs[J2].nom = str[J2].split("=")[0];
        str2 = str[J2].split("=")[1].split(":");
        tab = new int[str2.length];
        for(int i=0; i<str2.length; i++){
            tab[i]=Integer.parseInt(str2[i]);
            System.out.println(str2[i]);
        }
        nbCoup = Chargeur.nbCourant();
        joueurs[J2].setPieces(tab);
        jCourant = Chargeur.jCourant();
        System.out.println("Ici : "+jCourant);
        chargement=false;
    }

    /**
     *
     * @param nomSauv
     */
    public void sauvegarder(String nomSauv){
        String sauv = "";
        System.out.println("passé");
        switch(type){
            case FabriqueArbitre.LOCAL_JVJ:
                sauv = type+"::"+joueurs[J1].nom()+"::"+joueurs[J2].nom()+"\n";
                break;
            case FabriqueArbitre.LOCAL_JVIA:
                sauv = type+"::"+joueurs[J1].nom()+"::"+difficulte+"\n";
                break;
            case FabriqueArbitre.LOCAL_IAVJ:
                sauv = type+"::"+difficulte+"::"+joueurs[J2].nom()+"\n";
                break;
            case FabriqueArbitre.SIMULATION:
                Ordinateur o2 = (Ordinateur)joueurs[J2];
                Ordinateur o1 = (Ordinateur)joueurs[J1];
//                sauv = type+"::"+o1.diff()+"::"+o2.diff()+"\n";
                break;
        }
        sauv += type+":"+nbCoup[J1]+":"+nbCoup[J1]+":"+jCourant+"\n";
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
            File f = new File("Sauvegardes/"+nomSauv);
            f.createNewFile();
            FileWriter output = new FileWriter(f);
            output.write(sauv);
            output.close();
        
        }catch(FileNotFoundException e){
            System.err.println("Impossible de sauvegarder, fichier introuvable "+nomSauv);
        }catch(IOException e){
            System.err.println("Impossible de sauvegarder "+nomSauv);
        }
        
        String str = "";
        
        try{
            Scanner fr =new Scanner(new FileInputStream("Sauvegardes/Sauvegarde"));
            if(fr.hasNext()){
                str = fr.nextLine();
                if(str == null || str.equals("")){
                    str = nomSauv;
                }else{
                    str += (":"+nomSauv);
                }
                fr.close();
            }else{
                str = nomSauv;
            }
            PrintWriter writer = new PrintWriter("Sauvegardes/Sauvegarde", "UTF-8");
            writer.print(str);
            writer.close();
        }catch(IOException e){
            System.err.println("Echec de la sauvegarde "+e);
        }
        
        FabriqueArbitre.initChargeur();
    }
    
    /**
     *
     */
    public void aide(){
        /*
        A faire dès que les IA seront opérationnels
        */
        etat = AIDE;
        temps_ecoule = 0;
        plateauAide = plateau.clone();
        plateau.setDepotAide(-1);
        Ordinateur o = new Ordinateur(true,Ordinateur.FACILE_HEURISTIQUE , prop, Arrays.copyOf(joueurs[jCourant].pions(), joueurs[jCourant].pions().length) ,  jCourant, nom2);
        
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
        //System.out.println(nbCoup[J1]+" "+nbCoup[J2]);
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
        
        Coup c = o.coup(this, coups);
        if(c instanceof Deplacement)
            plateauAide.deplacePion((Deplacement)c);
        else {
            plateauAide.deposePion((Depot)c);
            plateauAide.setDepotAide(((Depot) c).type());
        }
    }

    /**
     *
     * @param dessinateur
     */
    public boolean accept(Visiteur dessinateur) {
        if(!aide){
            
            return plateau.accept(dessinateur);
        }
        else{
            //System.err.println("Ici");
            return plateauAide.accept(dessinateur);
        }
           
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
        //System.gc();
        if(Interface.pointeur().event()!=null){
            boolean b = this.accept(Interface.pointeur());
            //System.out.println(b);
            if(b)
                plateau.clearAide();
                if((Interface.pointeur().event().getEventType() == MouseEvent.MOUSE_CLICKED) && (etat == AIDE)){
                    etat = ATTENTE_COUP;
                    plateau.setDepotAide(-1);
                    aide = false;
                }
            Interface.pointeur().traiter();
        }
        long nouv = t-temps;
        temps=t;
        switch(etat){
            case AIDE:
                temps_ecoule+=nouv;
                if(temps_ecoule>=1000000000){
                    temps_ecoule=0;
                    aide = !aide;
                }
                break;
            case ATTENTE_COUP:
                aide = false;
                break;
            case PAUSE:
                break;
            case JOUE_EN_COURS:
                temps_ecoule+=nouv;
                if(temps_ecoule>=100000000){
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
                Interface.fin();
                break;
        }
    }
    boolean nul(){
        boolean b = plateau.estEncerclee(J1)&&plateau.estEncerclee(J2);
        
        if(!b){
            if(historique.size()>=12){
                Coup[] tmp = new Coup[4];
                Stack<Coup> c = new Stack();
                
                for(int i=0; i<12; i++)
                    c.push(historique.pop());
                
                
                int i=0;
                while(!c.isEmpty()){
                    Coup c1 = c.pop();
                    historique.push(c1);
                    b &= tmp[i]==null || tmp[i].equals(c1);
                    if(c1 instanceof Depot){
                        Depot d = (Depot)c1;
                        tmp[i] = new Depot(d.joueur(), d.type(), d.destination());
                    }else{
                        Deplacement d = (Deplacement)c1;
                        tmp[i] = new Deplacement(d.joueur(), d.source(), d.destination());
                    }
                    i = ++i % 4;
                }
            }
        }
        
        return b;
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
        initClopDepl = null;
        Coup[] c = depotPossible(jCourant, ins);
        //System.out.println(jCourant+" "+ins+" "+Arrays.toString(c));
        List<Case> l = new ArrayList();
        for(int i=0; c!=null && i<c.length; i++){
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
        initClopDepl = null;
        Coup[] c = deplacementPossible(ins);
        List<Case> l = new ArrayList();
            if (c != null)
                for (Coup c1 : c) {
                Case c2 = new Case(c1.destination().x(), c1.destination().y(), 1, 1);
                c2.jouable();
                l.add(c2);
                plateau.setAide(l);
            }
        
    }
    
    public void initClopDepl (Insecte i) {
        initClopDepl = i;
    }
    
    public void reinitClopDepl () {
        initClopDepl = null;
    }
    
    public Insecte getInitClopDepl () {
        return initClopDepl;
    }
    
    public void reinitDepl () {
        initDepl = null;
        initClopDepl = null;
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
        etat = ATTENTE_COUP;
    }
    
    public Stack<Coup> historique(){
        return historique;
    }
    
    public int nbcoups(int j){
        return nbCoup[j];
    }
    /*
    @Override
    public abstract Arbitre clone();*/
    
    public void coupSouris(Deplacement d){
        Deplacement res;
        for(int i=0; i<coups.length; i++){
            if(coups[i] instanceof Deplacement){
                Deplacement tmp = (Deplacement) coups[i];
                if(tmp.equals(d)){
                    res = tmp.clone();
                    joue(res);
                    break;
                }
            }
        }
        
    }
    
    public void pause(){
        if(pause){
            etat = precEtat;
        }else{
            precEtat = etat;
            etat = PAUSE;
        }
        pause = !pause;
            
    }
}