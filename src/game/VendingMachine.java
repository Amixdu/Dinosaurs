package game;

import edu.monash.fit2099.engine.*;

public class VendingMachine extends Ground {
    /**
     * price of a fruit
     */
    int fruitPrice;
    /**
     * price of a vegetarian meal kit
     */
    int vMealKitPrice;
    /**
     * price of a carnivore meal kit
     */
    int cMealKitPrice;
    /**
     * price of a stegosaur egg
     */
    int stegEggPrice;
    /**
     * price of a brachiosaur egg
     */
    int brachEggPrice;
    /**
     * price of a an allosaur egg
     */
    int alloEggPrice;
    /**
     * price of a laser gun
     */
    int laserGunPrice;

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

}
