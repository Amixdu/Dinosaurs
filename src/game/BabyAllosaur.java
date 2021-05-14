package game;

import edu.monash.fit2099.engine.*;


/**
 * Implement the BabyAllosaur class
 * @author Amindu Kaushal Kumarasinghe
 * @author Abhishek Shrestha
 */
public class BabyAllosaur extends CarnivorousDinosaur {
    /**
     * Keeps track of the age of the baby Allosaur
     */
    int growth;
    /**
     * No. of rounds for the baby to grow to an adult
     */
    int timeToGrow;
    /**
     * Hunger behaviour
     */
    Behaviour hBehaviour;
    /**
     * Attack behaviour
     */
    Behaviour aBehaviour;
    /**
     * Constructor.
     *
     * @param name the name of the BabyAllosaur
     * @param sex sex of the BabyAllosaur
     */
    public BabyAllosaur(String name, Sex sex, AgeGroup ageGroup) {
        // display character for BabyAllosaur is 'a'
        super(name, 'a', sex, 20, 100, 20, 90,
                20, 50, ageGroup, 50);
        growth = 0;
        timeToGrow = 5;

        // behaviors
        hBehaviour = new SeekMeatBehaviour(false);
        aBehaviour = new AllosaurAttackBehavior();


    }

    /**
     * Select and return an action to perform on the current turn and also keeps track of the growth of the baby.
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return the Action to be performed. This method returns either a wander movement,
     *         a hunger movement(looking for food) or a DoNothingAction.
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        boolean isAdult = growth(map);

        Action superAction = super.playTurn(actions, lastAction, map, display);
        boolean superActionSuccess = false;
        if (superAction instanceof MateAction || superAction instanceof LayEggAction || superAction instanceof MoveActorToMateAction) {
            superActionSuccess = true;
        }
        Action finalAction = superAction;

        if (!isAdult) {
            // if still a baby

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
        } else{
            // if is adult, then this baby Allosaur is removed from map, so Do nothing
            finalAction =  new DoNothingAction();
        }
        return finalAction;
    }

    /**
     * Check whether a baby is grown and if so, create an adult allosaur, with starting hit points as the current hit points as the baby.
     *
     * @param map
     * @return
     */
    private boolean growth(GameMap map){
        growth = growth + 1;
        if (growth >= timeToGrow){
            Location location = map.locationOf(this);
            int currentHitPoints = this.hitPoints;
            map.removeActor(this);
            System.out.println(currentHitPoints);
            location.addActor(new Allosaur("Allosaur", getSex(), currentHitPoints, AgeGroup.ADULT));
            return true;
        }
        return false;
    }
}
