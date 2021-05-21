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
        Location closestWater = closestWater(actor, map);
        if (closestWater == null){
            System.out.println("There is no water available in map");
            return null;
        }

        int minDistance = distance(closestWater, map.locationOf(actor));
        String name ="";
        // pterodactyls can either drink water by walking or flying
        // so if already flying above lake, dip beak in and drink
        if (type == 'P'){
            if (minDistance == 0){
                return new DrinkWaterAction(closestWater);
            }
            else if(minDistance == 1){
                return new DrinkWaterAction(closestWater);
            }
            else {
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

    /**
     * Gets the location of the closes food from the whole map
     * @param actor vegetarian dinosaur seeking for fruit
     * @param map map on which the dinosaur is currently on
     * @return location of closest food when calculated from current position of dinosaur
     */
    private Location closestWater(Actor actor, GameMap map){
        boolean foundWater = false;
        NumberRange width = map.getXRange();
        NumberRange height = map.getYRange();
        Location dinoLocation = map.locationOf(actor);
        // This method is used to initialize bestLocation
        Location bestLocation = firstLocationWithWater(map);
        if (bestLocation != null){
            int minDistance = distance(dinoLocation, bestLocation);
            for (int i : width){
                for (int j : height){
                    Location newLocation = map.at(i, j);
                    if (newLocation.getGround() != null) {
                        char groundType = newLocation.getGround().getDisplayChar();
                        if (groundType == '~') {
                            Lake lake = (Lake) newLocation.getGround();
                            if (lake.getSips() > 0){
                                foundWater = true;
                                int distance = distance(dinoLocation, newLocation);
                                // compare and update best distance and best location
                                if (distance < minDistance) {
                                    minDistance = distance;
                                    bestLocation = newLocation;
                                }
                            }
                            // Pterodactyls can increase water level by drinking while flying as well
                            if (type == 'P'){
                                if (lake.getSips() > 0){
                                    foundWater = true;
                                    int distance = distance(dinoLocation, newLocation);
                                    // compare and update best distance and best location
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
            if (foundWater){
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
    private Location firstLocationWithWater(GameMap map){
        NumberRange width = map.getXRange();
        NumberRange height = map.getYRange();
        for (int i : width){
            for (int j : height){
                Location newLocation = map.at(i, j);
                if (newLocation.getGround() != null){
                    char groundChar = newLocation.getDisplayChar();
                    if (groundChar == '~'){
                        Lake lake = (Lake) newLocation.getGround();
                        if (lake.getSips() > 0){
                            return newLocation;
                        }
                        // Pterodactyls can increase water level by fish as well
                        if (type == 'P'){
                            if (lake.getSips() > 0){
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
