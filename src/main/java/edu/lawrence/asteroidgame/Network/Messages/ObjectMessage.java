/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.lawrence.asteroidgame.Network.Messages;

/**
 *
 * @author Justin
 */
public class ObjectMessage {
    private final int id;
    private final double updatedX, updatedY;
    
    
    public ObjectMessage(int id, double x, double y){
            this.id  = id;
            updatedX = x;
            updatedY = y;
    }

    public int getId() {
        return id;
    }

    public double getX() {
        return updatedX;
    }

    public double getY() {
        return updatedY;
    }
    
}
