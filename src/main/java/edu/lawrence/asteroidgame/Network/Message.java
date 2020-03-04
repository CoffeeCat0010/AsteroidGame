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
public class Message {
    private final int messageType;
    
    private Message(int messageType){
        this.messageType = messageType;
    }

    public int getMessageType() {
        return messageType;
    }
}
