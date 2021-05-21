package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.MoveActorAction;

public class MovePterodactylToLayEggAction extends MoveActorAction {

    public MovePterodactylToLayEggAction(Location moveToLocation, String direction) {
        super(moveToLocation, direction);
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor.toString() + " moves " + direction + " towards tree to lay egg.";
    }
}
