package game;

import edu.monash.fit2099.engine.Location;

/**
 * Implement the AllosaurEgg class
 * @author Amindu Kaushal Kumarasinghe
 */
public class AllosaurEgg extends Egg{
    /**
     * Select and return an action to perform on the current turn.
     *
     * @return the Action to be performed. This method returns either a wander movement,
     *         a hunger movement(looking for food) or a DoNothingAction.
     */
    public AllosaurEgg() {
        super("Allosaur Egg", 'e', 50);
    }

}
