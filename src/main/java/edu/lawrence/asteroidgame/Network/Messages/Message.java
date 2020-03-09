/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.lawrence.asteroidgame.Network.Messages;

import edu.lawrence.asteroidgame.Network.NetworkConsts;
import java.io.Serializable;

/**
 *
 * @author Justin
 */
public class Message implements NetworkConsts, Serializable{
    private final int messageType;
    
    protected Message(int messageType){
        this.messageType = messageType;
    }

    public int getMessageType() {
        return messageType;
    }
}
