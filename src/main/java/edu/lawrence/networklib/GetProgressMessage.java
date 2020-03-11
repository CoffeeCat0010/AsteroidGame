/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.lawrence.networklib;

/**
 *
 * @author Justin
 */
public class GetProgressMessage extends Message{
    private final int playerID;
    public GetProgressMessage(int playerID){
        super(NetworkConsts.GET_PROGRESS);
        this.playerID = playerID;
    }

    public int getPlayerID() {
        return playerID;
    }
    
}
