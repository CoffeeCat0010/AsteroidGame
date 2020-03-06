package edu.lawrence.asteroidgame.GameObjects;

import edu.lawrence.asteroidgame.GameConsts;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.shape.Shape;

/**
 *
 * @author alanl
 */
public class GameState implements GameConsts{
    private PlayerShip ship;
    private ArrayList<Asteroid> asteroids;
    private List<Shape> shapes;
    
    public GameState() {
        shapes = new ArrayList<Shape>();
        asteroids = new ArrayList<Asteroid>();
        ship = new PlayerShip();
        shapes.add(ship.getShip());
    }
    
    public synchronized void movePlayer(boolean left) {
        ship.update(left);
    }
    
    public synchronized void update() {
        ship.draw();
    }
    
    public List<Shape> getShapes() { return shapes; }
}
