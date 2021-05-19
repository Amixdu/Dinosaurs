package game;

import edu.monash.fit2099.engine.*;

/**
 * Brachiosaur class
 * @author Abhishek Shreshta
 */
public class Brachiosaur extends VegetarianDinosaur{
    /**
     * Constructor.
     *
     * @param name the name of the Brachiosaur
     * @param sex sex of the Brachiosaur
     * @param ageGroup age group of the Brachiosaur
     */
    public Brachiosaur(String name, Sex sex, AgeGroup ageGroup) {
        super(name, 'R', sex, 100, 160, 15, 140,
                30, 70, ageGroup, 50, 200, true);
    }

    /**
     * Contructor
     * @param name name of the brachiosaur
     * @param sex sex of the brachiosaur
     * @param startingHitPoints starting hit points of the brachiosaur
     * @param ageGroup age group of the brachiosaur
     */
    public Brachiosaur(String name, Sex sex, int startingHitPoints, AgeGroup ageGroup) {
        super(name, 'R', sex, startingHitPoints, 160, 15, 140,
                30, 70, ageGroup, 50, 200, true);
    }

    /**
     * Select and return correct action to perform for the Brachiosaur
     * Always executes DestroyBushAction for Brachiosaur
     * Then it simply calls super
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return Action to perform
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        if (isConscious()){
            Ground currentGround = map.locationOf(this).getGround();
            new DestroyBushAction(currentGround).execute(this, map);
        }
        return super.playTurn(actions, lastAction, map, display);
    }
}
