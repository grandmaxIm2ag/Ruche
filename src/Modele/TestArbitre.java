/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import Joueurs.Humain;
import Joueurs.Joueur;
import java.util.Properties;

/**
 *
 * @author brignone
 */
public class TestArbitre extends Arbitre {
    
    public TestArbitre() {
        super(null);
        plateau = new Plateau (0, 0, 10,10, null);
        joueurs = new Joueur[2];
        joueurs[0] = new Humain(true, null);
        joueurs[1] = new Humain(false, null);
        for (int i = Insecte.REINE; i <= Insecte.CLOP; i++)
            joueurs[0].addPion(i, 10);
        for (int i = Insecte.REINE; i <= Insecte.COCC; i++)
            joueurs[1].addPion(i, 10);
        plateau.premierPion(new Reine(0,0,1,0));
        plateau.addPion();
    }
    
}
