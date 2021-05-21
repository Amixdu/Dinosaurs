package game;

import edu.monash.fit2099.engine.*;

/**
 * Implement the CarnivorousDinosaur class
 * @author Amindu Kaushal Kumarasinghe
 * @author Abhishek Shreshta
 */
public class CarnivorousDinosaur extends Dinosaur {

    /**
     * Constructor
     * @param name Name of the Dinosaur
     * @param displayChar Display char of the dinosaur
     * @param sex Sex of the dinosaur
     * @param startingHitPoints starting hitpoints for the dinosaur
     * @param maxHitPoints max hitpoints for the dinosaur
     * @param maxUnconsciousRounds number of rounds a dinosaur can stay unconscious without dying
     * @param hungerAmount Amount of health points below which a dinosaur feels hunger
     * @param turnsToLayEgg turns it takes to lay eggs
     * @param mateAmount food level above which mating is possible
     * @param ageGroup Age group of the dino (Baby or Adult)
     * @param timeToGrow turns needed to turn baby dinosaur to adult
     */
    public CarnivorousDinosaur(String name, char displayChar, Sex sex,  int startingHitPoints, int maxHitPoints, int maxUnconsciousRounds,
                               int hungerAmount, int turnsToLayEgg, int mateAmount, AgeGroup ageGroup, int timeToGrow, int maxWaterLevel, int corpsePoints) {
        super(name, displayChar,sex, startingHitPoints, maxHitPoints, maxUnconsciousRounds, hungerAmount,
                turnsToLayEgg, mateAmount, ageGroup, timeToGrow, maxWaterLevel, corpsePoints);
    }

    /**
     * Returns a collection of the Actions that the otherActor can do to the current Actor.
     * Currently adds a feeding Action.
     *
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return A collection of Actions (For attacking and feeding)
     */
    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        Actions actions = new Actions();
        actions.add(new FeedCarnivoreAction(this));
        return actions;
    }
}
