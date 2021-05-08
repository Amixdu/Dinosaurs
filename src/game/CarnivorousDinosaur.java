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
        // Starting out with 20
        this.hurt(maxHitPoints - startingHitPoints);
        this.maxUnconsciousRounds = maxUconsciousRounds;
        this.hungerAmount = hungerAmount;
        unconsciousCount = 0;
        wBehaviour = new WanderBehaviour();
        hBehaviour = new SeekMeatBehaviour(false);
    }

    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        Actions actions = new Actions();
        actions.add(new AttackAction(this));
        actions.add(new FeedCarnivoreAction(this));
        return actions;
    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        return null;
    }

    public String getName(){
        return this.name;
    }

    public int getHitPoints(){
        return this.hitPoints;
    }

    public int getMaxHitPoints(){
        return this.maxHitPoints;
    }
}
