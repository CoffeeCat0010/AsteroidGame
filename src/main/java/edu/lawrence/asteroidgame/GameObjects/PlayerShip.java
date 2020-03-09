/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.lawrence.asteroidgame.GameObjects;

import edu.lawrence.asteroidgame.GameConsts;
import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
/**
 *
 * @author Justin
 */
public class PlayerShip implements GameConsts{
    private Polygon ship;
    private ArrayList<LineSegment> walls;
    private double x, y;
    
    public PlayerShip(){
        walls = new ArrayList<LineSegment>();
        walls.add(new LineSegment(new Point(x-10,y), new Point(x+10,y)));
        walls.add(new LineSegment(new Point(x+10,y), new Point(x,y-30)));
        walls.add(new LineSegment(new Point(x,y-30), new Point(x-10,y)));
        this.x = WIDTH/2;
        this.y = HEIGHT;
        ship = new Polygon(x-10,y,x+10,y,x,y-30);
        ship.setFill(Color.BLUE);
    }
    public void update(boolean left){
        if(left && this.x > WIDTH/6) {
            this.x = this.x - WIDTH/3;
            
            for(int i=0;i<walls.size();i++)
            walls.get(i).move(-WIDTH/3,0);
        
        } else if(!left && this.x < (5*WIDTH)/6) {
            this.x = this.x + WIDTH/3;
            
            for(int i=0;i<walls.size();i++)
            walls.get(i).move(-WIDTH/3,0);
        }
    }
    
    public void draw(){
        ship.setTranslateX(x-WIDTH/2);
    }
    
    public Polygon getShip() {
        return ship;
    }
    
    public boolean collision(Ray in, double time) {
        Point intersect = null;
        for(int i=0;i<walls.size();i++) {
            LineSegment seg = in.toSegment(time);
            intersect = walls.get(i).intersection(seg);
            if(intersect != null) {
                return true;
            }
        }
        return false;
    }
    
    public boolean contains(Point p) {
        if(p.x >= x - 10 && p.x <= x + 10 && p.y >= y - 30 && p.y <= y)
            return true;
        return false;
    }
}
