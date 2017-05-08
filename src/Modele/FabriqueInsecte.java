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

    public static Insecte creer(String s) {
        String[] str = s.split("/");
        return creer(Integer.parseInt(str[0].substring(0)), Integer.parseInt(str[1]), new Point(str[2].substring(0, str[2].length()-1)));
    }
    
}
