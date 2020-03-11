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
public class ProgressMessage extends Message {
    public final int updatedProgress;
    public ProgressMessage(int progress){
        super(NetworkConsts.UPDATED_PROGRESS);
        this.updatedProgress = progress;
    }

    public int getUpdatedProgress() {
        return updatedProgress;
    }
    
}
