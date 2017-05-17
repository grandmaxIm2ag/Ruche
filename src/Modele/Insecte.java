/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author grandmax
 */
public abstract class Insecte extends Composant{

    /**
     *
     */
    public final static int SCAR = 1;

    /**
     *
     */
    public final static int REINE = 0;

    /**
     *
     */
    public final static int SAUT = 2;

    /**
     *
     */
    public final static int FOUR = 3;

    /**
     *
     */
    public final static int ARAI = 4;

    /**
     *
     */
    public final static int COCC = 5;

    /**
     *
     */
    public final static int MOUS = 6;

    /**
     *
     */
    public final static int CLOP = 7;

    /**
     *
     */
    public final static int NB_TYPE = 8;
    
    /**
     *
     */
    public boolean EST_POINTE;
    
    int joueur;
    int classement;
    
    /**
     *
     * @param x
     * @param y
     * @param larg
     * @param haut
     * @param j
     */
    public Insecte(double x, double y, double larg, double haut, int j) {
        super(x, y, larg, haut);
        joueur = j;
        EST_POINTE = false;
        classement = 0;
    }
    
    /**
     *
     */
    public void pointe() {
        EST_POINTE = true;
    }
    
    /**
     *
     */
    public void depointe() {
        EST_POINTE = false;
    }
    
    /**
     *
     * @return
     */
    public boolean estpointe () {
        return EST_POINTE;
    }

    /**
     *
     * @param c
     */
    public void setClassement(int c ){
        classement = c;
    }

    /**
     *
     * @return
     */
    public int classement(){
        return classement;
    }

    /**
     *
     * @return
     */
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
    
    /**
     *
     * @return
     */
    public abstract int type();
    
    /**
     *
     * @param plateau
     * @return
     */
    public abstract Coup[] deplacementValide(Plateau p);
    
    @Override
    public abstract Insecte clone();
    
    /**
     *
     * @return
     */
    public int joueur(){
        return joueur;
    }
    
    /**
     *
     * @param plateau
     * @return
     */
    public List<Coup> glisser(Map<Point, Case> plateau){
        List<Coup> c = new ArrayList();
        int i1;
        if(pos.x()==0)
            i1=0;
        else
            i1=(int)pos.x()-1;
        
        int j1;
        if(pos.y()==0)
            j1=0;
        else
            j1=(int)pos.y()-1;
        
        for(int i=(int)pos.x()-1; i<=(int)pos.x()+1;i++)
            for(int j=(int)pos.y()-1; j<=(int)pos.y()+1 ;j++){
                if(!((i==(int)pos.x()-1 && j==(int)pos.y()-1) || (i==(int)pos.x()+1 && j==(int)pos.y()+1) ))
                    if(!pos.equals(new Point(i,j)) && plateau.get(new Point(i,j))==null){
                        //int x1, y1, x2, y2;
                        int diffx = i - ((int)pos.x());
                        int diffy = j - ((int)pos.y());
                        
                        
                        switch(diffx){
                            case -1:
                                if(diffy==0){
                                    if( plateau.get( new Point(pos.x(), pos.y()-1))==null ^ plateau.get( new Point(pos.x()-1, pos.y()+1))==null )
                                        c.add(new Deplacement(joueur, new Point(pos.x(), pos.y()), new Point(i,j)));
                                }else{
                                    if(plateau.get( new Point(pos.x()-1, pos.y()))==null ^ plateau.get( new Point(pos.x(), pos.y()+1))==null )
                                        c.add(new Deplacement(joueur, new Point(pos.x(), pos.y()), new Point(i,j)));
                                }
                                break;
                            case 0:
                                if (diffy==-1){
                                    if(plateau.get( new Point(pos.x()+1, pos.y()-1))==null ^ plateau.get( new Point(pos.x()-1, pos.y()))==null ){
                                        c.add(new Deplacement(joueur, new Point(pos.x(), pos.y()), new Point(i,j)));
                                    }
                                }
                                else{
                                    if(plateau.get( new Point(pos.x()+1, pos.y()))==null ^ plateau.get( new Point(pos.x()-1, pos.y()+1))==null )
                                        c.add(new Deplacement(joueur, new Point(pos.x(), pos.y()), new Point(i,j)));
                                }
                                break;
                            case 1:
                                if(diffy==-1){
                                     if( plateau.get( new Point(pos.x(), pos.y()-1))==null ^ plateau.get( new Point(pos.x()+1, pos.y()))==null )
                                        c.add(new Deplacement(joueur, new Point(pos.x(), pos.y()), new Point(i,j)));
                                }else{
                                     if( plateau.get( new Point(pos.x()+1, pos.y()-1))==null ^ plateau.get( new Point(pos.x(), pos.y()+1))==null)
                                        c.add(new Deplacement(joueur, new Point(pos.x(), pos.y()), new Point(i,j)));
                                }
                                break;
                            default:
                                break;
                        }
                        
                    }
            }
                
        return c;
    }

    /**
     *
     * @param plateau
     * @return
     */
    public List<Coup> monter(Map<Point, Case> plateau){
        List<Coup> c = new ArrayList();
        for(int i=(int)pos.x()-1; i<=(int)pos.x()+1;i++)
            for(int j=(int)pos.y()-1; j<=(int)pos.y()+1;j++)
                if(!((i==(int)pos.x()-1 && j==(int)pos.y()-1) || (i==(int)pos.x()+1 && j==(int)pos.y()+1) ))
                    if(!pos.equals(new Point(i,j)))
                        if(plateau.get(new Point(i,j))!=null)
                            c.add(new Deplacement(joueur, new Point(pos.x(), pos.y()), new Point(i,j)));
        return c;
    }

    /**
     *
     * @param plateau
     * @return
     */
    public List<Coup> descendre(Map<Point, Case> plateau){
        List<Coup> c = new ArrayList();
        for(int i=(int)pos.x()-1; i<=(int)pos.x()+1;i++)
            for(int j=(int)pos.y()-1; j<=(int)pos.y()+1;j++)
                if(!((i==(int)pos.x()-1 && j==(int)pos.y()-1) || (i==(int)pos.x()+1 && j==(int)pos.y()+1) ))
                    if(!pos.equals(new Point(i,j)))
                        if(plateau.get(new Point(i,j))==null)
                            c.add(new Deplacement(joueur, new Point(pos.x(), pos.y()), new Point(i,j)));
        return c;
    }
    
    @Override
    public String toString(){
        return "["+type()+"/"+joueur()+"/"+pos+"]";
    }
    
   
}
