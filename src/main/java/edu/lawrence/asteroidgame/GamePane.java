package edu.lawrence.asteroidgame;

import edu.lawrence.asteroidgame.GameObjects.GameState;
import edu.lawrence.asteroidgame.Network.Gateway;
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
    
    public GamePane(Gateway gateway, GameState gamestate) {
        this.gateway = gateway;
        this.gamestate = gamestate;
        this.getChildren().addAll(gamestate.getShapes());
        this.setOnKeyPressed(e->handleKey(e));
        this.setMinWidth(USE_PREF_SIZE);
        this.setMaxWidth(USE_PREF_SIZE);
        this.setPrefWidth(GameConsts.WIDTH);
        this.setMinHeight(USE_PREF_SIZE);
        this.setMaxHeight(USE_PREF_SIZE);
        this.setPrefHeight(GameConsts.HEIGHT);
        new Thread(new UpdateGameState(gamestate)).start();
        new Thread(new UpdateGameScore(gateway)).start();
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
    
    
}

class UpdateGameScore implements Runnable {
    private Gateway gateway;
    
    public UpdateGameScore(Gateway gateway) {
        this.gateway = gateway;
    }
    
    public void run() {
        while(true) {
            try {
                /*
                Thread.sleep(250);
                if(gateway.open())
                    gateway.refresh();
                else
                    break;
                */
            } catch(Exception ex) {
                
            }
        }
    }
}
class UpdateGameState implements Runnable {
    private GameState gamestate;
    
    public UpdateGameState(GameState gamestate) {
        this.gamestate = gamestate;
    }
    
    public void run() {
        double ns = 1000000000.0 / 60.0;
        double delta = 0;

        long lastTime = System.nanoTime();

        while (true) {
        long now = System.nanoTime();
        delta += (now - lastTime) / ns;
        lastTime = now;

        while (delta >= 1) {
            gamestate.update();
            delta--;
        }
    }
    }
}
