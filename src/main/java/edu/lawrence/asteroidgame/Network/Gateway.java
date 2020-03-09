/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.lawrence.asteroidgame.Network;

import edu.lawrence.asteroidgame.Network.Messages.GetProgressMessage;
import edu.lawrence.asteroidgame.Network.Messages.Message;
import edu.lawrence.asteroidgame.Network.Messages.ProgressMessage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * @author Justin
 */
public class Gateway {
    private ObjectInputStream OIS;
    private ObjectOutputStream OOS;
    private ArrayList<Message> messageQueue;
    private int progress;
    private boolean isOpen = true;
    private ReadWriteLock lock;
    
    public Gateway(){
        messageQueue = new ArrayList<Message>();
        lock = new ReentrantReadWriteLock();
        try{
            Socket socket = new Socket("143.44.68.130", 8000);
            OIS = new ObjectInputStream(socket.getInputStream());
            OOS = new ObjectOutputStream(socket.getOutputStream());
            progress = 0;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void pushMessage(Message message){
        lock.writeLock().lock();
        messageQueue.add(message);
        lock.writeLock().unlock();
    }
    
    
    public synchronized void refresh(){
        lock.readLock().lock();
        Message[] messages = messageQueue.toArray(new Message[0]);
        messageQueue.clear();
        lock.readLock().unlock();
        Message[] received = null;
        try {
            OOS.writeObject(messages);
            OOS.flush();
            
            received = (Message[]) OIS.readObject();
            for(Message m : received){
                switch(m.getMessageType()){
                    case NetworkConsts.UPDATED_PROGRESS:
                        int progress = ((ProgressMessage)m).getUpdatedProgress();
                        System.out.println(progress); break;
                }
            }   
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        
        
    } 
        public boolean isOpen(){
            return isOpen;
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
