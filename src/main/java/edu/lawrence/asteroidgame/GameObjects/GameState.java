package edu.lawrence.asteroidgame.GameObjects;

import edu.lawrence.asteroidgame.GameConsts;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javafx.scene.shape.Shape;

/**
 *
 * @author alanl
 */
public class GameState implements GameConsts{
    private PlayerShip ship;
    private ArrayList<Asteroid> asteroids;
    private List<Shape> shapes;
    private Lock lock;
    private Iterator<Asteroid> iter;
    private Asteroid tempAst;
    private int score = 0;
    
    public GameState() {
        shapes = new ArrayList<Shape>();
        asteroids = new ArrayList<Asteroid>();
        ship = new PlayerShip();
        shapes.add(ship.getShip());
        lock = new ReentrantLock();
    }
    
    public void movePlayer(boolean left) {
        lock.lock();
        ship.update(left);
        lock.unlock();
    }
    
    public void evolve(double time) {
        lock.lock();
        iter = asteroids.iterator();
        while (iter.hasNext()) {
            tempAst = iter.next();
            if(ship.collision(tempAst.getRay(),time)) {
                score = score - 500;
                iter.remove();
            } else {
                tempAst.move(time);
                if(tempAst.getY() > HEIGHT) {
                    iter.remove();
                }
            }
        }
        lock.unlock();
    }
    
    public void spawnAst() {
        lock.lock();
        Random random = new Random();
        double position;
        position = (random.nextInt(2)+1)*WIDTH*(1/3);
        asteroids.add(new Asteroid(5,position,0));
        lock.unlock();
    }
    
    public void update() {
        lock.lock();
        score = score + 1;
        ship.draw();
        iter = asteroids.iterator();
        shapes.clear();
        shapes.add(ship.getShip());
        while (iter.hasNext()) {
            shapes.add(tempAst.getAsteroid());
            tempAst = iter.next();
            tempAst.draw();
        }
        lock.unlock();
    }
    
    public List<Shape> getShapes() { return shapes; }
}
