package edu.lawrence.asteroidgame;

import edu.lawrence.asteroidgame.GameObjects.GameState;
import edu.lawrence.asteroidgame.Network.Gateway;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {
    private Gateway gateway;
    private GameState gameState;
    
    @Override
    public void start(Stage stage) {
        
        gateway = new Gateway();
        gameState = new GameState();
        GamePane pane = new GamePane(gateway, gameState);
        var scene = new Scene(pane, GameConsts.WIDTH, GameConsts.HEIGHT);
        pane.requestFocus();
        stage.setScene(scene);
        stage.setOnCloseRequest(e->gateway.close());
        stage.setTitle("Asteroid");
        stage.show();
        
    }

    public static void main(String[] args) {
        launch();
    }
}