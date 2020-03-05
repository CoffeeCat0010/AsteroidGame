/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.lawrence.asteroidgame.GameObjects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
/**
 *
 * @author Justin
 */
public class PlayerShip {
    private Polygon ship;
    
    private double x, y;
    
    public PlayerShip(double x, double y){
        ship = new Polygon(x-10,y,x+10,y,x,y-30);
        ship.setFill(Color.BLUE);
    }
    public void update(double x, double y){
        this.x = x;
        this.y = y;
    }
    
    public void draw(){
        ship.setTranslateX(x);
        ship.setTranslateY(y);
    }
    
    public Polygon getShip() {
        return ship;
    }
}
