/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.lawrence.asteroidgame.GameObjects;

import edu.lawrence.asteroidgame.GameConsts;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
/**
 *
 * @author Justin
 */
public class PlayerShip implements GameConsts{
    private Polygon ship;
    
    private double x, y;
    
    public PlayerShip(){
        this.x = WIDTH/2;
        this.y = HEIGHT;
        ship = new Polygon(x-10,y,x+10,y,x,y-30);
        ship.setFill(Color.BLUE);
    }
    public void update(boolean left){
        if(left && this.x > WIDTH/6)
            this.x = this.x - WIDTH/3;
        else if(!left && this.x < (5*WIDTH)/6)
            this.x = this.x + WIDTH/3;
    }
    
    public void draw(){
        ship.setTranslateX(x-WIDTH/2);
    }
    
    public Polygon getShip() {
        return ship;
    }
}
