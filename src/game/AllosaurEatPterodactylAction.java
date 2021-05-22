package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

public class AllosaurEatPterodactylAction extends Action {
    Actor attacker;
    Actor target;

    public AllosaurEatPterodactylAction(Actor attacker, Actor target) {
        this.attacker = attacker;
        this.target = target;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        // allosaur gets fully healed
        attacker.heal(100);
        map.removeActor(target);
        return attacker.toString() + " eats " + target.toString();
    }

    @Override
    public String menuDescription(Actor actor) {
        return null;
    }
}
