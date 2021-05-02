package game;

import edu.monash.fit2099.engine.*;

import java.util.List;

public class FeedStegosaurAction extends Action {
    protected Stegosaur stegosaur;

    public FeedStegosaurAction(Stegosaur stegosaur) {
        this.stegosaur = stegosaur;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        String result = actor.toString() + " tries to feed " + stegosaur.getName();
        List<Item> inventory = actor.getInventory();
        int beforeFeeding = stegosaur.getFoodLevel();
        int afterFeeding;
        // add fruit to inventory for testing
        actor.addItemToInventory(new Fruit());

        // Cant feed if stegosaur is already full
        if (stegosaur.getFoodLevel() >= 100){
            return (result + ", but " + stegosaur.getName() + " is full!");
        }

        // Going through inventory searching for fruit, feed if found
        for (int i = 0; i < inventory.size(); i++){
            if (inventory.get(i).getDisplayChar() == 'f'){
                actor.removeItemFromInventory(inventory.get(i));
                stegosaur.increaseFoodLevel(20);
                afterFeeding = stegosaur.getFoodLevel();
                return (result + ", successfully fed (food level increased from "+beforeFeeding+" to "+afterFeeding+ ")");
            }
        }

        return ("No fruit in inventory");

    }

    @Override
    public String menuDescription(Actor actor) {
        return actor.toString() + " feeds " + stegosaur.getName();
    }

}
