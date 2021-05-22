package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

public class PterodactylRestAction extends Action {
    @Override
    public String execute(Actor actor, GameMap map) {
        Pterodactyl pterodactyl = (Pterodactyl) actor;
        pterodactyl.setFuel(25);
        pterodactyl.addCapability(Flight.YES);
        return (actor.toString() + " has rested and is able to fly now!");
    }

    @Override
    public String menuDescription(Actor actor) {
        return null;
    }
}
