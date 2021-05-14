package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

public class MateAction extends Action {
    /**
     * target to mate with
     */
    private Actor target;

    /**
     * Constructor
     * @param target dinosaur to mate with
     */
    public MateAction(Actor target) {
        this.target = target;
    }

    /**
     * Method that lets the actor dino and target dino to mate
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Dinosaur targetDino = (Dinosaur) target;
        Dinosaur actorDino = (Dinosaur) actor;
        String output;
        // if of opposite sex
        if (targetDino.getSex() == Sex.Female && actorDino.getSex() == Sex.Male) {
            // mate
            if (!targetDino.getHasMated()){
                targetDino.setHasMated(true);
                output = menuDescription(actor);
            } else {
                output = target.toString() + " is pregnant. Cannot mate with her!";
            }

        } else if (targetDino.getSex() == Sex.Male && actorDino.getSex() == Sex.Female){
            // mate
            if (!actorDino.getHasMated()){
                actorDino.setHasMated(true);
                output = menuDescription(actor);
            } else {
                output = actor.toString() + " is pregnant. She cannot mate!";
            }
        } else {
            output = "Cannot mate!" + actor.toString() + " and " + target.toString() + " are of same sex.";
        }
        return output;
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor.toString() + " mated with " + target.toString();
    }
}
