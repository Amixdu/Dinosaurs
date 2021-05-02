package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

import java.util.List;

public class FeedStegosaurMealKitAction extends Action {
    protected Stegosaur stegosaur;

    public FeedStegosaurMealKitAction(Stegosaur stegosaur) {
        this.stegosaur = stegosaur;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        String result = actor.toString() + " tries to feed " + stegosaur.getName();
        List<Item> inventory = actor.getInventory();
        int beforeFeeding = stegosaur.getFoodLevel();
        // add fruit to inventory for testing
        actor.addItemToInventory(new VegetarianMealKit());

        // Cant feed if stegosaur is already full
        if (stegosaur.getFoodLevel() >= 100){
            return (result + ", but " + stegosaur.getName() + " is full!");
        }

        // Going through inventory searching for fruit, feed if found
        for (int i = 0; i < inventory.size(); i++){
            if (inventory.get(i).getDisplayChar() == 'v'){
                actor.removeItemFromInventory(inventory.get(i));
                stegosaur.increaseFoodLevel(100);
                return (result + ", successfully fed (food level increased from "+beforeFeeding+" to "+ "max food level" + ")");
            }
        }

        return ("No vegetarian meal kits in inventory");
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor.toString() + " feeds vegetarian meal kit" + stegosaur.getName();
    }
}
