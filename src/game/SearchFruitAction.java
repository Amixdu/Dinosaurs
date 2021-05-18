package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

import java.util.Locale;
import java.util.Random;

/**
 * Action class for player to search for fruit in tree or bush
 * @author Abhishek Shrestha
 */
public class SearchFruitAction extends Action {
    /**
     * Target ground (Fruit Producer) that actor will search
     */
    private FruitProducer target;

    /**
     * Contructor
     * @param target
     */
    public SearchFruitAction(FruitProducer target){
        this.target = target;
    }

    /**
     * Search for fruit in bush or tree
     * increase ecopoints by 10 if successful
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        String result;
        Random rand = new Random();

        // fails if no fruits
        if (!target.containsFruit()){
            result = "Fail! No ripe fruits available";
        } else {
            // 60% chance of failing regardless
            if (rand.nextInt(100) < 60){
                result = "Fail! Can't find any ripe fruits";
            } else {
                // success
                target.removeFruit();
                actor.addItemToInventory(new Fruit());

                // increase eco points
                result = "Success! Fruit added to inventory";
                Player.increaseEcoPoints(10);
            }
        }
        return result;
    }

    /**
     * Returns a descriptive stirng
     * @param actor The actor performing the action.
     * @return a text to put on the screen
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor.toString() + " searches for fruit";
    }
}
