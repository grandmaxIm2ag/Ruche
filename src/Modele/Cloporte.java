/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

/**
 *
 * @author grandmax
 */
public class Cloporte extends Insecte{

   public Cloporte(double x, double y, double larg, double haut, int j) {
        super(x, y, larg, haut,j);
    }

    @Override
    public Coup[] deplacementValide(Case[][] plateau) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean equals(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean accept(Visiteur v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Insecte clone() {
        return new Cloporte(pos.x(), pos.y(), l, h, joueur);
    }
    
}
