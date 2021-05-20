package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.MoveActorAction;

public class MoveActorToConsumeAction extends MoveActorAction {
    String consumeType;
    public MoveActorToConsumeAction(Location moveToLocation, String direction, String consumeType) {
        super(moveToLocation, direction);
        this.consumeType = consumeType;
    }

    /**
     * Returns a descriptive string
     * @param actor The actor performing the action.
     * @return the text we put on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor.toString() + " moves " + direction + " to consume " + consumeType;
    }
}
