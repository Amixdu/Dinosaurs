package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

import java.util.List;
import java.util.Scanner;

public class FeedVegetarianAction extends Action {
    VegetarianDinosaur vegetarianDinosaur;
    String message;

    public FeedVegetarianAction(VegetarianDinosaur dinosaur) {
        this.vegetarianDinosaur = dinosaur;
    }

    @Override
    public String execute(Actor actor, GameMap map) {

        String result = actor.toString() + " tries to feed " + vegetarianDinosaur.getName();
        List<Item> inventory = actor.getInventory();

        boolean hasFruits = false;
        boolean hasVmKits = false;
        int beforeFeeding = vegetarianDinosaur.getFoodLevel();
        int afterFeeding;
        // add fruit to inventory for testing
//        actor.addItemToInventory(new Fruit());
//        actor.addItemToInventory(new VegetarianMealKit());


        // Cant feed if stegosaur is already full
        if (vegetarianDinosaur.getFoodLevel() >= 100){
            return (result + ", but " + vegetarianDinosaur.getName() + " is full!");
        }

        int fruitPosition = 0;
        int mealKitPosition = 0;
        // Going through inventory searching for fruit, feed if found
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
                return (result + ", successfully fed (food level increased from "+beforeFeeding+" to "+afterFeeding+ ")");
            }
            else if(option.equals("B")){
                afterFeeding = feedMealKit(actor);
                actor.removeItemFromInventory(inventory.get(mealKitPosition));
                return (result + ", successfully fed (food level increased from "+beforeFeeding+" to "+afterFeeding+ ")");
            }
            else{
                return "Enter a valid input";
            }
        }
        else if(hasFruits && !hasVmKits){
            afterFeeding = feedFruit(actor);
            actor.removeItemFromInventory(inventory.get(fruitPosition));
            return (result + ", successfully fed (food level increased from "+beforeFeeding+" to "+afterFeeding+ ")");
        }
        else if(!hasFruits && hasVmKits){
            afterFeeding = feedMealKit(actor);
            actor.removeItemFromInventory(inventory.get(fruitPosition));
            return (result + ", successfully fed (food level increased from "+beforeFeeding+" to "+afterFeeding+ ")");
        }
        return ("No fruit in inventory");

    }

    @Override
    public String menuDescription(Actor actor) {
        return actor.toString() + " feeds " + vegetarianDinosaur.getName();
    }

    public String userInput(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press A to feed fruit\nPress B to feed vegetarian meal kit");
        String result = scanner.next();
        return result;

    }

    public int feedFruit(Actor actor){
        Player player = (Player) actor;
        vegetarianDinosaur.increaseFoodLevel(20);
        player.increaseEcoPoints(1000);
        return vegetarianDinosaur.getFoodLevel();
    }

    public int feedMealKit(Actor actor){
        vegetarianDinosaur.increaseFoodLevel(vegetarianDinosaur.getMaxFoodLevel());
        return vegetarianDinosaur.getFoodLevel();
    }
}
