package game;

import edu.monash.fit2099.engine.*;


/**
 * Implement the Allosaur class
 * @author Amindu Kaushal Kumarasinghe
 * @author Abhishek Shreshta
 */
public class Allosaur extends CarnivorousDinosaur {

    /**
     * hunger Behavior for Allosaur
     */
    private Behaviour hBehaviour;
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
                20, 50, ageGroup, 50, 100, true);

        //behaviors
        hBehaviour = new SeekMeatBehaviour(getAgeGroup() == AgeGroup.ADULT);
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
        if (superAction instanceof MateAction || superAction instanceof LayEggAction || superAction instanceof MoveActorToMateAction) {
            superActionSuccess = true;
        }
        Action finalAction = superAction;
        if (this.isConscious()) {
            // if hungry
            if (this.hitPoints < this.getHungerAmount()) {
                System.out.println(this.name + " at (" + map.locationOf(this).x() + "," + map.locationOf(this).y() + ") is hungry!");
                Action hungerMovement = hBehaviour.getAction(this, map);

                // breeding takes precedence over hunger
                // if no breeding action, then hunger movement
                if (!superActionSuccess) {
                    if (hungerMovement != null) {
                        finalAction = hungerMovement;
                    } else {
                        // if hunger movement is null -> no food in map
                        // if Stegosaur is nearby, the Allosaur attacks it
                        Action attack = aBehaviour.getAction(this, map);
                        if (attack != null) {
                            finalAction = attack;
                        }
                    }
                }
            } else {
                // if not hungry, check if stegosaur is nearby to attack

                // breeding takes precedence over attacking Stegosaur
                // if no breeding action, then hunger movement

                if (!superActionSuccess){
                    Action attack = aBehaviour.getAction(this, map);
                    if (attack != null){
                        finalAction = attack;
                    }
                }
            }
        }
        return finalAction;
    }
}
