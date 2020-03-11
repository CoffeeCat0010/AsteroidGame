package edu.lawrence.asteroidgame;

import edu.lawrence.networklib.GetProgressMessage;
import edu.lawrence.asteroidgame.GameObjects.GameState;
import edu.lawrence.asteroidgame.Network.Gateway;
import edu.lawrence.networklib.ProgressMessage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
/**
 *
 * @author alan
 */
public class GamePane extends Pane{
    private Gateway gateway;
    private GameState gamestate;
    private Boolean isStarted = false;
    private Boolean shouldClose = false;
    
    private Thread gameStateThread;
    private Thread gameScoreThread;
    public GamePane(Gateway gateway, GameState gamestate) {
        this.gateway = gateway;
        this.gamestate = gamestate;
        this.getChildren().add(gamestate.getProgress());
        this.getChildren().addAll(gamestate.getShapes());
        this.setOnKeyPressed(e->handleKey(e));
        this.setMinWidth(USE_PREF_SIZE);
        this.setMaxWidth(USE_PREF_SIZE);
        this.setPrefWidth(GameConsts.WIDTH);
        this.setMinHeight(USE_PREF_SIZE);
        this.setMaxHeight(USE_PREF_SIZE);
        this.setPrefHeight(GameConsts.HEIGHT);
        gameStateThread = new Thread(new UpdateGameState(this, gamestate));
        gameScoreThread = new Thread(new UpdateGameScore(this, gateway, gamestate));
        gameStateThread.start();
        gameScoreThread.start();
    }
    
    public void close(){
            shouldClose = true;
        try {
            gameStateThread.join();
            gameScoreThread.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
    private void handleKey(KeyEvent evt) {
        KeyCode code = evt.getCode();
        if(code == KeyCode.LEFT) {
            gamestate.movePlayer(true);
        } else if(code == KeyCode.RIGHT) {
            gamestate.movePlayer(false);
        }
    }
    
    @Override
    public boolean isResizable() {
        return false; 
    }

    public Boolean getIsStarted() {
        return isStarted;
    }

    public void setIsStarted(Boolean isStarted) {
        this.isStarted = isStarted;
    }

    public Boolean shouldClose() {
        return shouldClose;
    }
    
    
}

class UpdateGameScore implements Runnable {
    private Gateway gateway;
    private GameState gameState;
    private GamePane pane;
    
    public UpdateGameScore(GamePane pane, Gateway gateway, GameState gameState) {
        this.gateway = gateway;
        this.gameState = gameState;
        this.pane = pane;
    }
    
    public void run() {
        while(!pane.shouldClose()) {
            try {
                
                Thread.sleep(250);
                if(gateway.isOpen()){
                    if(gameState.isStarted()){
                    gateway.pushMessage(new GetProgressMessage(1));
                    gateway.pushMessage(new ProgressMessage(gameState.getScore()));
                    }
                    gateway.refresh();
                }
                else
                    break;
                
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
class UpdateGameState implements Runnable {
    private GameState gamestate;
    private GamePane pane;
    
    public UpdateGameState(GamePane pane,GameState gamestate) {
        this.gamestate = gamestate;
        this.pane = pane;
    }
    
    public void run() {
        double ns = 1000000000.0 / 60.0;
        double delta = 0;

        long lastTime = System.nanoTime();

        while (!pane.shouldClose()) {
        long now = System.nanoTime();
        delta += (now - lastTime) / ns;
        lastTime = now;
            while (delta >= 1 && gamestate.isStarted()) {
                gamestate.update();
                delta--;
            }
        }   
    }
}
