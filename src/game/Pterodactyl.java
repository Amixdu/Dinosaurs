package game;

import game.AgeGroup;
import game.CarnivorousDinosaur;
import game.Sex;

public class Pterodactyl extends CarnivorousDinosaur {
    /**
     * Constructor
     *
     * @param name                 Name of the Dinosaur
     * @param sex                  Sex of the dinosaur
     * @param startingHitPoints    starting hitpoints for the dinosaur
     * @param maxHitPoints         max hitpoints for the dinosaur
     * @param maxUnconsciousRounds number of rounds a dinosaur can stay unconscious without dying
     * @param hungerAmount         Amount of health points below which a dinosaur feels hunger
     * @param turnsToLayEgg        turns it takes to lay eggs
     * @param mateAmount           food level above which mating is possible
     * @param ageGroup             Age group of the dino (Baby or Adult)
     * @param timeToGrow           turns needed to turn baby dinosaur to adult
     * @param maxWaterLevel
     */
    public Pterodactyl(String name, Sex sex, int startingHitPoints, int maxHitPoints, int maxUnconsciousRounds, int hungerAmount, int turnsToLayEgg, int mateAmount, AgeGroup ageGroup, int timeToGrow, int maxWaterLevel) {
        super(name, 'P', sex, startingHitPoints, maxHitPoints, maxUnconsciousRounds, hungerAmount, turnsToLayEgg, mateAmount, ageGroup, timeToGrow, maxWaterLevel);
        this.addCapability(Flight.YES);
    }
}
