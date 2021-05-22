package game;

import edu.monash.fit2099.engine.*;

/**
 * Implementation of VendingMachine class
 * @author Amindu Kaushal Kumarasinghe
 */
public class VendingMachine extends Ground {
    /**
     * price of a fruit
     */
    final private int fruitPrice;
    /**
     * price of a vegetarian meal kit
     */
    final private int vMealKitPrice;
    /**
     * price of a carnivore meal kit
     */
    final private int cMealKitPrice;
    /**
     * price of a stegosaur egg
     */
    final private int stegEggPrice;
    /**
     * price of a brachiosaur egg
     */
    final private int brachEggPrice;
    /**
     * price of a an allosaur egg
     */
    final private int alloEggPrice;
    /**
     * price of a laser gun
     */
    final private int laserGunPrice;
    /**
     * Price of a pterodactyl egg
     */
    final private int pteroEggPrice;

    /**
     * Constructor
     */
    public VendingMachine() {
        super('X');
        fruitPrice = 30;
        vMealKitPrice = 100;
        cMealKitPrice = 500;
        stegEggPrice = 200;
        brachEggPrice = 500;
        alloEggPrice = 1000;
        laserGunPrice = 500;
        pteroEggPrice = 200;
    }

    /**
     *
     * @return price of fruit
     */
    public int getFruitPrice() {
        return fruitPrice;
    }

    /**
     *
     * @return price of vegetarian meal kit
     */
    public int getvMealKitPrice() {
        return vMealKitPrice;
    }

    /**
     *
     * @return price of carnivore meal kit
     */
    public int getcMealKitPrice() {
        return cMealKitPrice;
    }

    /**
     *
     * @return price of stegosaur egg
     */
    public int getStegEggPrice() {
        return stegEggPrice;
    }

    /**
     *
     * @return price of brachiosaur egg
     */
    public int getBrachEggPrice() {
        return brachEggPrice;
    }

    /**
     *
     * @return price of allosaur egg
     */
    public int getAlloEggPrice() {
        return alloEggPrice;
    }

    /**
     *
     * @return price of laser gun
     */
    public int getLaserGunPrice() {
        return laserGunPrice;
    }

    /**
     *
     * @return price of pterodactyl egg
     */
    public int getPteroEggPrice() {
        return pteroEggPrice;
    }

    /**
     * Returns an action list containing actions that be done to the VendingMachine
     *
     * @param actor the Actor acting
     * @param location the current Location
     * @param direction the direction of the Ground from the Actor
     * @return BuyItemAction
     */

    @Override
    public Actions allowableActions(Actor actor, Location location, String direction) {
        Actions actions = new Actions();
        actions.add(new BuyItemAction());
        return actions;
    }

    /**
     * To define if the actor can enter a vending machin square
     * @param actor the Actor to check
     * @return false
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return false;
    }

    /**
     * to define if a vending machine can block any thrown object
     * @return true
     */
    @Override
    public boolean blocksThrownObjects() {
        return true;
    }


}
