package edu.lawrence.asteroidgame.GameObjects;

import edu.lawrence.asteroidgame.GameConsts;
import java.util.ArrayList;
import java.util.Collections;
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
        //shapes = new ArrayList<Shape>();
        shapes = Collections.synchronizedList(new ArrayList<Shape>());
        asteroids = new ArrayList<Asteroid>();
        ship = new PlayerShip();
        shapes.add(ship.getShip());
        lock = new ReentrantLock();
    }
    
    public void movePlayer(boolean left) {
        lock.lock();
        ship.update(left);
        iter = asteroids.iterator();
            while (iter.hasNext()) {
                tempAst = iter.next();
                if(ship.collision(tempAst.getRay(),2) && ship.collision(tempAst.getRay(), -2)) {
                    score = score - 500;
                    System.out.println("Score -500");
                    iter.remove();
                }
            }
        lock.unlock();
    }
    
    public void evolve(double time) {
        lock.lock();
        try {
            iter = asteroids.iterator();
            while (iter.hasNext()) {
                tempAst = iter.next();
                if(ship.collision(tempAst.getRay(),time)) {
                    score = score - 50;
                    System.out.println("Score -50, current score: " + score);
                    iter.remove();
                } else {
                    tempAst.move(time);
                    if(tempAst.getY() > HEIGHT) {
                        iter.remove();
                    }
                }
            }
        } finally {
            lock.unlock();
        }
    }
    
    public void spawnAst() {
        lock.lock();
        try {
            Random random = new Random();
            double position;
            position = (random.nextInt(3)-1)*(WIDTH/3)+WIDTH/2;
            asteroids.add(new Asteroid(20,position,0));
        } finally {
            lock.unlock();
        }
    }
    
    public void update() {
        lock.lock();
        try {
            score = score + 2;
            ship.draw();
            iter = asteroids.iterator();
            shapes.clear();
            shapes.add(ship.getShip());
            while (iter.hasNext()) {
                tempAst = iter.next();
                shapes.add(tempAst.getAsteroid());
                tempAst.draw();
            }
        } finally {
            lock.unlock();
        }
    }
    
    public List<Shape> getShapes() { 
        return shapes; 
    }
}
