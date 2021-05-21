package game;

import game.AgeGroup;
import game.CarnivorousDinosaur;
import game.Sex;

public class Pterodactyl extends CarnivorousDinosaur {

    int fuel;
    /**
     * Constructor
     *
     * @param name                 Name of the Dinosaur
     * @param sex                  Sex of the dinosaur
     * @param startingHitPoints    starting hitpoints for the dinosaur
     * @param ageGroup             Age group of the dino (Baby or Adult)
     */
//    public Pterodactyl(String name, Sex sex, int startingHitPoints, int maxHitPoints, int maxUnconsciousRounds, int hungerAmount, int turnsToLayEgg, int mateAmount, AgeGroup ageGroup, int timeToGrow, int maxWaterLevel) {
//        super(name, 'P', sex, startingHitPoints, maxHitPoints, maxUnconsciousRounds, hungerAmount, turnsToLayEgg, mateAmount, ageGroup, timeToGrow, maxWaterLevel, 30);
//        this.addCapability(Flight.YES);
//    }

    public Pterodactyl(String name, Sex sex, int startingHitPoints, AgeGroup ageGroup, int fuel){
        super(name, 'P', sex, startingHitPoints, 100, 20, 90,
                10, 50, ageGroup, 30, 100, 30);
        this.addCapability(Flight.YES);
        this.fuel = 30 - (30 - fuel);
    }
}
