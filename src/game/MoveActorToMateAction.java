package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.MoveActorAction;

/**
 * Move Actor to Mate Action class
 * To Differentiate if an actor is moving to mate or due to another behavior.
 * @author Abhishek Shreshta
 */
public class MoveActorToMateAction extends MoveActorAction {
    /**
     * Construtor
     * @param moveToLocation location to move to
     * @param direction direction of the move
     */
    public MoveActorToMateAction(Location moveToLocation, String direction) {
        super(moveToLocation, direction);
    }

    /**
     * Returns a descriptive string
     * @param actor The actor performing the action.
     * @return the text we put on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor.toString() + " moves " + direction + " to mate.";
    }
}
