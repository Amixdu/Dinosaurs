package game;

import edu.monash.fit2099.engine.*;


/**
 * Implement the Allosaur class
 * @author Amindu Kaushal Kumarasinghe
 * @author Abhishek Shreshta
 */
public class Allosaur extends CarnivorousDinosaur {

    /**
     * Attack Behavior for Allosaur
     */
    private Behaviour aBehaviour;

    /**
     * Constructor
     *
     * @param name              Name of the Dinosaur
     * @param sex               Sex of the dinosaur
     * @param startingHitPoints starting hitpoints for the dinosaur
     */
    public Allosaur(String name, Sex sex, int startingHitPoints, AgeGroup ageGroup) {
        super(name, 'A', sex, startingHitPoints, 100, 20, 90,
                20, 50, ageGroup, 50, 100, 50);

        //behaviors
        aBehaviour = new AllosaurAttackBehavior();
    }

    /**
     * Select and return an action to perform on the current turn.
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return the Action to be performed. This method returns either a wander movement,
     * a hunger movement(looking for food) or a DoNothingAction.
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        Action superAction = super.playTurn(actions, lastAction, map, display);
        boolean superActionSuccess = false;
        if (superAction instanceof MateAction || superAction instanceof LayEggAction ||
                superAction instanceof MoveActorToMateAction || superAction instanceof MoveActorToConsumeAction ||
                superAction instanceof EatFoodAction || superAction instanceof DrinkWaterAction) {
            superActionSuccess = true;
        }
        Action finalAction = superAction;
        if (this.isConscious()) {
            // breeding takes precedence over hunger
            // if no breeding action, then hunger movement
            if (!superActionSuccess) {
                // if hunger movement is null -> no food in map
                // if Stegosaur is nearby, the Allosaur attacks it
                Action attack = aBehaviour.getAction(this, map);
                if (attack != null) {
                    finalAction = attack;
                }
            }

        }
        return finalAction;
    }
}
