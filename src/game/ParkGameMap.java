package game;

import edu.monash.fit2099.engine.*;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * Class Representing ParkGameMap
 * @author Abhishek Shrestha
 */
public class ParkGameMap extends GameMap {
    /**
     * Random instance
     */
    private Random rand = new Random();
    /**
     * Number of turns
     */
    private int noOfTurns;

    /**
     * Constructor that creates a map from a sequence of ASCII strings.
     *
     * @param groundFactory Factory to create Ground objects
     * @param lines         List of Strings representing rows of the map
     */
    public ParkGameMap(GroundFactory groundFactory, List<String> lines) {
        super(groundFactory, lines);
        noOfTurns = 0;
    }

    /**
     * Constructor.
     *
     * @param groundFactory Factory to create Ground objects
     * @param groundChar    Symbol that will represent empty Ground in this map
     * @param width         width of the GameMap, in characters
     * @param height        height of the GameMap, in characters
     */
    public ParkGameMap(GroundFactory groundFactory, char groundChar, int width, int height) {
        super(groundFactory, groundChar, width, height);
        noOfTurns = 0;
    }

    /**
     * Constructor that reads a map from file.
     *
     * @param groundFactory Factory to create Ground objects
     * @param mapFile       Name of a file containing an ASCII representation of a
     *                      level
     * @throws IOException when file I/O fails
     */
    public ParkGameMap(GroundFactory groundFactory, String mapFile) throws IOException {
        super(groundFactory, mapFile);
        noOfTurns = 0;
    }

    /**
     * Method to add exits to a location in another map
     * Access level set to public
     * @param here the current location
     * @param x X coordinate
     * @param y Y coordinate
     * @param name name of the Exit
     * @param hotKey the hotkey for the appropiate Action
     */
    public void addExitFromHereToAnotherMap(Location here, int x, int y, String name, String hotKey, GameMap newMap){
        NumberRange newMapWidth = newMap.getXRange();
        NumberRange newMapHeight = newMap.getYRange();
        if (newMapWidth.contains(x) && newMapHeight.contains(y)){
            here.addExit(new Exit(name, newMap.at(x,y), hotKey));
        }
    }
}
