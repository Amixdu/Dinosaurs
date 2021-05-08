package game;

import edu.monash.fit2099.engine.*;


import java.util.List;


public class SeekFruitBehaviour implements Behaviour{
    char type;

    /**
     * Constructor
     * @param type display char of dinosaur seeking for fruit (Stegosaur or Brachiosaur)
     */
    public SeekFruitBehaviour(char type) {
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
        Location closestFruit = closestFood(actor, map);
        if (closestFruit == null){
            System.out.println("There are no fruits available in map");
            return null;
        }

        int minDistance = distance(closestFruit, map.locationOf(actor));
        String name ="";
        if (minDistance == 0){
            // On same ground as fruit, so eating it
            name = eatFood(actor, closestFruit);
        }

        for (Exit exit : map.locationOf(actor).getExits()) {
            Location destination = exit.getDestination();
            if (destination.canActorEnter(actor)) {
                int newDistance = distance(closestFruit, destination);
                if (newDistance < minDistance){
                    minDistance = newDistance;
                    minLocation = destination;
                    name = exit.getName();
                }
            }
        }
        return new MoveActorAction(minLocation, name);

    }

    /**
     * Gets the location of the closes food from the whole map
     * @param actor vegetarian dinosaur seeking for fruit
     * @param map map on which the dinosaur is currently on
     * @return location of closest food when calculated from current position of dinosaur
     */
    public Location closestFood(Actor actor,GameMap map){
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
                    if (newLocation.getGround() != null){
                        // if found a bush
                        if (newLocation.getGround().getDisplayChar() == 'b') {
                            Bush currentBush = (Bush) newLocation.getGround();
                            // if bush has fruits
                            if (currentBush.getFruits() > 0){
                                foundFood = true;
                                int distance = distance(dinoLocation, newLocation);
                                // compare and update best distance and best location
                                if (distance < minDistance){
                                    minDistance = distance;
                                    bestLocation = newLocation;
                                }
                            }
                        }
                        List<Item> items = newLocation.getItems();
                        for (Item item : items){
                            if (item.getDisplayChar() == 'f'){
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
            if (foundFood){
                return bestLocation;
            }
        }
        return null;
    }

    /**
     * Used to get the first location in the map that contains either a fruit or a bush with fruits
     * @param actor  vegetarian dinosaur seeking for fruit
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
                    if (newLocation.getGround().getDisplayChar() == 'b') {
                        Bush currentBush = (Bush) newLocation.getGround();
                        if (currentBush.getFruits() > 0){
                            return newLocation;
                        }
                    }
                    List<Item> items = newLocation.getItems();
                    for (Item item : items){
                        if (item.getDisplayChar() == 'f'){
                            return newLocation;
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

     * @param fruitLocation location of the closest fruit
     * @return a string saying went nowhere, since dinosaur was eating
     */
    private String eatFood(Actor actor, Location fruitLocation){
        if (type == 'S'){
            Stegosaur steg = (Stegosaur) actor;
            steg.heal(10);
        }

        if (fruitLocation.getGround().getDisplayChar() == 'b'){
            Bush bush = (Bush) fruitLocation.getGround();
            bush.removeFruit();
        }
        else {
            List<Item> items = fruitLocation.getItems();
            for (int i = 0; i<items.size(); i++ ){
                if (items.get(i).getDisplayChar() == 'f'){
                    fruitLocation.removeItem(items.get(i));
                    System.out.println("Stegasour at location (" + fruitLocation.x() + "," + fruitLocation.y() +")eats");
                }
            }
        }
        return "nowhere";

    }

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
