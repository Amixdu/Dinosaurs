package game;

import edu.monash.fit2099.engine.*;


import java.util.List;


public class SeekFruitBehaviour implements Behaviour{
    char type;

    public SeekFruitBehaviour(char type) {
        this.type = type;
    }

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



        // to test fruits in bush before eating
//        Bush bush = (Bush) closestFruit.getGround();
//        System.out.println(bush.getFruits());

        // to test location of closest fruit
//        System.out.println("x:"+closestFruit.x() + "y:" + closestFruit.y());

        int minDistance = distance(closestFruit, map.locationOf(actor));
        String name ="";
        if (minDistance == 0){
            // On same ground as fruit, so eating it
            name = eatFood(actor, map, closestFruit);
            // to test fruits in bush before eating
//            System.out.println(bush.getFruits());

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

    public Location closestFood(Actor actor,GameMap map){
        boolean foundFood = false;
        NumberRange width = map.getXRange();
        NumberRange height = map.getYRange();
        Location stegLocation = map.locationOf(actor);
        Location bestLocation = firstLocationWithFood(actor, map);
        if (bestLocation != null){
            int minDistance = distance(stegLocation, bestLocation);
            for (int i : width){
                for (int j : height){
                    Location newLocation = map.at(i, j);
                    if (newLocation.getGround() != null){
                        if (newLocation.getGround().getDisplayChar() == 'b') {
                            Bush currentBush = (Bush) newLocation.getGround();
                            if (currentBush.getFruits() > 0){
                                foundFood = true;
                                int distance = distance(stegLocation, newLocation);
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
                                int distance = distance(stegLocation, newLocation);
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

    private int distance(Location a, Location b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }

    private String eatFood(Actor actor, GameMap map, Location fruitLocation){
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

//            Fruit fruit = new Fruit();
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
