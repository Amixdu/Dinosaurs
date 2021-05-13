package game;

import edu.monash.fit2099.engine.Location;

/**
 * Implement the Fruit class
 * @author Amindu Kaushal Kumarasinghe
 * @author Abhishek Shrestha
 */
public class Fruit extends PortableItem{
    private int age;
    /**
     * Constructor
     */
    public Fruit() {
        super("Fruit", 'f');
    }

    @Override
    public void tick(Location currentLocation) {
        age++;
        if (age > 15) {
            currentLocation.removeItem(this);
        }
    }
}
