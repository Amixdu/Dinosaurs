package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;
import java.util.List;

public class SeekMeatBehaviour implements Behaviour {
    private boolean adult;

    public SeekMeatBehaviour(boolean adult) {
        this.adult = adult;
    }

    @Override
    public Action getAction(Actor actor, GameMap map) {

        Location minLocation = map.locationOf(actor);
        Tuple data = closestFood(actor, map);

        if (data == null){
            System.out.println("There is no meat available in map");
            return null;
        }
        Location closestMeat = data.getFirst();
        char type = data.getSecond();



        // to test fruits in bush before eating
//        Bush bush = (Bush) closestMeat.getGround();
//        System.out.println(bush.getFruits());

        // to test location of closest fruit
        System.out.println("x:"+closestMeat.x() + "y:" + closestMeat.y());

        int minDistance = distance(closestMeat, map.locationOf(actor));
        String name ="";
        if (minDistance == 0){
            // On same ground as fruit, so eating it
            name = eatFood(actor, map, closestMeat, type);
            // to test fruits in bush before eating
//            System.out.println(bush.getFruits());

        }



        for (Exit exit : map.locationOf(actor).getExits()) {
            Location destination = exit.getDestination();
            if (destination.canActorEnter(actor)) {
                int newDistance = distance(closestMeat, destination);
                if (newDistance < minDistance){
                    minDistance = newDistance;
                    minLocation = destination;
                    name = exit.getName();
                }

            }
        }


        return new MoveActorAction(minLocation, name);

    }

    public Tuple closestFood(Actor actor,GameMap map){
        boolean foundFood = false;
        NumberRange width = map.getXRange();
        NumberRange height = map.getYRange();
        Location allosaurLocation = map.locationOf(actor);
        Tuple tuple = firstLocationWithFood(actor, map);
        if (tuple != null){
            Location bestLocation = tuple.getFirst();
            char type = tuple.getSecond();
            if (bestLocation != null){
                int minDistance = distance(allosaurLocation, bestLocation);
                for (int i : width){
                    for (int j : height){
                        Location newLocation = map.at(i, j);
                        if (newLocation.getGround() != null){
                            List<Item> items = newLocation.getItems();
                            for (Item item : items){

                                if (item.getDisplayChar() == 'C'){
                                    foundFood = true;
                                    int distance = distance(allosaurLocation, newLocation);
                                    if (distance < minDistance){
                                        minDistance = distance;
                                        bestLocation = newLocation;
                                        Corpse corpse = (Corpse) item;
                                        type = corpse.getCorpseType();
                                    }
                                }
                                else if (item.getDisplayChar() == 'q'){
                                    foundFood = true;
                                    int distance = distance(allosaurLocation, newLocation);
                                    if (distance < minDistance){
                                        minDistance = distance;
                                        bestLocation = newLocation;
                                        Corpse corpse = (Corpse) item;
                                        type = corpse.getCorpseType();
                                    }
                                }
                                else if (item.getDisplayChar() == 'w'){
                                    foundFood = true;
                                    int distance = distance(allosaurLocation, newLocation);
                                    if (distance < minDistance){
                                        minDistance = distance;
                                        bestLocation = newLocation;
                                        Corpse corpse = (Corpse) item;
                                        type = corpse.getCorpseType();
                                    }
                                }
                            }

                        }
                    }
                }
            }

            if (foundFood){
                Tuple returnTuple = new Tuple(bestLocation, type);
                return returnTuple;
            }
        }

        return null;
    }

    private Tuple firstLocationWithFood(Actor actor, GameMap map){
        NumberRange width = map.getXRange();
        NumberRange height = map.getYRange();
        for (int i : width){
            for (int j : height){
                Location newLocation = map.at(i, j);
                if (newLocation.getGround() != null){
                    List<Item> items = newLocation.getItems();
                    for (Item item : items){
                        if (item.getDisplayChar() == 'C'){
                            Corpse corpse = (Corpse) item;
                            Tuple tuple = new Tuple(newLocation, corpse.getCorpseType());
                            return tuple;
                        }
                        // q = Stegosaur egg
                        else if (item.getDisplayChar() == 'q'){
                            Tuple tuple = new Tuple(newLocation, 'q');
                            return tuple;
                        }
                        // w = Brachiosaur egg
                        else if (item.getDisplayChar() == 'w'){
                            Tuple tuple = new Tuple(newLocation, 'w');
                            return tuple;
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

    private String eatFood(Actor actor, GameMap map, Location corpseLocation, char type){
        if(adult){
            Allosaur allosaur = (Allosaur) actor;
            if (type == 'S' || type =='s' || type == 'A' || type == 'a'){
                allosaur.heal(100);
            }
            else if (type == 'B' || type == 'b'){
                allosaur.heal(100);
            }
            else if (type == 'q' || type == 'w'){
                allosaur.heal(10);
            }
        }
        else{
            BabyAllosaur babyAllosaur = (BabyAllosaur) actor;
            if (type == 'S' || type =='s' || type == 'A' || type == 'a'){
                babyAllosaur.heal(50);
            }
            else if (type == 'B' || type == 'b'){
                babyAllosaur.heal(100);
            }
            else if (type == 'q' || type == 'w'){
                babyAllosaur.heal(10);
            }
        }


        List<Item> items = corpseLocation.getItems();
        for (int i = 0; i<items.size(); i++ ){
            if (items.get(i).getDisplayChar() == 'C'){
                corpseLocation.removeItem(items.get(i));
                System.out.println("Allosaur at (" + corpseLocation.x() + "," + corpseLocation.y() + ") eats");

            }
            if (items.get(i).getDisplayChar() == 'b'){
                corpseLocation.removeItem(items.get(i));
                System.out.println("Allosaur at (" + corpseLocation.x() + "," + corpseLocation.y() + ") eats");

            }
            if (items.get(i).getDisplayChar() == 'w'){
                corpseLocation.removeItem(items.get(i));
                System.out.println("Allosaur at (" + corpseLocation.x() + "," + corpseLocation.y() + ") eats");

            }
        }

        return "nowhere";

    }
}
