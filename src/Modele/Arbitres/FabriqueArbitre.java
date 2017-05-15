/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele.Arbitres;

import Joueurs.Ordinateur;
import java.util.Properties;

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
    public final static int LOCAL_JVJ = 0;
    /**
    * La valeur de cette constante est {@value}.
    */
    public final static int LOCAL_JVIA = 1;
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
    Properties prop;
    
    /**
     * La représentation du type d'arbitre qui va être favriquée, peut être modifié.
     * @see FabriqueArbitre#initType(int) 
     */ 
    private int type;
    /**
     * La représentation de la difficulté donné à l'arbitre qui va être favriquée, peut être modifié.
     * @see FabriqueArbitre#initDiff(int) 
     */
    private int difficulte;
    /**
     * Le nom de la sauvegarde donné à l'arbitre qui va être favriquée, peut être modifié.
     * @see FabriqueArbitre#initP(java.lang.String) 
     */
    private String plateau;
    /**
     * Les représentations textuelles des différentes difficulté, ne peut pas être modifié.
     */
    private final String[] diff;
    /**
     * Les représentations textuelles des différents types d'Arbitres, ne peut pas être modifié.
     */
    private final String[] types;
    /**
     * Les noms des différentes sauvegardes, ne peut pas être modifié.
     */
    private String[] plateaux;
    
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
    public FabriqueArbitre(Properties p){
        this.prop = p;
        
        this.difficulte = Ordinateur.FACILE_HEURISTIQUE;
        this.diff = new String[4];
        this.diff[Ordinateur.FACILE_ALEATOIRE] = "Très Facile";
        this.diff[Ordinateur.FACILE_HEURISTIQUE] = "Facile";
        this.diff[Ordinateur.MOYEN] = "Normal";
        this.diff[Ordinateur.DIFFICILE] = "Difficile";
        
        this.type = SIMULATION;
        this.types = new String[4];
        this.types[LOCAL_JVJ] = "Joueur vs Joueur";
        this.types[LOCAL_JVIA] = "Joueur vs IA";
    }
    
    /**
     * Créer un nouvel Arbitre.
     * 
     * @return l'Arbitre fabriqué.
     *  
     */
    public Arbitre nouveau(){
        switch(type){
            case LOCAL_JVJ:
                return new Local(prop, type, difficulte);
            case LOCAL_JVIA:
                return new Local(prop, type, difficulte);
            case SIMULATION:
                return new SimulationIA(prop, difficulte);
            case RESEAU_CLIENT:
                return new ReseauClient(prop);
            case RESEAU_SERVER:
                return new ReseauServer(prop);
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
    public void initType(int t){
        type = t;
    }

    /**
     *Setter de l'instance difficulte
     * 
     * @param t la nouvelle valeur de l'instance difficulte
     *
     * @see FabriqueArbitre#difficulte
     */
    public void initDiff(int t){
        difficulte = t;
    }

    /**
     *Setter de l'instance plateau
     * 
     * @param p la nouvelle valeur de l'instance plateau
     * 
     * @see FabriqueArbitre#plateau
     */
    public void initP(String p){
        plateau=p;
    }
    
    /**
     *Getter de l'instance types
     * 
     * @return un tableau de toutes les représentations textuelles des différents types d'Arbitre
     * 
     * @see FabriqueArbitre#types
     */
    public String[] types(){
        return types;
    }

    /**
     * Getter de l'instance difficultes
     * 
     * @return un tableau de toutes les représentations textuelles des difficultés
     * 
     * @see FabriqueArbitre#diff
     */
    public String[] difficultes(){
        return diff;
    }

    /**
     *Getter de l'instance plateaux.L
     * 
     * @return un tableau de toutes les noms des sauvegardes
     * 
     * @see FabriqueArbitre#plateaux
     */
    public String[] plateaux(){
        return plateaux;
    }
    
}
