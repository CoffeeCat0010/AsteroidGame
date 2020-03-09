/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.lawrence.asteroidgame.GameObjects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author Justin
 */
public class Asteroid {
    private Circle asteroidShape;
    private double x, y;
    private Ray r;
    
    public Asteroid(double radius, double x, double y){
        asteroidShape = new Circle(radius);
        asteroidShape.setFill(Color.BLUE);
        Vector v = new Vector(0,2);
        double speed = v.length();
        r = new Ray(new Point(x,y),v,speed);
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
    
    public Circle getAsteroid() {
        return asteroidShape;
    }
    
    public double getY() {
        return y;
    }
    
    public Ray getRay() {
        return r;
    }
    
    public void setRay(Ray r) {
        this.r = r;
    }
    
    public void move(double time) {
        this.x = r.endPoint(time).getX();
        this.y = r.endPoint(time).getY();
        r = new Ray(r.endPoint(time),r.v,r.speed);
    }
}
