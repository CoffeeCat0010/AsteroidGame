/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.lawrence.asteroidgame.Network;

import edu.lawrence.asteroidgame.Network.Messages.GetProgressMessage;
import edu.lawrence.asteroidgame.Network.Messages.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Justin
 */
public class Gateway {
    private ObjectInputStream OIS;
    private ObjectOutputStream OOS;
    private int progress;
    private boolean isOpen = true;
    
    public Gateway(){
        try{
            Socket socket = new Socket("localhost", 8000);
            OIS = new ObjectInputStream(socket.getInputStream());
            OOS = new ObjectOutputStream(socket.getOutputStream());
            progress = 0;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public synchronized void refresh(){
        Message[] messages = {new GetProgressMessage(1), new GetProgressMessage(2)};
        try {
            OOS.writeObject(messages);
            OOS.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    } 
        public void close(){
            try{
                OIS.close();
                OOS.close();
            }catch(IOException IOE){
                
            }
            isOpen = false;
        }
}
