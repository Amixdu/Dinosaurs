package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

/**
 * Implementation of AllosaurAttackAction
 * @author Amindu Kaushal Kumarasinghe
 * @author Abhishek Shrestha
 */
public class AllosaurAttackAction extends Action {
    /**
     * The Allosaur that is attacking
     */
    private Actor attacker;
    /**
     * The Stegosaur that is being attacked
     */
    private Actor target;
    /**
     * Amount of HP reduced for Stegosaur
     */
    private int damage;
    /**
     * Amount of HP increased for Allosaur
     */
    private int heal;

    /**
     * Constructor
     * @param attacker The Allosaur that is attacking
     * @param target The Stegosaur that is being attacked
     * @param damage Amount of HP reduced for Stegosaur
     * @param heal Amount of HP increased for Allosaur
     */
    public AllosaurAttackAction(Actor attacker, Actor target, int damage, int heal) {
        this.attacker = attacker;
        this.target = target;
        this.damage = damage;
        this.heal = heal;
    }

    /**
     * Perform the AllosaurAttackAction.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Stegosaur steg = (Stegosaur) target;
        int beforeAttack = steg.getHitPoints();
        target.hurt(damage);
        actor.heal(heal);
        return attacker.toString() + " attacks " + target.toString() + " " + steg.getName() + "'s HP reduces from " + beforeAttack + " to " + steg.getHitPoints() + ")";

    }

    /**
     * Returns a descriptive string
     * @param actor The actor performing the action.
     * @return the text we put on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return null;
    }
}
