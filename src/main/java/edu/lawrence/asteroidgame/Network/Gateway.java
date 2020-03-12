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
import javafx.application.Platform;
import javafx.scene.control.Label;

/**
 *
 * @author Justin
 */
public class Gateway implements GameConsts{
    private ObjectInputStream OIS;
    private ObjectOutputStream OOS;
    private GameState gameState;
    private ArrayList<Message> messageQueue;
    private int score2;
    private boolean isOpen = true;
    
    private boolean isGameOver = false;
    private Label progress2;
    private Label gameOver;
    
    private ReadWriteLock lock;
    
    public Gateway(GameState gameState){
        this.gameState = gameState;
        messageQueue = new ArrayList<Message>();
        
        progress2 = new Label();
        progress2.setLayoutX(WIDTH-80);
        progress2.setLayoutY(10);
        gameOver = new Label();
        gameOver.setLayoutX(WIDTH/2-80);
        gameOver.setLayoutY(30);
        
        lock = new ReentrantReadWriteLock();
        try{
            Socket socket = new Socket("143.44.68.188", 8000);
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
                    /*case NetworkConsts.GAME_END:
                        Platform.runLater(() -> {
                            if(true) {
                                gameOver.setText("YOU WIN");
                            }else {
                                gameOver.setText("YOU LOSE");
                            }
                        });
                        isGameOver = true;
                    */
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
    
    public Label getProgress2() {
        return progress2;
    }
    
    public Label getGameOver() {
        return gameOver;
    }
    
    public boolean isGameOver() {
        return isGameOver();
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
