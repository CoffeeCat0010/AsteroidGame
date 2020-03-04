/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.lawrence.asteroidgame.Network;

/**
 *
 * @author Justin
 */
public interface NetworkConsts {
    //messages to be recieved
    public static final int UPDATE_OBJECT   = 1;
    public static final int CREATE_OBJECT   = 2;
    public static final int DELETE_OBJECT   = 3;
    public static final int UPDATE_PROGRESS = 4;
    //messages to be sent
    public static final int UPDATE_PLAYER   = 5;
}
