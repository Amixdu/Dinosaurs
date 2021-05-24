package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

import java.util.List;
import java.util.Scanner;

/**
 * Implement the FeedVegetarianAction class
 * @author Amindu Kaushal Kumarasinghe
 */
public class FeedVegetarianAction extends Action {
    private VegetarianDinosaur vegetarianDinosaur;

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


        int beforeFeeding = vegetarianDinosaur.getHitPoints();
        int afterFeeding;

        // Cant feed if already full
        if (vegetarianDinosaur.getHitPoints() >= vegetarianDinosaur.getMaxHitPoints()){
            return (result + ", but " + vegetarianDinosaur.getName() + " is full!");
        }

        List<Item> inventory = actor.getInventory();
        String userInput = userInput();
        // Going through inventory searching for fruit or vegetarian meal kits
        for (int i = 0; i < inventory.size(); i++){
            char displayChar = inventory.get(i).getDisplayChar();
            if (userInput.equals("A")){
                // feeding fruit
                if (displayChar == 'f'){
                    actor.removeItemFromInventory(inventory.get(i));
                    vegetarianDinosaur.heal(20);
                    Player.increaseEcoPoints(10);
                    afterFeeding = vegetarianDinosaur.getHitPoints();
                    return (result + ", successfully fed (Hit points increased from " + beforeFeeding + " to " + afterFeeding + ")");
                }
            }
            else if (userInput.equals("B")){
                // feeding vegetarian meal kit
                if (displayChar == 'v'){
                    actor.removeItemFromInventory(inventory.get(i));
                    vegetarianDinosaur.heal(vegetarianDinosaur.getMaxHitPoints());
                    afterFeeding = vegetarianDinosaur.getHitPoints();
                    return (result + ", successfully fed (Hit points increased from " + beforeFeeding + " to " + afterFeeding + ")");
                }
            }
            else{
                System.out.println("Please enter a valid input");
            }
        }

        return ("Item not in inventory");

    }

    @Override
    public String menuDescription(Actor actor) {
        return actor.toString() + " feeds " + vegetarianDinosaur.getName();
    }

    /**
     * Method to get user input on whether to feed fruit or vegetarian meal kit
     * @return string represnting user input
     */
    private String userInput(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press A to feed fruit\nPress B to feed vegetarian meal kit");
        String result = scanner.next();
        return result;

    }

}
