package game;

import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

public class Corpse extends Item {
    private char corpseType;
    private int rounds;
    /***
     * Constructor.
     *  @param name the name of this Item
     * @param portable true if and only if the Item can be picked up
     */
    public Corpse(String name,  boolean portable, char corpseType) {
        super(name, 'C', portable);
        this.corpseType = corpseType;
        rounds = 0;
    }

    /**
     * Inform an Item on the ground of the passage of time.  After a set period of ticks, the corpse disappears
     * This method is called once per turn, if the item rests upon the ground.
     * @param currentLocation The location of the ground on which we lie.
     */
    @Override
    public void tick(Location currentLocation) {
        super.tick(currentLocation);
        rounds = rounds + 1;
        if (corpseType == 'S' || corpseType == 'B' || corpseType == 's' || corpseType == 'b'){
            if(rounds >= 20){
                currentLocation.removeItem(this);
            }
        }
        else if (corpseType == 'A' || corpseType == 'a'){
            if (rounds >= 40){
                currentLocation.removeItem(this);
            }
        }
    }

    /**
     * Method to retrieve the type of dinosaur to which the corpse belongs to
     * @return display char of dinosaur to which the corpse belongs to
     */
    public char getCorpseType() {
        return corpseType;
    }
}
