package game;

import edu.monash.fit2099.engine.Location;


/**
 * Implement the egg class
 * @author Amindu Kaushal Kumarasinghe
 */
public class Egg extends PortableItem{
    private int hatchPeriod;
    private int rounds;

    /**
     * Constructor
     * @param name name of the egg
     * @param displayChar display character of the egg
     * @param hatchPeriod number of rounds taken for the egg to hatch
     */
    public Egg(String name, char displayChar, int hatchPeriod) {
        super(name, displayChar);
        this.hatchPeriod = hatchPeriod;
        rounds = 0;
    }

    /**
     * Inform an Item on the ground of the passage of time. After a set number of rounds the egg will hatch
     * @param currentLocation The location of the ground on which we lie.
     */
    @Override
    public void tick(Location currentLocation) {
        super.tick(currentLocation);
        rounds = rounds + 1;
        if (rounds >= hatchPeriod + 1){
            // q : stegosaur egg, w : brachiosaur egg, e : allosaur egg
            // s : baby stegosaur, b : baby brachiosaur, a : baby allosaur
            if (displayChar == 'e'){
                currentLocation.addActor(new BabyAllosaur("Allosaur", 'a'));
                currentLocation.removeItem(this);
            }

        }
    }
}
