package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

public class Corpse extends Item {
    char corpseType;
    int rounds;
    GameMap map;
    /***
     * Constructor.
     *  @param name the name of this Item
     * @param displayChar the character to use to represent this item if it is on the ground
     * @param portable true if and only if the Item can be picked up
     */
    public Corpse(String name, char displayChar, boolean portable, char corpseType) {
        super(name, displayChar, portable);
        this.corpseType = corpseType;
        rounds = 0;
    }

    @Override
    public void tick(Location currentLocation) {
        super.tick(currentLocation);
        rounds = rounds + 1;
        if (corpseType == 'S' || corpseType == 'B'){
            if(rounds >= 20){
                currentLocation.removeItem(this);
            }
        }
        else if (corpseType == 'A'){
            if (rounds >= 40){
                currentLocation.removeItem(this);
            }
        }
    }
}
