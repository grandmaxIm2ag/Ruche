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
public class Point {
    double x, y;
    
    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }
    public Point(String m){
        String tmp[] = m.split(",");
        int x = (int)Double.parseDouble(tmp[0].substring(1));
        int y = (int)Double.parseDouble(tmp[1].substring(0, tmp[1].length()-1));
        this.x = x;
        this.y = y;
    }
    
    public void fixe(double x, double y){
        this.x = x; this.y = y;
    }
    
    public double x(){
        return x;
    }
    public double y(){
        return y;
    }
    
    public boolean equals(Object o){
        if(o instanceof Point){
            Point p = (Point) o;
            return (p.x()==x && p.y()==y);
        }
        return false;
    }
    
    public String toString(){
        return "("+x+","+y+")";
    }
    
    @Override
    public int hashCode()
    {
        return toString().hashCode();
    }
    
    public Point clone(){
        Point nouv = new Point(x(), y());
        return nouv;
    }
}
