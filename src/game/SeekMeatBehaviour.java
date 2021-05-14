package game;

import edu.monash.fit2099.engine.*;

import java.util.List;

/**
 * Implementation of SeekMeatBehaviourClass
 * @author Amindu Kaushal Kumarasinghe
 * @author Abhishek Shrestha
 */
public class SeekMeatBehaviour implements Behaviour {
    /**
     * indicates whether allosaur is adult or baby
     */
    private boolean adult;

    /**
     * Constructor
     * @param adult indicates whether allosaur is adult or baby
     */
    public SeekMeatBehaviour(boolean adult) {
        this.adult = adult;
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
        // Tuple consists of the location and the char representing the type of corpse or egg
        Tuple data = closestFood(actor, map);

        if (data == null){
            System.out.println("There is no meat available in map");
            return null;
        }
        Location closestMeat = data.getFirst();
        char type = data.getSecond();

        int minDistance = distance(closestMeat, map.locationOf(actor));
        String name ="";
        if (minDistance == 0){
            // On same ground as meat, so eating it
            name = eatFood(actor, map, closestMeat, type);
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

    /**
     * Gets the location of the closes food from the whole map
     * @param actor vegetarian dinosaur seeking for fruit
     * @param map map on which the dinosaur is currently on
     * @return location of closest food when calculated from current position of dinosaur
     */
    private Tuple closestFood(Actor actor,GameMap map){
        boolean foundFood = false;
        NumberRange width = map.getXRange();
        NumberRange height = map.getYRange();
        Location allosaurLocation = map.locationOf(actor);
        Tuple tuple = firstLocationWithFood(map);
        if (tuple != null){
            Location bestLocation = tuple.getFirst();
            char type = tuple.getSecond();
            if (bestLocation != null){
                int minDistance = distance(allosaurLocation, bestLocation);
                for (int i : width){
                    for (int j : height){
                        Location newLocation = map.at(i, j);
                        if (newLocation.getGround() != null){
                            // get list of items at loaton
                            List<Item> items = newLocation.getItems();
                            for (Item item : items){
                                // if a corpse is found at location
                                if (item.getDisplayChar() == 'C'){
                                    foundFood = true;
                                    int distance = distance(allosaurLocation, newLocation);
                                    // check distance and update
                                    if (distance < minDistance){
                                        minDistance = distance;
                                        bestLocation = newLocation;
                                        Corpse corpse = (Corpse) item;
                                        type = corpse.getCorpseType();
                                    }
                                }
                                // if a stegosaur egg is found
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
                                // if a brachiosaur egg is found
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
                return new Tuple(bestLocation, type);
            }
        }

        return null;
    }

    /**
     * Used to get the first location in the map that contains a corpse
     * @param map map which dinosaur is on
     * @return first location in the map that contains a corpse
     */
    private Tuple firstLocationWithFood(GameMap map){
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
                            // return location and the type of dinosaur who died
                            return new Tuple(newLocation, corpse.getCorpseType());
                        }
                        // q = Stegosaur egg
                        else if (item.getDisplayChar() == 'q'){
                            return new Tuple(newLocation, 'q');
                        }
                        // w = Brachiosaur egg
                        else if (item.getDisplayChar() == 'w'){
                            return new Tuple(newLocation, 'w');
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
     * Heals allosaur (baby or adult) accordingly with the type of food eatne
     * @param actor Dinosaur (Adult allosaur or Baby allosaur)
     * @param map Map that dinosaur is on
     * @param corpseLocation location of closest corpse
     * @param type type of food (Allosaur corpse/ Stegosaur corpse/ Stegosaur eggs)
     * @return String saying nowhere, since dinosaur wont go anywhere while eating
     */
    private String eatFood(Actor actor, GameMap map, Location corpseLocation, char type){
        if(adult){
            Allosaur allosaur = (Allosaur) actor;
            if (type == 'S' || type =='s' || type == 'A' || type == 'a'){
                allosaur.heal(100);
            }
            else if (type == 'R' || type == 'r'){
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
            else if (type == 'R' || type == 'r'){
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
            else if (items.get(i).getDisplayChar() == 'b'){
                corpseLocation.removeItem(items.get(i));
                System.out.println("Allosaur at (" + corpseLocation.x() + "," + corpseLocation.y() + ") eats");

            }
            else if (items.get(i).getDisplayChar() == 'w'){
                corpseLocation.removeItem(items.get(i));
                System.out.println("Allosaur at (" + corpseLocation.x() + "," + corpseLocation.y() + ") eats");

            }
        }
        return "nowhere";

    }
}
