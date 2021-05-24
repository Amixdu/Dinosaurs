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
     * Max number of sips a lake can have
     */
    private int maxSips = 25;
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
        setSips(maxSips);
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
     * A lake cannot have more than 25 sips (sips <= maxSips)
     * if sips is 0, fish count will also be 0
     * cant can't have fish in a lake with no sips
     * @param sips
     */
    public void setSips(int sips) {
        this.sips = Math.min(sips, maxSips);
        if (sips <= 0)
            setFishCount(0);
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
        this.fishCount = Math.min(fishCount, 25);
    }

    /**
     * Passage of Time for Lake
     * Each turn, there is a 60% chance of incrementing fishCount by 1, given that the lake has any sips
     * @param location The location of the Ground
     */
    @Override
    public void tick(Location location) {
        if (rand.nextInt(100) < 60 && sips > 0){
            // success -> increment fishCount
            setFishCount(getFishCount() + 1);
        }
    }
}
