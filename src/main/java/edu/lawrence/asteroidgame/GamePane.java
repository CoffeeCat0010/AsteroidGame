package edu.lawrence.asteroidgame;

import edu.lawrence.asteroidgame.GameObjects.GameState;
import edu.lawrence.asteroidgame.Network.Gateway;
import java.util.List;
import java.util.Random;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
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
        new Thread(new UpdateGameState(this, gamestate)).start();
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
    
    public void updateShapes(List<Shape> shapes) {
        this.getChildren().clear();
        this.getChildren().addAll(gamestate.getShapes());
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
    private GamePane gamepane;
    
    public UpdateGameState(GamePane gamepane, GameState gamestate) {
        this.gamestate = gamestate;
        this.gamepane = gamepane;
    }
    
    public void run() {
        double ns = 1000000000.0 / 60.0;
        double delta = 0;
        int astTimer = 0;
        int randTimer = 100;
        Random random = new Random();

        long lastTime = System.nanoTime();

        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1) {
                gamestate.evolve(10);
                if(astTimer >= randTimer) {
                    gamestate.spawnAst();
                    astTimer = 0;
                    randTimer = random.nextInt(20)+10;
                }
                gamestate.update();
                Platform.runLater(() -> {
                    gamepane.getChildren().clear();
                    gamepane.getChildren().addAll(gamestate.getShapes());
                });
                delta--;
                astTimer++;
            }
        }
    }
}
