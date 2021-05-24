package game;

import edu.monash.fit2099.engine.*;

import java.util.List;

/**
 * @author Amindu Kumarasinghe
 */
public class SearchMap {
    /**
     * The current map
     */
    GameMap map;

    /**
     * The relevant actor
     */
    Actor actor;

    /**
     * Constructor
     * @param actor  The relevant actor
     * @param map The current map
     */
    public SearchMap(Actor actor, GameMap map) {
        this.map = map;
        this.actor = actor;
    }

    /**
     * Goes through the given map, and looks for a target object (actor/item/ground)
     * @param target The object that we are looking for
     * @param type The type of object (whether its an Actor, Item or Ground)
     * @return closest location to target from the actor
     */
    public Location closest(char target, String type){
        boolean found = false;
        NumberRange width = map.getXRange();
        NumberRange height = map.getYRange();
        Location dinoLocation = map.locationOf(actor);
        // This method is used to initialize bestLocation
        Location bestLocation = firstLocation(map, target);
        if (bestLocation != null){
            int minDistance = distance(dinoLocation, bestLocation);
            for (int i : width){
                for (int j : height){
                    Location newLocation = map.at(i, j);
                    if (newLocation.getGround() != null) {
                        char groundType = newLocation.getGround().getDisplayChar();
                        // if searching for ground
                        if (type.equals("Ground")){
                            if (groundType == target) {
                                found = true;
                                int distance = distance(dinoLocation, newLocation);
                                // compare and update best distance and best location
                                if (distance < minDistance) {
                                    minDistance = distance;
                                    bestLocation = newLocation;
                                }
                            }
                        }
                        // if searching for item
                        else if (type.equals("Item")){
                            List<Item> items = newLocation.getItems();
                            for (Item item : items) {
                                // check if fruit is there
                                if (item.getDisplayChar() == target) {
                                    found = true;
                                    int distance = distance(dinoLocation, newLocation);
                                    if (distance < minDistance) {
                                        minDistance = distance;
                                        bestLocation = newLocation;
                                    }
                                }
                            }
                        }
                        else if (type.equals("Actor")){
                            if (newLocation.getActor() != null){
                                if (newLocation.getActor().getDisplayChar() == target){
                                    found = true;
                                    int distance = distance(dinoLocation, newLocation);
                                    if (distance < minDistance) {
                                        minDistance = distance;
                                        bestLocation = newLocation;
                                    }
                                }
                            }

                        }

                    }
                }
            }
            if (found){
                return bestLocation;
            }
        }
        return null;
    }

    /**
     * Used to get the first location in the map that contains either a fruit or a bush with fruits for stegosaur
     * or tree with fruits for brachiosaur
     * @param map map which dinosaur is on
     * @return first location in the map that contains either a fruit or a bush with fruits
     */
    private Location firstLocation(GameMap map, char targetGround){
        NumberRange width = map.getXRange();
        NumberRange height = map.getYRange();
        for (int i : width){
            for (int j : height){
                Location newLocation = map.at(i, j);
                if (newLocation.getGround() != null){
                    char groundChar = newLocation.getDisplayChar();
                    if (groundChar == targetGround){
                        return newLocation;
                    }
                }
            }
        }

        return null;
    }

    /**
     * Compute the Manhattan distance between two locations.
     *
     * @param a the first location
     * @param b the first location
     * @return the number of steps between a and b if you only move in the four cardinal directions.
     */
    private int distance(Location a, Location b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }


}
