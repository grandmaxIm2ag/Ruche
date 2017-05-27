/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele.Arbitres;

import Controleur.Choix;
import Joueurs.Ordinateur;
import Modele.Chargeur;
import Modele.Insecte;
import Vue.Interface;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;
import ruche.Configuration;
import ruche.Reglage;

/**
 *<b>FabriqueArbitre est la classe représentant le pattern Fabrique pour les Arbitres </b>
 * 
 * 
 * 
 * @author UGA L3 Projet Logiciel 2016-2017 groupe 7
 */
public class FabriqueArbitre {
    /**
    * La valeur de cette constante est {@value}.
    */
    public final static int LOCAL_JVJ = 1;
    /**
    * La valeur de cette constante est {@value}.
    */
    public final static int LOCAL_JVIA = 0;
    /**
    * La valeur de cette constante est {@value}.
    */
    public final static int LOCAL_IAVJ = 42;
    /**
    * La valeur de cette constante est {@value}.
    */
    public final static int SIMULATION = 2;
    /**
    * La valeur de cette constante est {@value}.
    */
    public final static int RESEAU_CLIENT = 3;
    /**
    * La valeur de cette constante est {@value}.
    */
    public final static int RESEAU_SERVER = 4;
    
    /**
     * Les propriétés de la partie.
     */
    static Properties prop;
    
    /**
     * La représentation du type d'arbitre qui va être favriquée, peut être modifié.
     * @see FabriqueArbitre#initType(int) 
     */ 
    private static int type;
    /**
     * La représentation de la difficulté donné à l'arbitre qui va être favriquée, peut être modifié.
     * @see FabriqueArbitre#initDiff(int) 
     */
    private static int difficulte;
    private static int difficulte2;
    /**
     * Le nom de la sauvegarde donné à l'arbitre qui va être favriquée, peut être modifié.
     * @see FabriqueArbitre#initP(java.lang.String) 
     */
    private static String plateau;
    /**
     * Les représentations textuelles des différentes difficulté, ne peut pas être modifié.
     */
    private static String[] diff;
    /**
     * Les représentations textuelles des différents types d'Arbitres, ne peut pas être modifié.
     */
    private static String[] types;
    /**
     * Les noms des différentes sauvegardes, ne peut pas être modifié.
     */
    private static String[] plateaux;
    
    private static String nom1, nom2, ip;
    
    private static boolean cocc;
    private static boolean clop;
    private static boolean mous;
    /**
     * Constructeur FabriqueArbitre
     * <p>
     * A la construction d'un objet FabriqueArbitre, l'instance "difficulte" est fixé à FabriqueArbitre.FACILE_HEURISTIQUE, ce qui correspond à la 
     * difficulté facile d'une Intelligence Artificielle, l'instance "type" est fixé à FabriqueArbitre.SIMULATION, ce qui correspond à une
     * partie où deux Intelligence Artificielle sont simulées.
     *</p>
     * @param p 
     *          Les propriétés de la partie.
     * 
     * @see FabriqueArbitre#difficulte
     * @see FabriqueArbitre#type
     */
    public static void init(Properties p){
        prop = p;
        
        difficulte = Ordinateur.MOYEN;
        diff = new String[4];
        diff[Ordinateur.FACILE_ALEATOIRE] = "Très Facile";
        diff[Ordinateur.FACILE_HEURISTIQUE] = "Facile";
        diff[Ordinateur.MOYEN] = "Normal";
        diff[Ordinateur.DIFFICILE] = "Difficile";
        Scanner fr = null;
        try{
            fr =new Scanner(new FileInputStream("Sauvegardes/Sauvegarde"));
        }catch(FileNotFoundException e){
            
        }
        if(fr!=null && fr.hasNext()){
            String str = fr.nextLine();
            if(str == null || str.equals("")){
                plateaux = new String[1];
                plateaux[0] = "(none)";
            }else{
                plateaux = str.split(":");
            }
        }else{
            plateaux = new String[0];
        }
        
        //System.err.println(Arrays.toString(plateaux));
        
        type = SIMULATION;
        types = new String[5];
        types[LOCAL_JVJ] = "Joueur vs Joueur";
        types[LOCAL_JVIA] = "Joueur vs IA";
        types[SIMULATION] = "Simulation";
        types[RESEAU_SERVER] = "Créer une partie en ligne";
        types[RESEAU_CLIENT] = "Réjoindre un Hôte";
        
        cocc=false;
        clop=false;
        mous=false;
        
        nom1 = "Joueur1";
        nom2="Joueur2";
        ip = "127.0.0.1";
        
    }
    
