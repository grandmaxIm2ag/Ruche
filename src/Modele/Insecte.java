/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author grandmax
 */
public abstract class Insecte extends Composant{
    public final static int SCAR = 0;
    public final static int REINE = 1;
    public final static int SAUT = 2;
    public final static int FOUR = 3;
    public final static int ARAI = 4;;
    public final static int COCC = 5;
    public final static int MOUS = 6;
    public final static int CLOP = 7;
    
    int type;
    int joueur;
    
    public Insecte(double x, double y, double larg, double haut, int j) {
        super(x, y, larg, haut);
        joueur = j;
    }

    public int type(){
        return type;
    }
    public double[] vecteur(){
        double[] vecteur = new double[12];
        double cote = l/2;
        double angle;
        
        int j=0;
        for(int i=1; i<=6;i++){
            angle = 60*i+30;
            angle = Math.PI/180 * angle;
            vecteur[j++] = pos.x() + cote * Math.cos(angle);
            vecteur[j++] = pos.y() + cote * Math.sin(angle);
        }        
        return vecteur;
    }
    
    public abstract Coup[] deplacementValide(Case[][] plateau);
    
    public abstract Insecte clone();
    
    public int joueur(){
        return joueur;
    }
    
    public List<Coup> glisser(Case[][] plateau){
        List<Coup> c = new ArrayList();
        for(int i=(int)pos.x()-1; i<=(int)pos.x()+1;i++)
            for(int j=(int)pos.y()-1; j<=(int)pos.y()+1;i++)
                if(!((i==(int)pos.x()-1 && j==(int)pos.y()-1) || (i==(int)pos.x()+1 && j==(int)pos.y()+1) ))
                    if(!pos.equals(plateau[i][j].position())){
                        //int x1, y1, x2, y2;
                        int diffx = i - ((int)pos.x());
                        int diffy = j - ((int)pos.y());
                    
                        switch(diffx){
                            case -1:
                                if(diffy==0){
                                    if( ! (plateau[(int)pos.x()][(int)pos.y()-1].utilise() && plateau[(int)pos.x()-1][(int)pos.y()+1].utilise() ))
                                        c.add(new Deplacement(pos, new Point(i,j), joueur));
                                }else{
                                    if( ! (plateau[(int)pos.x()-1][(int)pos.y()].utilise() && plateau[(int)pos.x()][(int)pos.y()+1].utilise() ))
                                        c.add(new Deplacement(pos, new Point(i,j), joueur));
                                }
                                break;
                            case 0:
                                if (diffy==-1){
                                    if( ! (plateau[(int)pos.x()+1][(int)pos.y()-1].utilise() && plateau[(int)pos.x()-1][(int)pos.y()].utilise() ))
                                        c.add(new Deplacement(pos, new Point(i,j), joueur));
                                }
                                else{
                                    if( ! (plateau[(int)pos.x()+1][(int)pos.y()].utilise() && plateau[(int)pos.x()-1][(int)pos.y()+1].utilise() ))
                                        c.add(new Deplacement(pos, new Point(i,j), joueur));
                                }
                                break;
                            case 1:
                                if(diffy==-1){
                                     if( ! (plateau[(int)pos.x()][(int)pos.y()-1].utilise() && plateau[(int)pos.x()+1][(int)pos.y()].utilise() ))
                                        c.add(new Deplacement(pos, new Point(i,j), joueur));
                                }else{
                                     if( ! (plateau[(int)pos.x()+1][(int)pos.y()-1].utilise() && plateau[(int)pos.x()][(int)pos.y()+1].utilise() ))
                                        c.add(new Deplacement(pos, new Point(i,j), joueur));
                                }
                                break;
                            default:
                                break;
                        }
                        
                    }
                
                
        return c;
    }

    public List<Coup> monter(Case[][] plateau){
        List<Coup> c = new ArrayList();
        for(int i=(int)pos.x()-1; i<=(int)pos.x()+1;i++)
            for(int j=(int)pos.y()-1; j<=(int)pos.y()+1;i++)
                if(!((i==(int)pos.x()-1 && j==(int)pos.y()-1) || (i==(int)pos.x()+1 && j==(int)pos.y()+1) ))
                    if(!pos.equals(plateau[i][j].position()))
                        if(plateau[i][j].utilise())
                            c.add(new Deplacement(pos, new Point(i,j), joueur));
        return c;
    }
    public List<Coup> descendre(Case[][] plateau){
        List<Coup> c = new ArrayList();
        for(int i=(int)pos.x()-1; i<=(int)pos.x()+1;i++)
            for(int j=(int)pos.y()-1; j<=(int)pos.y()+1;i++)
                if(!((i==(int)pos.x()-1 && j==(int)pos.y()-1) || (i==(int)pos.x()+1 && j==(int)pos.y()+1) ))
                    if(!pos.equals(plateau[i][j].position()))
                        if(plateau[i][j].utilise())
                            c.add(new Deplacement(pos, new Point(i,j), joueur));
        return c;
    }
}
