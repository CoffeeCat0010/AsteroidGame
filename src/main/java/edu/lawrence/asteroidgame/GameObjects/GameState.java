package edu.lawrence.asteroidgame.GameObjects;

import edu.lawrence.asteroidgame.GameConsts;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
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
    private int score = 0;
    private long lastTime = 0;
    private boolean started = false;
    private ReadWriteLock lock;
    private ReadWriteLock startLock;

    
    public GameState() {
        shapes = new ArrayList<Shape>();
        asteroids = new ArrayList<Asteroid>();
        ship = new PlayerShip();
        shapes.add(ship.getShip());
        progress = new Label();
        progress.setLayoutX(10);
        progress.setLayoutY(10);
        lock = new ReentrantReadWriteLock();
        startLock = new ReentrantReadWriteLock();

    }
    
    public synchronized void movePlayer(boolean left) {
        ship.update(left);
    }
    
    public synchronized void update() {
        if(lastTime == 0) lastTime = System.currentTimeMillis();
        long currentTime = System.currentTimeMillis();
        long deltaTime = currentTime - lastTime;
        lock.writeLock().lock();
        score += (deltaTime/1000) * 1;
        lock.writeLock().unlock();
        Platform.runLater(() -> progress.setText(String.valueOf(score)));
        ship.draw();
    }
    
    public List<Shape> getShapes() { return shapes; }

    public int getScore() {
        lock.readLock().lock();
        int result = score;
        lock.readLock().unlock();
        return result;
    }

    public void setScore(int score) {
        lock.writeLock().lock();
        this.score = score;
        lock.writeLock().unlock();

    }
    
    public Label getProgress(){
        return progress;
    }

    public boolean isStarted() {
        boolean result = false;
        try{
        startLock.readLock().lock();
        result = started;
        }finally{
        startLock.readLock().unlock();
        }
        return result;
    }

    public void setStarted(boolean isStarted) {
        try{
        startLock.writeLock().lock();
        this.started = isStarted;
        }finally{
        startLock.writeLock().unlock();
        }
    }
    
}
