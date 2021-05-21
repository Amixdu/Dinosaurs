package game;

import edu.monash.fit2099.engine.*;


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
        Location closestFoodLocation = closestFood(actor, map);
        if (closestFoodLocation == null){
            System.out.println("There is no edible food for " + actor.toString());
            return null;
        }

        int minDistance = distance(closestFoodLocation, map.locationOf(actor));
        String name ="";
        if (minDistance == 0){
            // On same ground as food, so eating it
            return new EatFoodAction(closestFoodLocation);
        } else {
            for (Exit exit : map.locationOf(actor).getExits()) {
                Location destination = exit.getDestination();
                if (destination.canActorEnter(actor)) {
                    int newDistance = distance(closestFoodLocation, destination);
                    if (newDistance < minDistance) {
                        minDistance = newDistance;
                        minLocation = destination;
                        name = exit.getName();
                    }
                }
            }
            return new MoveActorToConsumeAction(minLocation, name, "food");
        }
    }

    /**
     * Gets the location of the closes food from the whole map
     * @param actor vegetarian dinosaur seeking for fruit
     * @param map map on which the dinosaur is currently on
     * @return location of closest food when calculated from current position of dinosaur
     */
    private Location closestFood(Actor actor,GameMap map){
        boolean foundFood = false;
        NumberRange width = map.getXRange();
        NumberRange height = map.getYRange();
        Location dinoLocation = map.locationOf(actor);
        // This method is used to initialize bestLocation
        Location bestLocation = firstLocationWithFood(actor, map);
        if (bestLocation != null){
            int minDistance = distance(dinoLocation, bestLocation);
            for (int i : width){
                for (int j : height){
                    Location newLocation = map.at(i, j);
                    if (newLocation.getGround() != null) {
                        // if dino is brachiosaur, find closest tree
                        if (type == 'R') {
                            char groundType = newLocation.getGround().getDisplayChar();
                            // if tree
                            if (groundType == '+' || groundType == 't' || groundType == 'T') {
                                Tree currentTree = (Tree) newLocation.getGround();

                                // if tree has fruits
                                if (currentTree.containsFruit()) {
                                    foundFood = true;
                                    int distance = distance(dinoLocation, newLocation);
                                    // compare and update best distance and best location
                                    if (distance < minDistance) {
                                        minDistance = distance;
                                        bestLocation = newLocation;
                                    }
                                }
                            }
                        } else if (type == 'S') {
                            // if dino is stegosaur

                            // if found a bush
                            if (newLocation.getGround().getDisplayChar() == 'b') {
                                Bush currentBush = (Bush) newLocation.getGround();
                                // if bush has fruits
                                if (currentBush.getFruits() > 0) {
                                    foundFood = true;
                                    int distance = distance(dinoLocation, newLocation);
                                    // compare and update best distance and best location
                                    if (distance < minDistance) {
                                        minDistance = distance;
                                        bestLocation = newLocation;
                                    }
                                }
                            }
                            // iterate through all items in current location
                            List<Item> items = newLocation.getItems();
                            for (Item item : items) {
                                // check if fruit is there
                                if (item.getDisplayChar() == 'f') {
                                    foundFood = true;
                                    int distance = distance(dinoLocation, newLocation);
                                    if (distance < minDistance) {
                                        minDistance = distance;
                                        bestLocation = newLocation;
                                    }
                                }
                            }
                        } else if (type == 'A'){
                            // get list of items at loaton
                            List<Item> items = newLocation.getItems();
                            for (Item item : items){
                                // if a corpse is found at location
                                if (item.getDisplayChar() == 'C'){
                                    foundFood = true;
                                    int distance = distance(dinoLocation, newLocation);
                                    // check distance and update
                                    if (distance < minDistance){
                                        minDistance = distance;
                                        bestLocation = newLocation;
                                    }
                                }
                                // if a stegosaur egg is found
                                else if (item.getDisplayChar() == 'q'){
                                    foundFood = true;
                                    int distance = distance(dinoLocation, newLocation);
                                    if (distance < minDistance){
                                        minDistance = distance;
                                        bestLocation = newLocation;
                                    }
                                }
                                // if a brachiosaur egg is found
                                else if (item.getDisplayChar() == 'w'){
                                    foundFood = true;
                                    int distance = distance(dinoLocation, newLocation);
                                    if (distance < minDistance){
                                        minDistance = distance;
                                        bestLocation = newLocation;
                                    }
                                }
                            }
                        } else if (type == 'P'){
                            // found a lake and can fly
                            if (newLocation.getGround().getDisplayChar() == '~' && actor.hasCapability(Flight.YES)){
                                Lake lake = (Lake) newLocation.getGround();
                                if (lake.getFishCount() > 0){
                                    foundFood = true;
                                    int distance = distance(dinoLocation, newLocation);
                                    // compare and update best distance and best location
                                    if (distance < minDistance) {
                                        minDistance = distance;
                                        bestLocation = newLocation;
                                    }
                                }
                            }

                            // if found water
                            List<Item> items = newLocation.getItems();
                            for (Item item : items){
                                // if a corpse is found at location
                                if (item.getDisplayChar() == 'C'){
                                    foundFood = true;
                                    int distance = distance(dinoLocation, newLocation);
                                    // check distance and update
                                    if (distance < minDistance){
                                        minDistance = distance;
                                        bestLocation = newLocation;
                                    }
                                }
                                // if a stegosaur egg is found
                                else if (item.getDisplayChar() == 'q'){
                                    foundFood = true;
                                    int distance = distance(dinoLocation, newLocation);
                                    if (distance < minDistance){
                                        minDistance = distance;
                                        bestLocation = newLocation;
                                    }
                                }
                                // if a brachiosaur egg is found
                                else if (item.getDisplayChar() == 'w'){
                                    foundFood = true;
                                    int distance = distance(dinoLocation, newLocation);
                                    if (distance < minDistance){
                                        minDistance = distance;
                                        bestLocation = newLocation;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (foundFood){
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
    private Location firstLocationWithFood(Actor actor, GameMap map){
        NumberRange width = map.getXRange();
        NumberRange height = map.getYRange();
        for (int i : width){
            for (int j : height){
                Location newLocation = map.at(i, j);
                if (newLocation.getGround() != null){
                    // if actor is brachiosaur, find tree
                    if (type == 'R'){
                        char groundChar = newLocation.getDisplayChar();
                        if (groundChar == '+' || groundChar == 't' || groundChar == 'T'){
                            Tree currentTree = (Tree) newLocation.getGround();
                            if (currentTree.containsFruit()){
                                return newLocation;
                            }
                        }
                    } else if (type == 'S') {
                        // if actor is stegosaur, find fruit or bush

                        // if location with bush found, return location
                        if (newLocation.getGround().getDisplayChar() == 'b') {
                            Bush currentBush = (Bush) newLocation.getGround();
                            if (currentBush.containsFruit()) {
                                return newLocation;
                            }
                        }

                        // if location with fruit found, return location
                        List<Item> items = newLocation.getItems();
                        for (Item item : items) {
                            if (item.getDisplayChar() == 'f') {
                                return newLocation;
                            }
                        }
                    } else if (type == 'A'){
                        // if location with fruit found, return location
                        List<Item> items = newLocation.getItems();
                        for (Item item : items) {
                            if (item.getDisplayChar() == 'C') {
                                return newLocation;
                            }
                            // q = Stegosaur egg
                            else if (item.getDisplayChar() == 'q'){
                                return newLocation;
                            }
                            // w = Brachiosaur egg
                            else if (item.getDisplayChar() == 'w'){
                                return newLocation;
                            }
                        }
                    } else if (type == 'P'){
                        // if lake with fish found and Pterodactyl can fly, return location
                        if (newLocation.getGround().getDisplayChar() == '~' && actor.hasCapability(Flight.YES)) {
                            Lake lake = (Lake) newLocation.getGround();
                            if (lake.getFishCount() > 0) {
                                return newLocation;
                            }
                        }
                        List<Item> items = newLocation.getItems();
                        for (Item item : items) {
                            if (item.getDisplayChar() == 'C') {
                                return newLocation;
                            }
                            // q = Stegosaur egg
                            else if (item.getDisplayChar() == 'q'){
                                return newLocation;
                            }
                            // w = Brachiosaur egg
                            else if (item.getDisplayChar() == 'w'){
                                return newLocation;
                            }
                        }

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
