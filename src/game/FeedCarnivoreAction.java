package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

import java.util.List;
import java.util.Scanner;

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

        int beforeFeeding = carnivorousDinosaur.getHitPoints();
        int afterFeeding;

        // Cant feed if already full
        if (carnivorousDinosaur.getHitPoints() >= carnivorousDinosaur.getMaxHitPoints()){
            return (result + ", but " + carnivorousDinosaur.getName() + " is full!");
        }

        // get users input
        String userInput = userInput();
        List<Item> inventory = actor.getInventory();
        // Going through inventory searching for food, feed if found
        for (int i = 0; i < inventory.size(); i++){
            // feeding a corpse
            if (userInput.equals("A")){
                if (inventory.get(i).getDisplayChar() == 'C'){
                    Corpse corpse = (Corpse) inventory.get(i);
                    int points = corpse.getCorpsePoints();
                    actor.removeItemFromInventory(inventory.get(i));
                    carnivorousDinosaur.heal(points);
                    afterFeeding = carnivorousDinosaur.getHitPoints();
                    return (result + ", successfully fed (Hit points increased from " + beforeFeeding + " to " + afterFeeding + ")");
                }
            }
            // feeding a carnivore meal kit
            else if(userInput.equals("B")){
                if (inventory.get(i).getDisplayChar() == 'c'){
                    actor.removeItemFromInventory(inventory.get(i));
                    carnivorousDinosaur.heal(carnivorousDinosaur.getMaxHitPoints());
                    afterFeeding = carnivorousDinosaur.getHitPoints();
                    return (result + ", successfully fed (Hit points increased from " + beforeFeeding + " to " + afterFeeding + ")");
                }
            }
            // feeding an egg
            else if (userInput.equals("C")){
                char displayChar = inventory.get(i).getDisplayChar();
                if (displayChar == 'q' || displayChar == 'w' || displayChar == 'e' || displayChar == 'y'){
                    actor.removeItemFromInventory(inventory.get(i));
                    carnivorousDinosaur.heal(10);
                    afterFeeding = carnivorousDinosaur.getHitPoints();
                    return (result + ", successfully fed (Hit points increased from " + beforeFeeding + " to " + afterFeeding + ")");
                }
            }
            else{
                System.out.println("Please enter a valid input");
            }

        }
        return ("Item not in inventory");
    }

    /**
     * Method to get user input on whether to feed fruit or vegetarian meal kit
     * @return string represnting user input
     */
    private String userInput(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press A to feed Corpse\nPress B to feed carnivore meal kit\nPress C to feed egg");
        String result = scanner.next();
        return result;

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
