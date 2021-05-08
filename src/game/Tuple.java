package game;

import edu.monash.fit2099.engine.Location;

/**
 * Implementation of helper class Tuple
 * @author Amindu Kaushal Kumarasinghe
 */
public class Tuple {
    /**
     * First item of tuple
     */
    private Location first;
    /**
     * second item of tuple
     */
    private char second;

    /**
     * Constructor
     * @param first Location of corpse or egg
     * @param second Display character of dinosaur before death or display character of egg
     */
    public Tuple(Location first, char second) {
        this.first = first;
        this.second = second;
    }

    /**
     * @return Location of the corpse/egg
     */
    public Location getFirst() {
        return first;
    }

    /**
     * @return Display character to identify type of food
     */
    public char getSecond() {
        return second;
    }

}
