package game;

import edu.monash.fit2099.engine.*;
/**
 * Implementation of AllosaurAttackBehaviour
 * @author Amindu Kaushal Kumarasinghe
 * @author Abhishek Shrestha
 */
public class AllosaurAttackBehavior implements Behaviour{

    /**
     * Goes through th entire map, if an attackable stegosaur/pterodactyl is present, return a the appropriate action
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return a new AllosaurAttackAction or an EatPterodactylAction
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        Location current = map.locationOf(actor);
        SearchMap searchMap = new SearchMap(actor, map);
        Location stegLocation = searchMap.closest('S', "Actor");
        Location pteroLocation = searchMap.closest('P', "Actor");
        // if attacking a stegosaur is possible, an AllosaurAttackAction will be returned
        if (stegLocation != null){
            Stegosaur target = (Stegosaur) stegLocation.getActor();
            if (target.isConscious()){
                if (target.getAttackCoolDown() >= 20){
                    if (distance(current, stegLocation) == 1){
                        target.resetAttackCoolDown();
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
        if (pteroLocation != null){
            Pterodactyl target = (Pterodactyl) pteroLocation.getActor();
            // pterodactyl is conscious and not flying
            if ((target.isConscious()) && target.hasCapability(Flight.NO)){
                if (distance(current, pteroLocation) == 1){
                    return (new AllosaurEatPterodactylAction(actor, target));
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
