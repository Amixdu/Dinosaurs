package game;

import edu.monash.fit2099.engine.*;

/**
 * Implement the Allosaur class
 * @author Amindu Kaushal Kumarasinghe
 */
public class Allosaur extends CarnivorousDinosaur {
    private int unconsciousCount;
    Behaviour wBehaviour;
    Behaviour hBehaviour;
    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param currentHitPoints   the Actor's starting hit points
     */
    public Allosaur(String name, char displayChar, int currentHitPoints) {
        super(name, displayChar, 100, 100, 20, 90);
        this.hurt(maxHitPoints - currentHitPoints);
        unconsciousCount = 0;
        wBehaviour = new WanderBehaviour();
        hBehaviour = new SeekMeatBehaviour(true);
    }

    /**
     * Select and return an action to perform on the current turn.
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

        if (this.isConscious()){
            this.hurt(1);
            if (this.hitPoints < this.getHungerAmount()){
                System.out.println(this.name + " at (" + map.locationOf(this).x() + "," + map.locationOf(this).y() + ") is hungry!");
                Action hungerMovement = hBehaviour.getAction(this, map);
                if (hungerMovement != null)
                    return hungerMovement;
                else{
                    // If null is returned, it means no food in map, so dinosaur just wanders
                    Action wander = wBehaviour.getAction(this, map);
                    if (wander != null)
                        return wander;
                    else{
                        return new DoNothingAction();
                    }
                }
            }
            // if not hungry, wander
            else{
                Action wander = wBehaviour.getAction(this, map);
                if (wander != null)
                    return wander;
                else{
                    return new DoNothingAction();
                }
            }
        }
        // if not conscious, update counter
        else {
            if (unconsciousCount < this.getMaxUnconsciousRounds()){
                this.unconsciousCount += 1;
                System.out.println(this.name + " at (" + map.locationOf(this).x() + "," + map.locationOf(this).y() + ") is unconscious!");
            }
            else {
                System.out.println(this.name + " at (" + map.locationOf(this).x() + "," + map.locationOf(this).y() + ") died  due to lack of food!");
                Corpse corpse = new Corpse("Corpse", false, this.getDisplayChar());
                map.locationOf(this).addItem(corpse);
                map.removeActor(this);
            }

            return new DoNothingAction();
        }
    }

}
