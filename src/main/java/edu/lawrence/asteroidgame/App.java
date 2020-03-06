package edu.lawrence.asteroidgame;

import edu.lawrence.asteroidgame.GameObjects.GameState;
import edu.lawrence.asteroidgame.Network.Gateway;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        
        Gateway gateway = new Gateway();
        GameState gamestate = new GameState();
        GamePane pane = new GamePane(gateway,gamestate);
        var scene = new Scene(pane, 480, 640);
        pane.requestFocus();
        stage.setScene(scene);
        //Some error occurs, preventing the window from closing due to this line currently
        //stage.setOnCloseRequest(e->gateway.close());
        stage.setTitle("Asteroid");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}