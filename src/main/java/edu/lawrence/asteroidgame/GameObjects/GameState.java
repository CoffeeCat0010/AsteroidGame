package edu.lawrence.asteroidgame.GameObjects;

import edu.lawrence.asteroidgame.GameConsts;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.shape.Shape;

/**
 *
 * @author alanl
 */
public class GameState implements GameConsts{
    private PlayerShip ship;
    private ArrayList<Asteroid> asteroids;
    private List<Shape> shapes;
    private Label progress;
    private double score = 0;
    private long lastTime = 0;
    
    public GameState() {
        shapes = new ArrayList<Shape>();
        asteroids = new ArrayList<Asteroid>();
        ship = new PlayerShip();
        shapes.add(ship.getShip());
        progress = new Label();
        progress.setLayoutX(10);
        progress.setLayoutY(10);

    }
    
    public synchronized void movePlayer(boolean left) {
        ship.update(left);
    }
    
    public synchronized void update() {
        if(lastTime == 0) lastTime = System.currentTimeMillis();
        long currentTime = System.currentTimeMillis();
        long deltaTime = currentTime - lastTime;
        score += (deltaTime/1000) * 10; 
        Platform.runLater(() -> progress.setText(String.valueOf(score)));
        ship.draw();
    }
    
    public List<Shape> getShapes() { return shapes; }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
    
    public Label getProgress(){
        return progress;
    }
    
}
