package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

import java.util.List;
import java.util.Scanner;

public class FeedVegetarianAction extends Action {
    VegetarianDinosaur vegetarianDinosaur;

    /**
     * Constructor
     * @param dinosaur vegetarian dinosaur to feed
     */
    public FeedVegetarianAction(VegetarianDinosaur dinosaur) {
        this.vegetarianDinosaur = dinosaur;
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

        String result = actor.toString() + " tries to feed " + vegetarianDinosaur.getName();
        List<Item> inventory = actor.getInventory();

        boolean hasFruits = false;
        boolean hasVmKits = false;
        int beforeFeeding = vegetarianDinosaur.getHitPoints();
        int afterFeeding;
        // add fruit to inventory for testing
//        actor.addItemToInventory(new Fruit());
//        actor.addItemToInventory(new VegetarianMealKit());


        // Cant feed if stegosaur is already full
        if (vegetarianDinosaur.getHitPoints() >= 100){
            return (result + ", but " + vegetarianDinosaur.getName() + " is full!");
        }

        int fruitPosition = 0;
        int mealKitPosition = 0;
        // Going through inventory searching for fruit or vegetarian meal kits
        for (int i = 0; i < inventory.size(); i++){
            if (inventory.get(i).getDisplayChar() == 'f'){
                hasFruits = true;
                fruitPosition = i;
            }
            else if (inventory.get(i).getDisplayChar() == 'v'){
                hasVmKits = true;
                mealKitPosition = i;
            }
        }

        if (hasFruits && hasVmKits){
            String option = userInput();
            if (option.equals("A")){
                afterFeeding = feedFruit(actor);
                actor.removeItemFromInventory(inventory.get(fruitPosition));
                return (result + ", successfully fed (Hit points increased from "+beforeFeeding+" to "+afterFeeding+ ")");
            }
            else if(option.equals("B")){
                afterFeeding = feedMealKit();
                actor.removeItemFromInventory(inventory.get(mealKitPosition));
                return (result + ", successfully fed (Hit points increased from "+beforeFeeding+" to "+afterFeeding+ ")");
            }
            else{
                return "Enter a valid input";
            }
        }
        else if(hasFruits && !hasVmKits){
            afterFeeding = feedFruit(actor);
            actor.removeItemFromInventory(inventory.get(fruitPosition));
            return (result + ", successfully fed (Hit points increased from "+beforeFeeding+" to "+afterFeeding+ ")");
        }
        else if(!hasFruits && hasVmKits){
            afterFeeding = feedMealKit();
            actor.removeItemFromInventory(inventory.get(fruitPosition));
            return (result + ", successfully fed (Hit points increased from "+beforeFeeding+" to "+afterFeeding+ ")");
        }
        return ("No feedable items in inventory");

    }

    @Override
    public String menuDescription(Actor actor) {
        return actor.toString() + " feeds " + vegetarianDinosaur.getName();
    }

    /**
     * Method to get user input on whether to feed fruit or vegetarian meal kit
     * @return string represnting user input
     */
    public String userInput(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press A to feed fruit\nPress B to feed vegetarian meal kit");
        String result = scanner.next();
        return result;

    }

    /**
     * Heals dinosaur from fruit and increases players eco points
     * @param actor Player that is feeding
     * @return Amount of hit points after healing
     */
    public int feedFruit(Actor actor){
        Player player = (Player) actor;
        vegetarianDinosaur.heal(20);
        player.increaseEcoPoints(1);
        return vegetarianDinosaur.getHitPoints();
    }

    /**eals dinosaur from vegetarian meal kit and increases players eco points
     * @return Amount of hit points after healing
     */
    public int feedMealKit(){
        vegetarianDinosaur.heal(vegetarianDinosaur.getMaxHitPoints());
        return vegetarianDinosaur.getHitPoints();
    }
}
