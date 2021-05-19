package game;

import edu.monash.fit2099.engine.*;

/**
 * Implementation of vegetarianDinosaur class
 * @author Amindu Kaushal Kumarasinghe
 * @author Abhishek Shrestha
 */
public class VegetarianDinosaur extends Dinosaur {

    /**
     * Hunger Behavior for Vegetarian Dinosaur
     */
    private Behaviour hBehaviour;

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
     */
    public VegetarianDinosaur(String name, char displayChar, Sex sex,  int startingHitPoints, int maxHitPoints, int maxUnconsciousRounds,
                              int hungerAmount, int turnsToLayEgg, int mateAmount, AgeGroup ageGroup, int timeToGrow, int maxWaterLevel, boolean landBased) {
        super(name, displayChar,sex, startingHitPoints, maxHitPoints, maxUnconsciousRounds, hungerAmount, turnsToLayEgg,
                mateAmount, ageGroup, timeToGrow, maxWaterLevel, landBased);
        hBehaviour = new SeekFruitBehaviour(displayChar);
    }

    /**
     * Returns a collection of the Actions that the otherActor can do to the current Actor.
     * Currently adds two allowable actions, for attacking and feeding.
     *
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return A collection of Actions (For attacking and feeding)
     */
    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        Actions actions = new Actions();
        actions.add(new FeedVegetarianAction(this));
        return actions;

    }

    /**
     * Select and return an action to perform on the current turn
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return the Action to be performed.
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        Action superAction = super.playTurn(actions, lastAction, map, display);
        boolean superActionSuccess = false;
        if (superAction instanceof MateAction || superAction instanceof LayEggAction || superAction instanceof MoveActorToMateAction){
            superActionSuccess = true;
        }
        Action finalAction = superAction;
        if (this.isConscious()){
            // if hungry
            if (this.hitPoints < getHungerAmount()){
                System.out.println(this.name + " at (" + map.locationOf(this).x() + "," + map.locationOf(this).y() + ") is hungry!");
                Action hungerMovement = hBehaviour.getAction(this, map);

                // breeding takes precedences over hunger
                // if breeding action is not returned, then hunger action
                if (hungerMovement != null && !superActionSuccess){
                    finalAction = hungerMovement;
                }
            }
        }
        return finalAction;
    }
}
