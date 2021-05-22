package game;

import edu.monash.fit2099.engine.*;

/**
 * @author Amindu Kumarasinghe
 */
public class PterodactylSeekTreeBehaviour implements Behaviour {
    /**
     * The reason for seeking the tree (resting/laying egg)
     */
    String purpose;

    /**
     * Constructor
     * @param purpose The reason for seeking the tree (resting/laying egg)
     */
    public PterodactylSeekTreeBehaviour(String purpose) {
        this.purpose = purpose;
    }

    /**
     * Method to get the required Movement action by looking through the map for trees
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return A MovePterodactylToTreeAction if conditions are met, or else returns null
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        Location actorLocation = map.locationOf(actor);
        SearchMap searchMap = new SearchMap(actor, map);
        Location finalLoc = null;
        Location location;
        double minDist = Double.POSITIVE_INFINITY;
        location = searchMap.closest('T', "Ground");
        if (location != null){
            if (distance(location, actorLocation) < minDist){
                minDist = distance(location, actorLocation);
                finalLoc = location;
            }
        }

        location = searchMap.closest('t', "Ground");
        if (location != null){
            if (distance(location, actorLocation) < minDist){
                minDist = distance(location, actorLocation);
                finalLoc = location;
            }
        }
        location = searchMap.closest('+', "Ground");
        if (location != null){
            if (distance(location, actorLocation) < minDist){
                minDist = distance(location, actorLocation);
                finalLoc = location;
            }
        }
        String name = "";
        Location minLocation = actorLocation;
        if (finalLoc != null){
            int minDistance = distance(finalLoc, actorLocation);
            if (minDistance == 0){
                return new PterodactylRestAction();
            }
            for (Exit exit : map.locationOf(actor).getExits()) {
                Location destination = exit.getDestination();
                if (destination.canActorEnter(actor)) {
                    int newDistance = distance(finalLoc, destination);
                    if (newDistance < minDistance){
                        minDistance = newDistance;
                        minLocation = destination;
                        name = exit.getName();
                    }
                }
            }

            return new MovePterodactylToTreeAction(minLocation, name, purpose);
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
