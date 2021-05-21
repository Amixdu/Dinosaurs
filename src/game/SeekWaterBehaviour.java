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
        // so if already flying above lake, use fishing
        if (type == 'P'){
            if (minDistance == 0){
                fishing(actor, closestWater);
                return null;
            }
            else if(minDistance == 1){
                name = drinkWater(actor, closestWater);
                return null;
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
            name = drinkWater(actor, closestWater);
            return null;
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

    /**
     * Heal dinosaur and remove fruit from ground or bush
     * @param actor vegetarian dinosaur seeking for fruit

     * @param waterLocation location of the closest fruit
     * @return a string saying went nowhere, since dinosaur was eating
     */
    private String drinkWater(Actor actor, Location waterLocation){
        Lake lake = (Lake) waterLocation.getGround();
        lake.setSips(lake.getSips() - 1);
        if (type == 'R') {
            // if brachiosaur
            Brachiosaur brachiosaur = (Brachiosaur) actor;
            if (brachiosaur.getWaterLevel() + 80 < brachiosaur.getMaxWaterLevel()){
                brachiosaur.setWaterLevel(brachiosaur.getWaterLevel() + 80);
            }
            else {
                brachiosaur.setWaterLevel(brachiosaur.getMaxWaterLevel());
            }
            System.out.println(actor.toString() + " at location (" + waterLocation.x() + "," +
                    waterLocation.y() + ") drinks water");

        }
        // if Allosaur, Stegosaur or Pterodactyl
        else{
            // if allosaur or stegosaur
            Dinosaur dinosaur = (Dinosaur) actor;
            if (dinosaur.getWaterLevel() + 30 < dinosaur.getMaxWaterLevel()){
                dinosaur.setWaterLevel(dinosaur.getWaterLevel() + 30);
            }
            else {
                dinosaur.setWaterLevel(dinosaur.getMaxWaterLevel());
            }
            System.out.println(actor.toString() + " at location (" + waterLocation.x() + "," +
                    waterLocation.y() + ") drinks water");
        }
        return "nowhere";
    }

    private void fishing(Actor actor, Location waterLocation){
        // this method will only be called for pterodactyls
        Pterodactyl pterodactyl = (Pterodactyl) actor;
        Lake lake = (Lake) waterLocation.getGround();
        if (lake.getFishCount() >= 2){
            // chance based system : 60% chance of catching only one fish,
            // 60% chance of catching two and 20% chance of catching none.
            double random = Math.random();
            int fish = lake.getFishCount();
            // chance for eating one fish
            if (random > 0.4) {
                lake.setFishCount(fish - 1);
                pterodactyl.heal(5);
                pterodactyl.setWaterLevel(pterodactyl.getWaterLevel() + 30);
                System.out.println(actor.toString() + " at location (" + waterLocation.x() + "," +
                        waterLocation.y() + ") eats " + "1 fish and increases water level by 30");
            }
            // chance for eating two fish
            else if (random >= 0.2 && random <= 0.4){
                lake.setFishCount(fish - 2);
                pterodactyl.heal(5 * 2);
                pterodactyl.setWaterLevel(pterodactyl.getWaterLevel() + 60);
                System.out.println(actor.toString() + " at location (" + waterLocation.x() + "," +
                        waterLocation.y() + ") eats " + "2 fish and increases water level by 60");
            }
            // chance for eating no fish
            else if (random < 0.2) {
                System.out.println(actor.toString() + " at location (" + waterLocation.x() + "," +
                        waterLocation.y() + ") tries to eat but couldnt catch any fish");
                pterodactyl.setWaterLevel(pterodactyl.getWaterLevel() + 30);
            }
        }
        // theres only one fish
        else if (lake.getFishCount() == 1){
            lake.setFishCount(lake.getFishCount() - 1);
            pterodactyl.heal(5);
            pterodactyl.setWaterLevel(pterodactyl.getWaterLevel() + 30);
            System.out.println(actor.toString() + " at location (" + waterLocation.x() + "," +
                    waterLocation.y() + ") eats " + "1 fish and increases water level by 30");
        }
        // no fish
        else{
            System.out.println(actor.toString() + " at location (" + waterLocation.x() + "," +
                    waterLocation.y() + ") tries to eat but couldnt catch any fish");
            pterodactyl.setWaterLevel(pterodactyl.getWaterLevel() + 30);
        }

    }

//    Below method can be used to simulate fruits falling from trees for testing :

//    public void fallFruit(GameMap map){
//        NumberRange width = map.getXRange();
//        NumberRange height = map.getYRange();
//        for (int i : width){
//            for (int j : height){
//                Location newLocation = map.at(i, j);
//                if (newLocation.getGround().getDisplayChar() == '+') {
//                    if (Math.random() < 0.05){
//                        Fruit fruit = new Fruit();
//                        newLocation.addItem(fruit);
//                    }
//                }
//            }
//        }
//    }
}
