package game;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

/**
 * Implement the Bush class
 * @author Amindu Kaushal Kumarasinghe
 */
public class Bush extends FruitProducer {
    /**
     * Constructor.
     */
    public Bush() {
        super('b');
        setFruits(1);
    }

    /**
     * Passage of time for Bush
     * Grows a fruit with 10% chance
     * @param location The location of the Ground
     */
    @Override
    public void tick(Location location) {
        // grow fruit with 10% chance
        growFruit(10);
    }
}
