package game;

public class BabyBrachiosaur extends VegetarianDinosaur{
    /**
     * Constructor
     *
     * @param name                 Name of the Dinosaur
     * @param displayChar          Display char of the dinosaur
     * @param sex                  Sex of the dinosaur
     * @param startingHitPoints    starting hitpoints for the dinosaur
     * @param maxHitPoints         max hitpoints for the dinosaur
     * @param maxUnconsciousRounds number of rounds a dinosaur can stay unconscious without dying
     * @param hungerAmount         Amount of health points below which a dinosaur feels hunger
     * @param turnsToLayEgg        turns it takes to lay eggs
     * @param mateAmount           food level above which mating is possible
     */
    public BabyBrachiosaur(String name, char displayChar, Sex sex, int startingHitPoints, int maxHitPoints, int maxUnconsciousRounds, int hungerAmount, int turnsToLayEgg, int mateAmount) {
        super(name, displayChar, sex, startingHitPoints, maxHitPoints, maxUnconsciousRounds, hungerAmount, turnsToLayEgg, mateAmount);
    }
}
