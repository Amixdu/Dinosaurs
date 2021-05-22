package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.MoveActorAction;

public class MovePterodactylToTreeAction extends MoveActorAction {
    String purpose;


    public MovePterodactylToTreeAction(Location moveToLocation, String direction, String purpose) {
        super(moveToLocation, direction);
        this.purpose = purpose;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        return super.execute(actor, map);
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor.toString() + " moves " + direction + " towards tree for " + purpose;
    }
}
