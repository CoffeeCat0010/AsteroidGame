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
        asteroids = new ArrayList<Asteroid>();
        ship = new PlayerShip(WIDTH/2,HEIGHT);
        shapes.add(ship.getShip());
        /*
        public synchronized void movePaddle(boolean up) {
        if(up)
            outputToServer.println(MOVE_UP);
        else
            outputToServer.println(MOVE_DOWN);
        outputToServer.flush();
        }
        */
    }
    
    public List<Shape> getShapes() { return shapes; }
}
