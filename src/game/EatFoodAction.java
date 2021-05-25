package game;

import edu.monash.fit2099.engine.*;

import java.util.List;
/**
 * @author Amindu Kumarasinghe
 */
public class EatFoodAction extends Action {
    /**
     * Location of the target food
     */
    Location foodLocation;

    /**
     * constructor
     * @param foodLocation Location of the target food
     */
    public EatFoodAction(Location foodLocation) {
        this.foodLocation = foodLocation;
    }

    /**
     * This checks the appropriate foods for the matching dinosaurs and increases health points accordingly
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return an output message describing the result
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        String outputMessage = "";
        // for stegosaur
        if (actor.getDisplayChar() == 'S' || actor.getDisplayChar() == 's') {
            Stegosaur steg = (Stegosaur) actor;
            steg.heal(10);

            if (foodLocation.getGround().getDisplayChar() == 'b') {
                Bush bush = (Bush) foodLocation.getGround();
                bush.removeFruit();
            } else {
                List<Item> items = foodLocation.getItems();
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).getDisplayChar() == 'f') {
                        foodLocation.removeItem(items.get(i));
                        outputMessage = menuDescription(actor);
                        break;
                    }
                }
            }
        }
        // for brachiosuar
        else if (actor.getDisplayChar() == 'R' || actor.getDisplayChar() == 'r' ){
            // if brachiosaur
            Brachiosaur brach = (Brachiosaur) actor;
            Tree currentTree = (Tree) foodLocation.getGround();
            // brachiosaur eats all fruits
            // each fruit heals brach by 5
            brach.heal(currentTree.getFruits() * 5);

            // remove all fruits from tree
            currentTree.setFruits(0);
            outputMessage = menuDescription(actor);

        }
        // for allosaur
        else if (actor.getDisplayChar() == 'A' || actor.getDisplayChar() == 'a'){
            Allosaur allosaur = (Allosaur) actor;
            List<Item> items = foodLocation.getItems();
            for (int i = 0; i < items.size(); i++ ){
                if (items.get(i).getDisplayChar() == 'C') {
                    Corpse corpse = (Corpse) items.get(i);
                    char corpseType = corpse.getCorpseType();
                    if (corpseType == 'S' || corpseType =='s' || corpseType == 'A' || corpseType == 'a'){
                        allosaur.heal(50);
                        foodLocation.removeItem(items.get(i));
                        outputMessage = menuDescription(actor);
                        break;
                    }
                    else if (corpseType == 'R' || corpseType == 'r'){
                        allosaur.heal(100);
                        foodLocation.removeItem(items.get(i));
                        outputMessage = menuDescription(actor);
                        break;
                    }
                }
                // q = Stegosaur egg, w = Brachiosaur egg, y = Pterodactyl egg
                else if (items.get(i).getDisplayChar() == 'q' || items.get(i).getDisplayChar() == 'w'
                        || items.get(i).getDisplayChar() == 'y'){
                    allosaur.heal(10);
                    foodLocation.removeItem(items.get(i));
                    outputMessage = menuDescription(actor);
                    break;
                }
            }
        }
        // for pterodactyl
        else if (actor.getDisplayChar() == 'P' || actor.getDisplayChar() == 'p'){
            Pterodactyl pterodactyl = (Pterodactyl) actor;
            // eating fish
            // (this lake location would only have been sent here,if there was at least one fish in the lake)
            if (foodLocation.getGround().getDisplayChar() == '~'){
                Lake lake = (Lake) foodLocation.getGround();
                int fishCount = lake.getFishCount();
                if (fishCount >= 2){
                    // chance based system : 60% chance of catching only one fish,
                    // 60% chance of catching two and 20% chance of catching none.
                    double random = Math.random();
                    // chance for eating one fish
                    if (random > 0.4) {
                        lake.setFishCount(fishCount - 1);
                        pterodactyl.heal(5);
                        pterodactyl.setWaterLevel(pterodactyl.getWaterLevel() + 30);
                        outputMessage = actor.toString() + " at location (" + foodLocation.x() + "," +
                                foodLocation.y() + ") catches " + "1 fish";
                    }
                    // chance for eating two fish
                    else if (random >= 0.2 && random <= 0.4){
                        lake.setFishCount(fishCount - 2);
                        pterodactyl.heal(5 * 2);
                        pterodactyl.setWaterLevel(pterodactyl.getWaterLevel() + 60);
                        outputMessage = actor.toString() + " at location (" + foodLocation.x() + "," +
                                foodLocation.y() + ") catches " + "2 fish";
                    }
                    // chance for eating no fish
                    else if (random < 0.2) {
                        outputMessage = actor.toString() + " at location (" + foodLocation.x() + "," +
                                foodLocation.y() + ") doesnt catch any fish";
                        pterodactyl.setWaterLevel(pterodactyl.getWaterLevel() + 30);
                    }
                }
                // theres only one fish
                else if (fishCount == 1){
                    lake.setFishCount(fishCount - 1);
                    pterodactyl.heal(5);
                    pterodactyl.setWaterLevel(pterodactyl.getWaterLevel() + 30);
                    outputMessage = actor.toString() + " at location (" + foodLocation.x() + "," +
                            foodLocation.y() + ") catches " + "1 fish";
                }
            }

            // eating corpse or eggs
            List<Item> items = foodLocation.getItems();
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).getDisplayChar() == 'C') {
                    Corpse corpse = (Corpse) items.get(i);
                    int corspePoints = corpse.getCorpsePoints();
                    // count comparing with 1 to account for current round
                    if (corspePoints >= 10){
                        pterodactyl.heal(10);
                        corpse.setCorpsePoints(corspePoints - 10);
                        outputMessage = menuDescription(actor);
                        break;
                    }
                }
                // q = Stegosaur egg, w = Brachiosaur egg, a =Allosaur egg
                else if (items.get(i).getDisplayChar() == 'q' || items.get(i).getDisplayChar() == 'w' || items.get(i).getDisplayChar() == 'e'){
                    pterodactyl.heal(10);
                    foodLocation.removeItem(items.get(i));
                    outputMessage = menuDescription(actor);
                    break;
                }
            }
        }
        return outputMessage;
    }


    /**
     *
     * @param actor The actor performing the action.
     * @return A message that will be displayed after eating
     */
    @Override
    public String menuDescription(Actor actor) {
        return (actor.toString() + " at location(" + foodLocation.x() + ","  + foodLocation.y() + ") has eaten");
    }
}
