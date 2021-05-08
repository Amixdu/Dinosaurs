package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

import java.util.List;
import java.util.Scanner;

public class BuyItemAction extends Action {

    /**
     * Perform the Buying action, by getting user input and checking if enough eco points.
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a description of what happened that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        String option = userInput();
        List<Item> inventory = actor.getInventory();
        Player player = (Player) actor;
        String result;
        VendingMachine vendingMachine = new VendingMachine();
        int ecoPoints = player.getEcoPoints();
        if (option.equals("A")){
            if (ecoPoints >= vendingMachine.getFruitPrice()){
                player.addItemToInventory(new Fruit());
                player.reduceEcoPoints(vendingMachine.getFruitPrice());
                result = "Successfully bought a fruit";
            }
            else{
                result = "Not enough Eco Points";
            }
        }
        else if (option.equals("B")){
            if (ecoPoints >= vendingMachine.getvMealKitPrice()){
                player.addItemToInventory(new VegetarianMealKit());
                player.reduceEcoPoints(vendingMachine.getvMealKitPrice());
                result = "Successfully bought a Vegetarian meal kit";
            }
            else{
                result = "Not enough Eco Points";
            }
        }
        else if (option.equals("C")){
            if (ecoPoints >= vendingMachine.getcMealKitPrice()){
                player.addItemToInventory(new CarnivoreMealKit());
                player.reduceEcoPoints(vendingMachine.getcMealKitPrice());
                result = "Successfully bought a Carnivore meal kit";
            }
            else{
                result = "Not enough Eco Points";
            }
        }
        else if (option.equals("D")){
            if (ecoPoints >= vendingMachine.getStegEggPrice()){
                player.addItemToInventory(new StegosaurEgg());
                player.reduceEcoPoints(vendingMachine.getStegEggPrice());
                result = "Successfully bought a Stegosaur egg";
            }
            else{
                result = "Not enough Eco Points";
            }
        }
        else if (option.equals("E")){
            if (ecoPoints >= vendingMachine.getBrachEggPrice()){
                player.addItemToInventory(new BrachiosaurEgg());
                player.reduceEcoPoints(vendingMachine.getBrachEggPrice());
                result = "Successfully bought a Brachiosaur egg";
            }
            else{
                result = "Not enough Eco Points";
            }
        }
        else if (option.equals("F")){
            if (ecoPoints >= vendingMachine.getAlloEggPrice()){
                player.addItemToInventory(new AllosaurEgg());
                player.reduceEcoPoints(vendingMachine.getAlloEggPrice());
                result = "Successfully bought a Allosaur egg";
            }
            else{
                result = "Not enough Eco Points";
            }
        }
        else if (option.equals("G")){
            if (ecoPoints >= vendingMachine.getLaserGunPrice()){
                player.addItemToInventory(new LaserGun());
                player.reduceEcoPoints(vendingMachine.getLaserGunPrice());
                result = "Successfully bought a Laser Gun";
            }
            else{
                result = "Not enough Eco Points";
            }
        }
        else{
            result = "Please choose a valid option";
        }
        return result;
    }

    /**
     * Returns a descriptive string
     * @param actor The actor performing the action.
     * @return the text we put on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor.toString() + " purchases item from vending machine";
    }

    /**
     * Method to get uer input
     * @return a string representing user option for buying an item
     */
    public String userInput(){
        Scanner scanner = new Scanner(System.in);
        String print = "A: Buy Fruit(30 eco points)\nB: Buy Vegetarian Meal Kit(100 eco points)\nC: Buy Carnivore Meal Kit(500 eco points)\nD: Buy Stegosaur Egg(200 eco points)\nE: Buy Brachiosaur Egg(500 eco points)\nF: Buy Allosaur Egg(1000 eco points)\nG: Buy Lazer Gun(500 eco points)";
        System.out.println(print);
        return scanner.next();
    }


}
