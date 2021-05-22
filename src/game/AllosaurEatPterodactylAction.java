package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * @author Amindu Kumarasinghe
 */
public class AllosaurEatPterodactylAction extends Action {
    /**
     * The actor who is attacking (allosaur)
     */
    Actor attacker;

    /**
     * The actor who is being eaten (pterodactyl)
     */
    Actor target;

    /**
     * Constructor
     * @param attacker Allosaur who is trying to eat
     * @param target Pterodactyl who is being eaten
     */
    public AllosaurEatPterodactylAction(Actor attacker, Actor target) {
        this.attacker = attacker;
        this.target = target;
    }

    /**
     * Heals allosaur and removes the pterodactyl off teh map
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a success message of eating
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        // allosaur gets fully healed
        attacker.heal(100);
        map.removeActor(target);
        return attacker.toString() + " eats " + target.toString();
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
