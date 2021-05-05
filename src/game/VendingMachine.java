package game;

import edu.monash.fit2099.engine.*;

public class VendingMachine extends Ground {
    int fruitPrice;
    int vMealKitPrice;
    int cMealKitPrice;
    int stegEggPrice;
    int brachEggPrice;
    int alloEggPrice;
    int laserGunPrice;

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

    public int getFruitPrice() {
        return fruitPrice;
    }

    public int getvMealKitPrice() {
        return vMealKitPrice;
    }

    public int getcMealKitPrice() {
        return cMealKitPrice;
    }

    public int getStegEggPrice() {
        return stegEggPrice;
    }

    public int getBrachEggPrice() {
        return brachEggPrice;
    }

    public int getAlloEggPrice() {
        return alloEggPrice;
    }

    public int getLaserGunPrice() {
        return laserGunPrice;
    }

    @Override
    public Actions allowableActions(Actor actor, Location location, String direction) {
        return super.allowableActions(actor, location, direction);
    }

}
