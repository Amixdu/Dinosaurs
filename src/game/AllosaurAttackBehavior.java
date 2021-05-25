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
        Location actorLocation = map.locationOf(actor);

        // if adjacent and not pregnant -> Mate
        for (Exit e : actorLocation.getExits()){
            if (e.getDestination().containsAnActor()){
                Actor adjacentActor =  e.getDestination().getActor();
                if (adjacentActor.getDisplayChar() == 'P' || adjacentActor.getDisplayChar() == 'p'){
                    Pterodactyl target = (Pterodactyl) adjacentActor;
                    // if Pterodactyl is flying, it is safe from allosaur attacks
                    if (target.hasCapability(Flight.YES)){
                        System.out.println(actor.toString() + " spots " + target.toString() + " nearby, but it is flying!");
                    }
                    // pterodactyl is conscious
                    if ((target.isConscious())){
                        return (new AllosaurEatPterodactylAction(actor, target));
                    }
                }
                if (adjacentActor.getDisplayChar() == 'S' || adjacentActor.getDisplayChar() == 's'){
                    // if attacking a stegosaur is possible, an AllosaurAttackAction will be returned
                    Stegosaur target = (Stegosaur) adjacentActor;
                    if (target.isConscious()){
                        if (target.getAttackCoolDown() >= 20){
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
