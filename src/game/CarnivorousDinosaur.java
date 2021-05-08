package game;

import edu.monash.fit2099.engine.*;

public class CarnivorousDinosaur extends Actor {
    int growth;
    int unconsciousCount;
    int maxUnconsciousRounds;
    int hungerAmount;
    int foodLevel;
    int timeToGrow;
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
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        return null;
    }
}
