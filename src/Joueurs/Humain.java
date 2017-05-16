/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Joueurs;

import java.util.Properties;

/**
 * <b>Humain est la classe représentant un joueur réel </b>
 * @author UGA L3 Projet Logiciel 2016-2017 groupe 7
 */
public class Humain extends Joueur{

    /**Constructeur
     * Utilise le constructeur de la classe Joueur
     * @param m true ssi c'est le tour du joueur
     * @param p propriétés de la partie
     * @param tabP tableau des pièces que le joueur n'a pas posé
     * @param j indice du joueur (0 ou 1)
     */
    public Humain(boolean m, Properties p, int[] tabP, int j) {
        super(m, p, tabP, j);
    }
    
}
