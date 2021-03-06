package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

/**
 * Lay Egg Action class
 * Only called for pregnant Female Dinosaurs
 * @author Abhishek Shreshta
 */
public class LayEggAction extends Action {
    /**
     * Lay the correct egg for the given dinosaur type
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Location actorLocation = map.locationOf(actor);
        char dinoType = actor.getDisplayChar();
        if (dinoType == 'S'){
            actorLocation.addItem(new StegosaurEgg());
        } else if (dinoType == 'R'){
            actorLocation.addItem(new BrachiosaurEgg());
        } else if (dinoType == 'A'){
            actorLocation.addItem(new AllosaurEgg());
        }
        else if (dinoType == 'P'){
            actorLocation.addItem(new PterodactylEgg());
        }
        Dinosaur dino = (Dinosaur) actor;
        dino.setHasMated(false);
        dino.setTurnsSinceMate(0);
        return menuDescription(actor);
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor.toString() + " lays an egg!";
    }
}
