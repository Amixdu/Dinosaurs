package game;

import edu.monash.fit2099.engine.Location;

public class Egg extends PortableItem{
    int hatchPeriod;
    int rounds;

    public Egg(String name, char displayChar, int hatchPeriod) {
        super(name, displayChar);
        this.hatchPeriod = hatchPeriod;
        rounds = 0;
    }

    @Override
    public void tick(Location currentLocation) {
        super.tick(currentLocation);
        rounds = rounds + 1;
        if (rounds >= hatchPeriod){
            // q : stegosaur egg, w : brachiosaur egg, e : allosaur egg
            // s : baby stegosaur, b : baby brachiosaur, a : baby allosaur
            if (displayChar == 'q'){
                currentLocation.addActor(new Stegosaur("Stegasour", 's'));
                currentLocation.removeItem(this);
            }
            else if (displayChar == 'w'){
                currentLocation.addActor(new Stegosaur("Brachiosaur", 'b'));
                currentLocation.removeItem(this);
            }
            else if (displayChar == 'e'){
                currentLocation.addActor(new Stegosaur("Allosaur", 'a'));
                currentLocation.removeItem(this);
            }

        }
    }
}
