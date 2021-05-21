package game;

import edu.monash.fit2099.engine.Location;

import java.util.Random;


/**
 * Implement the egg class
 * @author Amindu Kaushal Kumarasinghe
 * @author Abhishek Shrestha
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
        if (rounds >= hatchPeriod + 1 && !currentLocation.containsAnActor()){
            // egg hatches
            // q : stegosaur egg, w : brachiosaur egg, e : allosaur egg
            // s : baby stegosaur, r : baby brachiosaur, a : baby allosaur

            // determine sex of baby dinosaur
            Random rand = new Random();
            Sex sexOfBaby;
            if (rand.nextBoolean()){
                sexOfBaby = Sex.Male;
            } else {
                sexOfBaby = Sex.Female;
            }
            System.out.printf("Egg at (%d,%d) hatched to become a baby Dinosaur!\n", currentLocation.x(), currentLocation.y());


            if (displayChar == 'e'){
                // allosaur egg -> baby allosaur
                currentLocation.addActor(new Allosaur("Allosaur", sexOfBaby, 20, AgeGroup.BABY));
                currentLocation.removeItem(this);
                Player.increaseEcoPoints(100);
            } else if (displayChar == 'q') {
                // stegosaur egg -> baby stegosaur
                currentLocation.addActor(new Stegosaur("Stegosaur", sexOfBaby, 10, AgeGroup.BABY));
                currentLocation.removeItem(this);
                Player.increaseEcoPoints(1000);
            } else if (displayChar == 'w') {
                // brachiosaur egg -> baby brachiosaur
                currentLocation.addActor(new Brachiosaur("Brachiosaur", sexOfBaby, 10, AgeGroup.BABY));
                currentLocation.removeItem(this);
                Player.increaseEcoPoints(1000);
            } else if (displayChar == 'y') {
                // pterodactyl egg -> baby pterodactyl
                currentLocation.addActor(new Pterodactyl("Pterodactyl", sexOfBaby, 10, AgeGroup.BABY, 30));
                currentLocation.removeItem(this);
                Player.increaseEcoPoints(1000);
            }

        }
    }
}
