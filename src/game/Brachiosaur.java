package game;

import edu.monash.fit2099.engine.*;

public class Brachiosaur extends VegetarianDinosaur{
    /**
     * Constructor.
     *
     * @param name the name of the Brachiosaur
     * @param sex sex of the Brachiosaur
     */
    public Brachiosaur(String name, Sex sex, AgeGroup ageGroup) {
        super(name, 'R', sex, 100, 160, 15, 140,
                30, 70, ageGroup, 50, 200);
    }

    public Brachiosaur(String name, Sex sex, int startingHitPoints, AgeGroup ageGroup) {
        super(name, 'R', sex, startingHitPoints, 160, 15, 140,
                30, 70, ageGroup, 50, 200);
    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        if (isConscious()){
            Ground currentGround = map.locationOf(this).getGround();
            new DestroyBushAction(currentGround).execute(this, map);
        }
        return super.playTurn(actions, lastAction, map, display);
    }
}
