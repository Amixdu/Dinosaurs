package game;

import edu.monash.fit2099.engine.*;


import java.util.List;

/**
 * Implementation of SeekFruitBehaviour class
 * @author Amindu Kaushal Kumarasinghe
 * @author Abhishek Shrestha
 */
public class SeekWaterBehaviour implements Behaviour{
    private char type;

    /**
     * Constructor
     * @param type display char of dinosaur seeking for fruit (Stegosaur or Brachiosaur)
     */
    public SeekWaterBehaviour(char type) {
        this.type = type;
    }

    /**
     * Obtain action which causes the dinosaur to move closer to the closest food
     * @param actor the dinosaur seeking for food
     * @param map the GameMap containing the Actor
     * @return MoveActorAction with destination and name(direction) of exit
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {

//        uncomment this and line 172 to simulate fruit falling for testing
//        fallFruit(map);
        Location minLocation = map.locationOf(actor);
//        Location closestWater = closestWater(actor, map);
        Location closestWater = closestWaterLoc(actor, map);
        if (closestWater == null){
            System.out.println("There is no water available in map");
            return null;
        }

        int minDistance = distance(closestWater, map.locationOf(actor));
        String name ="";
        // pterodactyls can either drink water by walking or flying
        // so if already flying above lake, dip beak in and drink
        if (type == 'P' && actor.hasCapability(Flight.YES)){
            if (minDistance == 0){
                return new DrinkWaterAction(closestWater);
            }
        }

        // if next to lake, drink water
        if (minDistance == 1){
            return new DrinkWaterAction(closestWater);
//            name = drinkWater(actor, closestWater);
//            return null;
        } else {
            for (Exit exit : map.locationOf(actor).getExits()) {
                Location destination = exit.getDestination();
                if (destination.canActorEnter(actor)) {
                    int newDistance = distance(closestWater, destination);
                    if (newDistance < minDistance) {
                        minDistance = newDistance;
                        minLocation = destination;
                        name = exit.getName();
                    }
                }
            }
            return new MoveActorToConsumeAction(minLocation, name, "water");
        }
    }

    private Location closestWaterLoc (Actor actor, GameMap map){
        SearchMap searchMap = new SearchMap(actor, map);
        Location location;
        Location returnLocation = null;
        double minDistance = Double.POSITIVE_INFINITY;
        location = searchMap.closest('~', true);
        if (location != null){
            Lake lake = (Lake) location.getGround();
            if (lake.getSips() > 0){
                if (distance(location, map.locationOf(actor)) < minDistance){
                    returnLocation = location;
                }
            }
        }
        return returnLocation;
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
