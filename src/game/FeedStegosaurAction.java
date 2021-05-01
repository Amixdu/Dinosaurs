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
        boolean success = false;
        boolean full = false;
        int beforeFeeding = stegosaur.getFoodLevel();
        int afterFeeding;
        // add fruit to inventory for testing
//        actor.addItemToInventory(new Fruit());
        for (int i = 0; i < inventory.size(); i++){
            if (inventory.get(i).getDisplayChar() == 'f'){
                actor.removeItemFromInventory(inventory.get(i));
                if (stegosaur.getFoodLevel() < 100){
                    stegosaur.increaseFoodLevel(20);
                    success = true;
                }
                else {
                    full = true;

                }
            }
        }

        if (success){
            afterFeeding = stegosaur.getFoodLevel();
            return (result + ", successfully fed (food level increased from "+beforeFeeding+" to "+afterFeeding+ ")");
        }
        else if (full){
            return (result + ", but " + stegosaur.getName() + " is full!");
        }

        return ("No fruit in inventory");

    }

    @Override
    public String menuDescription(Actor actor) {
        return actor.toString() + " feeds " + stegosaur.getName();
    }

}
