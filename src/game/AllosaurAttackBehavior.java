package game;

import edu.monash.fit2099.engine.*;
/**
 * Implementation of AllosaurAttackBehaviour
 * @author Amindu Kaushal Kumarasinghe
 * @author Abhishek Shrestha
 */
public class AllosaurAttackBehavior implements Behaviour{

    /**
     * Goes through th entire map, if an attackable stegosaur is present, return a new AllosaurAttackAction
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return a new AllosaurAttackAction
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        NumberRange width = map.getXRange();
        NumberRange height = map.getYRange();
        Location current = map.locationOf(actor);
        for(int i : width){
            for (int j : height){
                Location newLocation = map.at(i, j);
                if (newLocation.getGround() != null){
                    if (newLocation.containsAnActor()){
                        Actor target = newLocation.getActor();
                        char targetChar = newLocation.getActor().getDisplayChar();
                        // If a stegosaur or a baby stegosaur in current location
                        if (targetChar == 'S' || targetChar == 's'){
                            Stegosaur steg = (Stegosaur) target;
                            // Check if conscious
                            if (target.isConscious()){
                                // Check if has already been attacked within the previous 20 rounds
                                if (steg.getAttackCoolDown() >= 20){
                                    if (distance(current, newLocation) == 1){
                                        // Resetting attack cool down
                                        steg.resetAttackCoolDown();
                                        Dinosaur dino = (Dinosaur) actor;
                                        int damage = 20;
                                        // if attacker is a baby allosaur, only do 10 damage and heal 10 points
                                        if (dino.getAgeGroup() == AgeGroup.BABY)
                                            damage = 10;
                                        return (new AllosaurAttackAction(actor, target, damage, damage));
                                    }
                                }
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
