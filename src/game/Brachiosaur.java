package game;

import edu.monash.fit2099.engine.*;

public class Brachiosaur extends VegetarianDinosaur{
    /**
     * Constructor.
     *
     * @param name the name of the Actor
     */
    public Brachiosaur(String name) {
        super(name, 'R', 100, 160, 15, 140);
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
