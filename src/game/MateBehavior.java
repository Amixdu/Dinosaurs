package game;

import edu.monash.fit2099.engine.*;

import java.util.List;

/**
 * Mate Behavior class
 * Another behavior that all dinosaurs have
 * @author Abhishek Shrestha
 */
public class MateBehavior implements Behaviour{

    /**
     * can return LayEggAction, MateAction (if adjacent), MoveActorAction (if nearby) or null
     * if a female has mated, she cannot mate until she lays the egg
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return Suitable action
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        char dinoChar = actor.getDisplayChar();
        Dinosaur dino = (Dinosaur) actor;
        Sex dinoSex = dino.getSex();
        Location actorLocation = map.locationOf(actor);


        // if female has mated successfully -> LayEggs
        if (dino.getHasMated() && dino.getAgeGroup() == AgeGroup.ADULT){
            // if enough time has passed to lay eggs
            if (dino.getTurnsSinceMate() >= dino.getTurnsToLayEgg()){
                // lay egg
                // if any other dinosaur except pterodactyl
                if (dinoChar != 'P'){
                    return new LayEggAction();
                }
                // if pterodactyl, needs to be on top of a tree
                else{
                    char actorLocationChar = actorLocation.getGround().getDisplayChar();
                    // if already on tree, lay egg
                    if (actorLocationChar == '+' || actorLocationChar == 't' || actorLocationChar == 'T'){
                        return new LayEggAction();
                    }
                    // else go towards tree
                    else{
                        SearchMap searchMap = new SearchMap(actor, map);
                        Location finalLoc = null;
                        Location location;
                        double minDist = Double.POSITIVE_INFINITY;
                        location = searchMap.closest('T', true);
                        if (location != null){
                            if (distance(location, actorLocation) < minDist){
                                minDist = distance(location, actorLocation);
                                finalLoc = location;
                            }
                        }

                        location = searchMap.closest('t', true);
                        if (location != null){
                            if (distance(location, actorLocation) < minDist){
                                minDist = distance(location, actorLocation);
                                finalLoc = location;
                            }
                        }
                        location = searchMap.closest('+', true);
                        if (location != null){
                            if (distance(location, actorLocation) < minDist){
                                minDist = distance(location, actorLocation);
                                finalLoc = location;
                            }
                        }
                        String name = "";
                        Location minLocation = actorLocation;
                        if (finalLoc != null){
                            int minDistance = distance(finalLoc, actorLocation);
                            for (Exit exit : map.locationOf(actor).getExits()) {
                                Location destination = exit.getDestination();
                                if (destination.canActorEnter(actor)) {
                                    int newDistance = distance(finalLoc, destination);
                                    if (newDistance < minDistance){
                                        minDistance = newDistance;
                                        minLocation = destination;
                                        name = exit.getName();
                                    }
                                }
                            }
                            return new MoveActorToMateAction(minLocation, name);
                        }

//                        if (finalLoc != null){
//                            for (Exit e : actorLocation.getExits()){
//                                Location destination = e.getDestination();
//                                if (destination.canActorEnter(actor)) {
//                                    String name = e.getName();
//                                    return new MovePterodactylToLayEggAction(finalLoc, name);
//                                }
//                            }
//
//                        }
                    }
                }

            } else{
                return null;
            }
        }

        // if adjacent and not pregnant -> Mate
        for (Exit e : actorLocation.getExits()){
            if (e.getDestination().containsAnActor()){
                Actor adjacentActor =  e.getDestination().getActor();
                if (adjacentActor.getDisplayChar() == dinoChar){
                    // if dino of same species is adjacent
                    Dinosaur adjacentDino = (Dinosaur) adjacentActor;
                    // if both dinos are of opposite sex and are adults
                    if (adjacentDino.getSex() != dinoSex && adjacentDino.getAgeGroup() == AgeGroup.ADULT
                            && dino.getAgeGroup() == AgeGroup.ADULT){
                        // if they have different sex
                        // if not pregnant
                        if (!adjacentDino.getHasMated() && !dino.getHasMated()){
                            // if the dino is a pterodactyl, then they need to be on trees for mating
                            if (dinoChar == 'P'){
                                char dinoLocationChar = map.locationOf(dino).getGround().getDisplayChar();
                                char adjDinoLocationChar = e.getDestination().getGround().getDisplayChar();
                                if ((dinoLocationChar == 'T' || dinoLocationChar == 't' || dinoLocationChar == '+') &&
                                        (adjDinoLocationChar == 'T' || adjDinoLocationChar == 't' || adjDinoLocationChar == '+')){
                                    return new MateAction(adjacentDino);
                                }
                            }
                            else{
                                return new MateAction(adjacentDino);
                            }
                        }

                    }
                }

            }
        }

        // if dino is nearby, minDistance will always be < 10. Thus, initializing to 10 for now.
        int minDistance = 10;
        boolean foundNearByDino = false;
        Location nextLocation = null;
        // if nearby (2 squares out)
        for (Exit e : actorLocation.getExits()){
            Location adjLocation = e.getDestination();
            for (Exit outerExit : adjLocation.getExits()){
                if (outerExit.getDestination().containsAnActor()){
                    Actor nearbyActor = outerExit.getDestination().getActor();
                    if (nearbyActor.getDisplayChar() == dinoChar){
                        // if dino of the same species are nearby
                        Dinosaur nearbyDino = (Dinosaur) nearbyActor;
                        // if both dinosaurs are adults
                        if (nearbyDino.getAgeGroup() == AgeGroup.ADULT && dino.getAgeGroup() == AgeGroup.ADULT){
                            if (nearbyDino.getSex() != dinoSex && !nearbyDino.getHasMated() && !dino.getHasMated()){
                                // if they have different sex and female dino is not pregnant
                                minDistance = distance(actorLocation, map.locationOf(nearbyActor));
                                foundNearByDino = true;
                                nextLocation = map.locationOf(nearbyActor);
                                break;
                            }
                        }
                    }
                }
            }
            if (foundNearByDino)
                break;
        }

        // if dino of opposite sex is nearby, move closer -> MoveActorAction
        String name = "";
        Location minLocation = actorLocation;
        if (foundNearByDino){
            for (Exit exit : map.locationOf(actor).getExits()) {
                Location destination = exit.getDestination();
                if (destination.canActorEnter(actor)) {
                    int newDistance = distance(nextLocation, destination);
                    if (newDistance < minDistance){
                        minDistance = newDistance;
                        minLocation = destination;
                        name = exit.getName();
                    }
                }
            }
            return new MoveActorToMateAction(minLocation, name);
        } else {
            return null;
        }
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
