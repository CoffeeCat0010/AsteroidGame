package edu.lawrence.asteroidgame.GameObjects;

import edu.lawrence.asteroidgame.GameConsts;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javafx.scene.shape.Shape;

/**
 *
 * @author alanl
 */
public class GameState implements GameConsts {

    private PlayerShip ship;
    private ArrayList<Asteroid> asteroids;
    private List<Shape> shapes;
    private int score = 0;
    private long lastTime = 0;
    private boolean started = false;
    private Asteroid tempAst;
    private Iterator<Asteroid> iter;
    
    private Lock lock;
    private ReadWriteLock progressLock;
    private ReadWriteLock startLock;

    public GameState() {
        shapes = Collections.synchronizedList(new ArrayList<Shape>());
        asteroids = new ArrayList<Asteroid>();
        ship = new PlayerShip();
        shapes.add(ship.getShip());
        progressLock = new ReentrantReadWriteLock();
        startLock = new ReentrantReadWriteLock();
        lock = new ReentrantLock();

    }

    public synchronized void movePlayer(boolean left) {
        lock.lock();
        ship.update(left);
        iter = asteroids.iterator();
            while (iter.hasNext()) {
                tempAst = (Asteroid)iter.next();
                if(ship.contains(tempAst.getX(),tempAst.getY())) {
                    score = score - 100;
                    System.out.println("Score -100, current score: " + score);
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
                    score = score - 100;
                    System.out.println("Score -100, current score: " + score);
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

    public synchronized void update() {
        progressLock.writeLock().lock();
        score = score + 2;
        progressLock.writeLock().unlock();
        lock.lock();
        try {
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

    public List<Shape> getShapes() { return shapes; }

    public int getScore() {
        progressLock.readLock().lock();
        int result = score;
        progressLock.readLock().unlock();
        return result;
    }

    public void setScore(int score) {
        progressLock.writeLock().lock();
        this.score = score;
        progressLock.writeLock().unlock();

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

