package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

import java.util.List;

public class FeedCarnivoreAction extends Action {

    CarnivorousDinosaur carnivorousDinosaur;

    public FeedCarnivoreAction(CarnivorousDinosaur dinosaur) {
        this.carnivorousDinosaur = dinosaur;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        String result = actor.toString() + " tries to feed " + carnivorousDinosaur.getName();
        List<Item> inventory = actor.getInventory();

        int beforeFeeding = carnivorousDinosaur.getHitPoints();
        int afterFeeding;
        // add CarnivoreMealKit to inventory for testing
        actor.addItemToInventory(new CarnivoreMealKit());


        // Cant feed if stegosaur is already full
        if (carnivorousDinosaur.getHitPoints() >= carnivorousDinosaur.getMaxHitPoints()){
            return (result + ", but " + carnivorousDinosaur.getName() + " is full!");
        }

        // Going through inventory searching for fruit, feed if found
        for (int i = 0; i < inventory.size(); i++){
            if (inventory.get(i).getDisplayChar() == 'c'){
                actor.removeItemFromInventory(inventory.get(i));
                carnivorousDinosaur.heal(carnivorousDinosaur.getMaxHitPoints());
                afterFeeding = carnivorousDinosaur.getHitPoints();
                return (result + ", successfully fed (Hit points increased from " + beforeFeeding + " to " + afterFeeding + ")");
            }
        }


        return ("No carnivore meal kits in inventory");
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor.toString() + " feeds " + carnivorousDinosaur.getName();
    }


}
