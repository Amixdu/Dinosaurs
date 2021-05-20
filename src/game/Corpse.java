package game;

import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

/**
 * Implement the corpse class
 * @author Amindu Kaushal Kumarasinghe
 */
public class Corpse extends PortableItem {
    private char corpseType;
    private int rounds;
    /**
     * Counter to make sure corspe has 3 portions when pterodactyls eat
     */
    private int count;
    /***
     * Constructor.
     * @param name the name of this Item
     * @param corpseType type of dinosaur that is dead
     */
    public Corpse(String name, char corpseType) {
        super(name, 'C');
        this.corpseType = corpseType;
        rounds = 0;
        count = 3;
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
        // if pterodactyl has completely eaten
        if (count == 0){
            currentLocation.removeItem(this);
        }
        if (corpseType == 'S' || corpseType == 'R' || corpseType == 's' || corpseType == 'r'){
            if(rounds >= 200){
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

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }
}
