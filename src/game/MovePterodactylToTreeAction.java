package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.MoveActorAction;

/**
 * @author Amindu Kumarasinghe
 */
public class MovePterodactylToTreeAction extends MoveActorAction {
    /**
     * A string indicating why the pterodactyl is looking for the tree (laying egg/resting)
     */
    String purpose;

    /**
     * Constructor
     * @param moveToLocation Target location
     * @param direction Direction of target location
     * @param purpose A string indicating why the pterodactyl is looking for the tree (laying egg/resting)
     */
    public MovePterodactylToTreeAction(Location moveToLocation, String direction, String purpose) {
        super(moveToLocation, direction);
        this.purpose = purpose;
    }

    /**
     * Method to describe the action
     * @param actor The actor performing the action.
     * @return A string describing the action
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor.toString() + " moves " + direction + " towards tree for " + purpose;
    }
}
