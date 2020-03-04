/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.lawrence.asteroidgame.GameObjects;

import javafx.scene.shape.Polygon;
/**
 *
 * @author Justin
 */
public class PlayerShip {
    private static final double[] VERTICES = { 0.0,  0.0, 
                                              10.0,  0.0,
                                               5.0, 15.0};
    private Polygon ship;
    
    private double x, y;
    
    public PlayerShip(double x, double y){
        ship = new Polygon(VERTICES);
        
    }
    public void update(double x, double y){
        this.x = x;
        this.y = y;
    }
    
    public void draw(){
        ship.setTranslateX(x);
        ship.setTranslateY(y);
    }
}
