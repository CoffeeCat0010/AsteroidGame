/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.lawrence.asteroidgame.Network;

import edu.lawrence.asteroidgame.GameConsts;
import edu.lawrence.asteroidgame.GameObjects.GameState;
import edu.lawrence.networklib.Message;
import edu.lawrence.networklib.NetworkConsts;
import edu.lawrence.networklib.ProgressMessage;
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
public class Gateway implements GameConsts{
    private ObjectInputStream OIS;
    private ObjectOutputStream OOS;
    private Socket socket;
    private GameState gameState;
    private ArrayList<Message> messageQueue;
    private int score2;
    private boolean isOpen = true;
    
    private boolean isWinner = false;
    private boolean isGameOver = false;
    
    private ReadWriteLock lock;
    private ReadWriteLock gameOverLock;
    
    public Gateway(GameState gameState){
        this.gameState = gameState;
        
        lock = new ReentrantReadWriteLock();
        gameOverLock = new ReentrantReadWriteLock();
        
        messageQueue = new ArrayList<Message>();    
        
        try{
            socket = new Socket("127.0.0.1", 8000);
            OIS = new ObjectInputStream(socket.getInputStream());
            OOS = new ObjectOutputStream(socket.getOutputStream());
            score2 = 0;
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
                        score2 = ((ProgressMessage)m).getUpdatedProgress();
                        System.out.println(score2); break;
                    case NetworkConsts.START:
                        gameState.setStarted(true); break;
                    case NetworkConsts.WINNER:
                        isWinner = true;
                        isGameOver = true;
                        break;
                    case NetworkConsts.LOSER:
                        isWinner = false;
                        isGameOver = true;
                        break;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    } 
    
    public int getScore2() {
        return score2;
    }

    public boolean isWinner() {
        boolean result;
        gameOverLock.readLock().lock();
        result = isWinner;
        gameOverLock.readLock().unlock();
        return result;
    }
    public boolean isGameOver() {
        boolean result;
        gameOverLock.readLock().lock();
        result = isGameOver;
        gameOverLock.readLock().unlock();
        return result;
    }
    
    public boolean isOpen(){
        return isOpen;
    }
    
    public void close(){
        try{
            OIS.close();
            OOS.close();
            socket.close();
        }catch(IOException IOE){
            IOE.printStackTrace();
        }
        isOpen = false;
    }
}
