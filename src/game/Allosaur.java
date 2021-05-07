package game;

import edu.monash.fit2099.engine.*;

public class Allosaur extends Actor {
    int unconsciousCount;
    int maxUconsciousRounds;
    int hungerAmount;
    int foodLevel;
    Behaviour wBehaviour;
    Behaviour hBehaviour;
    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     */
    public Allosaur(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        unconsciousCount = 0;
        maxUconsciousRounds = 20;
        hungerAmount = 90;
        foodLevel = hitPoints;
        wBehaviour = new WanderBehaviour();
        hBehaviour = new SeekFruitBehaviour();
    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {

        if (this.foodLevel > 0){
            this.foodLevel = this.foodLevel - 1;
            if (this.foodLevel < hungerAmount){
                System.out.println(this.name + "at (" + map.locationOf(this).x() + "," + map.locationOf(this).y() + ") is hungry!");
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
            else{
                Action wander = wBehaviour.getAction(this, map);
                if (wander != null)
                    return wander;
                else{
                    return new DoNothingAction();
                }
            }
        }
        else {
            if (unconsciousCount < maxUconsciousRounds){
                this.unconsciousCount += 1;
                System.out.println(this.name + " at (" + map.locationOf(this).x() + "," + map.locationOf(this).y() + ") is unconscious!");
            }
            else {
                System.out.println(this.name + " at (" + map.locationOf(this).x() + "," + map.locationOf(this).y() + ") died  due to lack of food!");
                Corpse corpse = new Corpse("Corpse", 'C', false, this.getDisplayChar());
                map.locationOf(this).addItem(corpse);
                map.removeActor(this);
            }

            return new DoNothingAction();
        }


    }
}
