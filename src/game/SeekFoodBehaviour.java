package game;

import edu.monash.fit2099.engine.*;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of SeekFruitBehaviour class
 * @author Amindu Kaushal Kumarasinghe
 * @author Abhishek Shrestha
 */
public class SeekFoodBehaviour implements Behaviour{
    private char type;

    /**
     * Constructor
     * @param type display char of dinosaur seeking for fruit (Stegosaur or Brachiosaur)
     */
    public SeekFoodBehaviour(char type) {
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

        Location minLocation = map.locationOf(actor);
//        Location closestFoodLocation = closestFood(actor, map);
        Location closestFoodLocation = closestLocation(actor, map);
        if (closestFoodLocation == null){
            System.out.println("There is no edible food for " + actor.toString());
            return null;
        }

        int minDistance = distance(closestFoodLocation, map.locationOf(actor));
//        int minDistance = closestDistance(actor, map);
        String name ="";
        if (minDistance == 0){
            // On same ground as food, so eating it
            return new EatFoodAction(closestFoodLocation);
        } else {
            for (Exit exit : map.locationOf(actor).getExits()) {
                Location destination = exit.getDestination();
                if (destination.canActorEnter(actor)) {
                    int newDistance = distance(closestFoodLocation, destination);
                    if (newDistance <= minDistance) {
                        minDistance = newDistance;
                        minLocation = destination;
                        name = exit.getName();
                    }
                }
            }
            return new MoveActorToConsumeAction(minLocation, name, "food");
        }
    }

    private Location closestLocation(Actor actor, GameMap map){
        SearchMap searchMap = new SearchMap(actor, map);
        Location location;
        Location returnLocation = null;
        double minDistance = Double.POSITIVE_INFINITY;
        // For Brachiosaur
        if (type == 'R'){
            ArrayList<Integer> distances= new ArrayList<>();
            location = searchMap.closest('+', "Ground");
            if (location != null){
                Tree tree = (Tree) location.getGround();
                if (tree.containsFruit()){
                    if (distance(location, map.locationOf(actor)) < minDistance){
                        minDistance = distance(location, map.locationOf(actor));
                        returnLocation = location;
                    }
                }
            }
            location = searchMap.closest('t', "Ground");
            if (location != null){
                Tree tree = (Tree) location.getGround();
                if (tree.containsFruit()){
                    if (distance(location, map.locationOf(actor)) < minDistance){
                        minDistance = distance(location, map.locationOf(actor));
                        returnLocation = location;
                    }
                }
            }
            location = searchMap.closest('T', "Ground");
            if (location != null){
                Tree tree = (Tree) location.getGround();
                if (tree.containsFruit()){
                    if (distance(location, map.locationOf(actor)) < minDistance){
                        minDistance = distance(location, map.locationOf(actor));
                        returnLocation = location;
                    }
                }
            }
        }
        // For Stegosaur
        else if (type == 'S'){
            ArrayList<Integer> distances= new ArrayList<>();
            location = searchMap.closest('b', "Ground");
            if (location != null){
                Bush bush = (Bush) location.getGround();
                if (bush.containsFruit()){
                    if (distance(location, map.locationOf(actor)) < minDistance){
                        minDistance = distance(location, map.locationOf(actor));
                        returnLocation = location;
                    }
                }
            }
            location = searchMap.closest('f', "Item");
            if (location != null){
                if (distance(location, map.locationOf(actor)) < minDistance){
                    minDistance = distance(location, map.locationOf(actor));
                    returnLocation = location;
                }
            }
        }
        // For Allosaur
        else if (type == 'A'){
            ArrayList<Integer> distances= new ArrayList<>();
            location = searchMap.closest('C', "Item");
            if (location != null){
                if (distance(location, map.locationOf(actor)) < minDistance){
                    minDistance = distance(location, map.locationOf(actor));
                    returnLocation = location;
                }
            }
            location = searchMap.closest('q', "Item");
            if (location != null){
                if (distance(location, map.locationOf(actor)) < minDistance){
                    minDistance = distance(location, map.locationOf(actor));
                    returnLocation = location;
                }
            }
            location = searchMap.closest('w', "Item");
            if (location != null){
                if (distance(location, map.locationOf(actor)) < minDistance){
                    minDistance = distance(location, map.locationOf(actor));
                    returnLocation = location;
                }
            }
        }
        // For Pterodactyls
        else if (type == 'P'){
            ArrayList<Integer> distances= new ArrayList<>();
            location = searchMap.closest('C', "Item");
            if (location != null){
                if (distance(location, map.locationOf(actor)) < minDistance){
                    minDistance = distance(location, map.locationOf(actor));
                    returnLocation = location;
                }
            }
            // if flying, check for lakes as well
            if (actor.hasCapability(Flight.YES)){
                location = searchMap.closest('~', "Ground");
                if (location != null){
                    Lake lake = (Lake) location.getGround();
                    if (lake.getFishCount() > 0){
                        if (distance(location, map.locationOf(actor)) < minDistance){
                            minDistance = distance(location, map.locationOf(actor));
                            returnLocation = location;
                        }
                    }
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
