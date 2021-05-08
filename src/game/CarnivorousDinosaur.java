package game;

import edu.monash.fit2099.engine.*;

public class CarnivorousDinosaur extends Actor {
    int unconsciousCount;
    int maxUnconsciousRounds;
    int hungerAmount;
    int foodLevel;
    Behaviour wBehaviour;
    Behaviour hBehaviour;
    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param startingHitPoints   the Actor's starting hit points
     */
    public CarnivorousDinosaur(String name, char displayChar, int startingHitPoints, int maxHitPoints, int maxUconsciousRounds, int hungerAmount) {
        super(name, displayChar, maxHitPoints);
        // if current of baby was 20hp, (100 - 20 is 80 and when hurt (80) is used current hp goes down to 20 and -1 for the growing round)
        this.hurt(maxHitPoints - startingHitPoints - 1);
        this.maxUnconsciousRounds = maxUconsciousRounds;
        this.hungerAmount = hungerAmount;
        unconsciousCount = 0;
        wBehaviour = new WanderBehaviour();
        hBehaviour = new SeekMeatBehaviour(false);
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
        actions.add(new AttackAction(this));
        actions.add(new FeedCarnivoreAction(this));
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
        return null;
    }

    /**
     * Getter method to retrieve the name of current dinosaur
     * @return name of dinosaur
     */
    public String getName(){
        return this.name;
    }

    /**
     * Getter method to retrieve the current hit points of dinosaur
     * @return current hit points of dinosaur
     */
    public int getHitPoints(){
        return this.hitPoints;
    }

    /**
     * Getter method to retrieve the max hit points of dinosaur
     * @return max hit points of dinosaur
     */
    public int getMaxHitPoints(){
        return this.maxHitPoints;
    }
}
