package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * Class that allows player to quit game
 * @author Abhishek Shreshta
 */
public class QuitGameAction extends Action {
    /**
     * Quit the game
     *
     * @param actor The player
     * @param map The map the player is on.
     * @return a description that says player quit
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        map.removeActor(actor);
        return actor.toString() + " " + menuDescription(actor);
    }

    /**
     * Returns a string says player quit
     * @param actor The player
     * @return the text we put on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return "Quit Game";
    }
}
