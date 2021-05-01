package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;

public class SeekFruitBehaviour implements Behaviour{

    @Override
    public Action getAction(Actor actor, GameMap map) {

        Location minLocation = map.locationOf(actor);
        Location closestFruit = closestFood(actor, map);

        // to test fruits in bush before eating
//        Bush bush = (Bush) closestFruit.getGround();
//        System.out.println(bush.getFruits());

        // to test location of fruit
//        System.out.println("x:"+closestFruit.x() + "y:" + closestFruit.y());
        int minDistance = distance(closestFruit, map.locationOf(actor));
        String name ="";
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

        if (minDistance == 0){
            // On same ground as fruit, so eating it
            eatFood(actor, map, closestFruit);
            // to test fruits in bush before eating
//            System.out.println(bush.getFruits());

        }
        return new MoveActorAction(minLocation, name);

    }

    public Location closestFood(Actor actor,GameMap map){
        NumberRange width = map.getXRange();
        NumberRange height = map.getYRange();
        Location stegLocation = map.locationOf(actor);
        Location bestLocation = new Location(map, width.min(), height.min());
        int minDistance = distance(stegLocation, bestLocation);
        for (int i : width){
            for (int j : height){
                Location newLocation = map.at(i, j);
                if (newLocation.getGround() != null){
                    if (newLocation.getGround().getDisplayChar() == 'b'){
                        Bush currentBush = (Bush) newLocation.getGround();
                        if (currentBush.getFruits() > 0){
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
        return bestLocation;
    }

    private int distance(Location a, Location b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }

    private void eatFood(Actor actor, GameMap map, Location fruitLocation){
        Stegosaur steg = (Stegosaur) actor;
        steg.increaseFoodLevel(10);
        Bush bush = (Bush) fruitLocation.getGround();
        bush.removeFruit();
    }
}
