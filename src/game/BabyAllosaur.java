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
     * Rounds that the dinosaur can stay unconscious before dying
     */
    int unconsciousCount;
    /**
     * No. of rounds for the baby to grow to an adult
     */
    int timeToGrow;
    /**
     * Wander behaviour
     */
    Behaviour wBehaviour;
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
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     */
    public BabyAllosaur(String name, char displayChar) {
        super(name, displayChar, 20, 100, 20, 90);
        growth = 0;
        unconsciousCount = 0;
        this.timeToGrow = 5;
        wBehaviour = new WanderBehaviour();
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
        if (!isAdult){
            if (this.isConscious()){
                this.hurt(1);
                if (this.hitPoints < this.getHungerAmount()){
                    System.out.println(this.name + "at (" + map.locationOf(this).x() + "," + map.locationOf(this).y() + ") is hungry!");
                    Action hungerMovement = hBehaviour.getAction(this, map);
                    if (hungerMovement != null)
                        return hungerMovement;
                    else{
                        // If null is returned, it means no food in map, so dinosaur just wanders
                        // If null is returned, it means no food in map, so dinosaur just wanders.
                        // if stegosaur nearby, the allosaur attacks it
                        Action attack = aBehaviour.getAction(this, map);
                        if (attack != null){
                            return attack;
                        }
//                      if no stegosaurs nearby, just wander
                        else{
                            Action wander = wBehaviour.getAction(this, map);
                            if (wander != null)
                                return wander;
                            else{
                                return new DoNothingAction();
                            }
                        }
                    }
                }
                else{
                    Action wander = wBehaviour.getAction(this, map);
                    if (wander != null)
                        return wander;
                    else{
                        return new DoNothingAction();
                    }
                }
            }
            // if not hungry, check is any stegosaurs are nearby to attack
            else{
                Action attack = aBehaviour.getAction(this, map);
                if (attack != null){
                    return attack;
                }
//                if no stegosaurs nearby, just wander
                else{
                    Action wander = wBehaviour.getAction(this, map);
                    if (wander != null)
                        return wander;
                    else{
                        return new DoNothingAction();
                    }
                }

            }
        }
        else{
            return new DoNothingAction();
        }

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
            location.addActor(new Allosaur("Allosaur", 'A', currentHitPoints));
            return true;
        }
        return false;
    }
}
