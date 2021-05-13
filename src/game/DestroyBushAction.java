package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;

import java.util.Random;

public class DestroyBushAction extends Action {
    /**
     * target that can be destroyed (if bush)
     */
    Ground target;
    /**
     * Instance of random
     */
    Random rand = new Random();

    public DestroyBushAction(Ground currentGround) {
        target = currentGround;
    }

    /**
     * Brachiosaur destroys bush with 50% chance if it steps on it
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if (target.getDisplayChar() == 'b' && rand.nextBoolean()){
            // success
            map.locationOf(actor).setGround(new Dirt());
            System.out.println(menuDescription(actor));
        }
        return null;
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor.toString() + " stepped on a bush and destroyed it";
    }
}
