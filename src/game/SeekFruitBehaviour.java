package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;

public class SeekFruitBehaviour implements Behaviour{

    @Override
    public Action getAction(Actor actor, GameMap map) {
        Boolean success = false;
        Location minLocation = map.locationOf(actor);
        Location closestFruit = closestFood(actor, map);
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
                    success = true;
                }

            }
        }
        if (success){
            return new MoveActorAction(minLocation, name);
        }
        else{
            return null;
        }

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

                        int distance = distance(stegLocation, newLocation);
                        if (distance < minDistance){

                            minDistance = distance;
                            bestLocation = newLocation;
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
}
