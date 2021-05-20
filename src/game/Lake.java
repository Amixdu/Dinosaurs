package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

import java.util.Random;

/**
 * Lake Class
 * @author Abhishek Shreshta
 */
public class Lake extends Ground {
    /**
     * number of sips this lake has
     */
    private int sips;
    /**
     * Number of fish at this lake. Capped at 25
     * Each fish increases hitPoints by 5
     */
    private int fishCount;

    private Random rand = new Random();

    /**
     * Constructor.
     */
    public Lake() {
        super('~');
        setSips(25);
        fishCount = 5;
    }

    /**
     * Checks if the given actor can enter this terrain
     * Only game.Pterodactyls (since they have Flight capability) can enter
     * @param actor the Actor to check
     * @return true if it has flight capability
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        boolean retVal = false;
        if (actor.hasCapability(Flight.YES)){
            retVal = true;
        }
        return retVal;
    }

    /**
     * sips setter
     * @param sips
     */
    public void setSips(int sips) {
        this.sips = sips;
    }

    /**
     * sips getter
     * @return sips
     */
    public int getSips() {
        return sips;
    }

    /**
     * fishCount getter
     * @return fishCount
     */
    public int getFishCount() {
        return fishCount;
    }

    /**
     * fishcount setter
     * Fish Count cannot exceed 25
     * @param fishCount
     */
    public void setFishCount(int fishCount) {
        int newFistCount = this.fishCount + fishCount;
        this.fishCount = Math.min(newFistCount, 25);
    }

    /**
     * Passage of Time for Lake
     * Each turn, there is a 60% chance of incrementing fishCount by 1
     * @param location The location of the Ground
     */
    @Override
    public void tick(Location location) {
        if (rand.nextInt(100) < 60){
            // success -> increment fishCount
            setFishCount(getFishCount() + 1);
        }
    }
}
