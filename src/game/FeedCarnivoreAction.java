package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

import java.util.List;

/**
 * Implement the FeedCarnivoreAction class
 * @author Amindu Kaushal Kumarasinghe
 */
public class FeedCarnivoreAction extends Action {

    /**
     * The dinosaur to feed
     */
    private CarnivorousDinosaur carnivorousDinosaur;

    /**
     * Constructor
     *
     * @param dinosaur carnivorous dinosaur to feed
     */
    public FeedCarnivoreAction(CarnivorousDinosaur dinosaur) {
        this.carnivorousDinosaur = dinosaur;
    }

    /**
     * Perform the Feed Action.
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a description of what happened that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        String result = actor.toString() + " tries to feed " + carnivorousDinosaur.getName();
        List<Item> inventory = actor.getInventory();

        int beforeFeeding = carnivorousDinosaur.getHitPoints();
        int afterFeeding;


        // Cant feed if stegosaur is already full
        if (carnivorousDinosaur.getHitPoints() >= carnivorousDinosaur.getMaxHitPoints()){
            return (result + ", but " + carnivorousDinosaur.getName() + " is full!");
        }

        // Going through inventory searching for carnivore meal kit, feed if found
        for (int i = 0; i < inventory.size(); i++){
            if (inventory.get(i).getDisplayChar() == 'c'){
                actor.removeItemFromInventory(inventory.get(i));
                carnivorousDinosaur.heal(carnivorousDinosaur.getMaxHitPoints());
                afterFeeding = carnivorousDinosaur.getHitPoints();
                return (result + ", successfully fed (Hit points increased from " + beforeFeeding + " to " + afterFeeding + ")");
            }
        }
        return ("No carnivore carnivore meal kits in inventory");
    }

    /**
     * Returns a descriptive string
     * @param actor The actor performing the action.
     * @return the text we put on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor.toString() + " feeds " + carnivorousDinosaur.getName();
    }


}
