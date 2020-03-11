/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.lawrence.networklib;

import java.io.Serializable;

/**
 *
 * @author Justin
 */
public class Message implements NetworkConsts, Serializable{
    private final int messageType;
    
    public Message(int messageType){
        this.messageType = messageType;
    }

    public int getMessageType() {
        return messageType;
    }
}
