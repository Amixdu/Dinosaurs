package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
/**
 * @author Amindu Kumarasinghe
 */
public class PterodactylRestAction extends Action {

    /**
     * Replenishes pterodactyls fuel(fly count) and add the capability for flight
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return A String describing the action
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Pterodactyl pterodactyl = (Pterodactyl) actor;
        pterodactyl.setFuel(30);
        pterodactyl.addCapability(Flight.YES);
        return (actor.toString() + " has rested and is able to fly now!");
    }

    /**
     *
     * @param actor The actor performing the action.
     * @return null
     */
    @Override
    public String menuDescription(Actor actor) {
        return null;
    }
}
