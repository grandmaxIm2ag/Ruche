/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import ruche.Reglage;

/**
 *
 * @author grandmax
 */
public class FabriqueInsecte {
    
    public static Insecte creer(int type, int joueur, Point pos){
        switch(type){
            case Insecte.SCAR:
                return new Scarabee(pos.x(), pos.y(), (int)Reglage.lis("lInsecte"), (int)Reglage.lis("hInsecte"), joueur);
            case Insecte.REINE:
                return new Reine(pos.x(), pos.y(), /*(int)Reglage.lis("lInsecte"),*/ (int)Reglage.lis("hInsecte"), joueur);
            case Insecte.FOUR:
                return new Fourmie(pos.x(), pos.y(), (int)Reglage.lis("lInsecte"), (int)Reglage.lis("hInsecte"), joueur);
            case Insecte.CLOP:
                return new Cloporte(pos.x(), pos.y(), (int)Reglage.lis("lInsecte"), (int)Reglage.lis("hInsecte"), joueur);
            case Insecte.SAUT:
                return new Sauterelle(pos.x(), pos.y(), (int)Reglage.lis("lInsecte"), (int)Reglage.lis("hInsecte"), joueur);
            case Insecte.MOUS:
               return new Moustique(pos.x(), pos.y(), (int)Reglage.lis("lInsecte"), (int)Reglage.lis("hInsecte"), joueur);
            case Insecte.COCC:
                return new Coccinelle(pos.x(), pos.y(), (int)Reglage.lis("lInsecte"), (int)Reglage.lis("hInsecte"), joueur);
            case Insecte.ARAI:
                return new Araignee(pos.x(), pos.y(), (int)Reglage.lis("lInsecte"), (int)Reglage.lis("hInsecte"), joueur);
            default:
                return null;
        }
    }

    public static Insecte creer(int i, int i0, int i1, Point position) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