    public static  void init(){
        init(prop);
    }
    /**
     * Créer un nouvel Arbitre.
     * 
     * @return l'Arbitre fabriqué.
     *  
     */
    public static Arbitre nouveau(){
        System.out.println(plateau+" "+Arrays.toString(plateaux) );
        boolean b = plateau != null && !plateau.equals("(none)");
        initConf();
        switch(type){
            case LOCAL_JVJ:
                if(b)
                    return new Local(prop, type, difficulte, plateau,nom1,nom2);
                return new Local(prop, type, difficulte,nom1,nom2);
            case LOCAL_JVIA:
                if(b)
                    return new Local(prop, type, difficulte | difficulte2, plateau,nom1,"Ordinateur");
                return new Local(prop, type, difficulte | difficulte2,nom1,"Ordinateur");
           case LOCAL_IAVJ:
                if(b)
                    return new Local(prop, type, difficulte | difficulte2, plateau,nom1,"Ordinateur");
                return new Local(prop, type, difficulte | difficulte2,nom1,"Ordinateur");
            case SIMULATION:
                return new SimulationIA(prop, difficulte, difficulte2,nom1,nom2);
            case RESEAU_CLIENT:
                return new ReseauClient(prop,nom1,"",ip);
            case RESEAU_SERVER:
                return new ReseauServer(prop,nom1,nom2);
            default:
                return null;
        }
    }
    
    /**
     * Setter  de l'instance type
     * 
     * @param t la nouvelle valeur de linstance type
     * 
     * @see FabriqueArbitre#type
     */
    public static void initType(int t){
        type = t;
    }

    /**
     *Setter de l'instance difficulte
     * 
     * @param t la nouvelle valeur de l'instance difficulte
     *
     * @see FabriqueArbitre#difficulte
     */
    public static void initDiff(int t){
        difficulte = t;
    }
    
    public static void initDiff2(int t){
        difficulte2 = t;
    }

    /**
     *Setter de l'instance plateau
     * 
     * @param p la nouvelle valeur de l'instance plateau
     * 
     * @see FabriqueArbitre#plateau
     */
    public static void initP(String p){
        
        plateau=p;
    }
    
    public static void initN1(String p){
        System.err.println("Passé");
        nom1=p;
    }
    
    public static void initN2(String p){
        System.err.println("Passé");
        nom2=p;
    }
    /**
     *Getter de l'instance types
     * 
     * @return un tableau de toutes les représentations textuelles des différents types d'Arbitre
     * 
     * @see FabriqueArbitre#types
     */
    public static String[] types(){
        return types;
    }

    /**
     * Getter de l'instance difficultes
     * 
     * @return un tableau de toutes les représentations textuelles des difficultés
     * 
     * @see FabriqueArbitre#diff
     */
    public static String[] difficultes(){
        return diff;
    }

    /**
     *Getter de l'instance plateaux.L
     * 
     * @return un tableau de toutes les noms des sauvegardes
     * 
     * @see FabriqueArbitre#plateaux
     */
    public static String[] plateaux(){
        return plateaux;
    }
    
    public static void setInit(int c,int i){
        
        switch(c){
            case Choix.CHOIX_MODE:
                initType(i);
                break;
            case Choix.CHOIX_DIFFICULTE:
                initDiff(i);
                break;
            case Choix.CHOIX_PLATEAU:
                initP(plateaux[i]);
                break;
            default:
                break;
        }
        System.err.println(Arrays.toString(plateaux)+" "+plateau+" "+i+" "+c);
    }

    
    public static void initConf(){
        if(cocc && mous && clop){
            Configuration.chargerProprietes(prop, ClassLoader.getSystemClassLoader().getResourceAsStream("Reglages/conf213.cfg"));
            Reglage.init(prop);
        }else if(cocc && mous){
            Configuration.chargerProprietes(prop, ClassLoader.getSystemClassLoader().getResourceAsStream("Reglages/conf32.cfg"));
            Reglage.init(prop);
        }else if(cocc && clop){
            Configuration.chargerProprietes(prop, ClassLoader.getSystemClassLoader().getResourceAsStream("Reglages/conf21.cfg"));
            Reglage.init(prop);
        }else if(mous && clop){
            Configuration.chargerProprietes(prop, ClassLoader.getSystemClassLoader().getResourceAsStream("Reglages/conf31.cfg"));
            Reglage.init(prop);
        }else if(cocc){
            Configuration.chargerProprietes(prop, ClassLoader.getSystemClassLoader().getResourceAsStream("Reglages/conf2.cfg"));
            Reglage.init(prop);
        }else if(mous){
            Configuration.chargerProprietes(prop, ClassLoader.getSystemClassLoader().getResourceAsStream("Reglages/conf3.cfg"));
            Reglage.init(prop);
        }else if(clop){
            Configuration.chargerProprietes(prop, ClassLoader.getSystemClassLoader().getResourceAsStream("Reglages/conf1.cfg"));
            Reglage.init(prop);
        }else{
            Configuration.chargerProprietes(prop, ClassLoader.getSystemClassLoader().getResourceAsStream("Reglages/defaut.cfg"));
            Reglage.init(prop);
        }
    }
    
    public static int type(){
        return type;
    }
    
    public static void initIP(String p){
        ip = p;
    }
    
    public static void initChargeur(){
        Chargeur.init(prop);
    }

    public static void setBonus(int ins, boolean b){
        switch(ins){
            case Insecte.CLOP:
                clop=b;
                break;
            case Insecte.MOUS:
                mous=b;
                break;
            case Insecte.COCC:
                cocc=b;
                break;
        }
    }
}
