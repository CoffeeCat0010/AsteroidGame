/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.lawrence.asteroidgame.GameObjects;

import javafx.scene.shape.Circle;

/**
 *
 * @author Justin
 */
public class Asteroid {
    private Circle asteroidShape;
    private double x, y;
    
    public Asteroid(double radius, double x, double y){
        asteroidShape = new Circle(radius);
        update(x, y);
    }
    
    public void draw(){
        asteroidShape.setCenterX(x);
        asteroidShape.setCenterY(y);
    }
    
    public void update(double x, double y){
        this.x = x;
        this.y = y;
    }
    
}
