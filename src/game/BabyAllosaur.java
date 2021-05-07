package game;

import edu.monash.fit2099.engine.*;

public class BabyAllosaur extends Actor {
    int growth;
    int unconsciousCount;
    int maxUnconsciousRounds;
    int hungerAmount;
    int foodLevel;
    int timeToGrow;
    Behaviour wBehaviour;
    Behaviour hBehaviour;
    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     */
    public BabyAllosaur(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        growth = 0;
        this.maxUnconsciousRounds = 20;
        this.hungerAmount = 90;
        foodLevel = hitPoints;
        unconsciousCount = 0;
        this.timeToGrow = 50;
        wBehaviour = new WanderBehaviour();
        hBehaviour = new SeekFruitBehaviour();


    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        boolean isAdult = growth(map);
        if (!isAdult){
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
                if (unconsciousCount < maxUnconsciousRounds){
                    this.unconsciousCount += 1;
                    System.out.println(this.name + "at (" + map.locationOf(this).x() + "," + map.locationOf(this).y() + ") is unconscious!");
                }
                else {
                    System.out.println(this.name + "at (" + map.locationOf(this).x() + "," + map.locationOf(this).y() + ") died  due to lack of food!");
                    Corpse corpse = new Corpse("Corpse", 'C', false, this.getDisplayChar());
                    map.locationOf(this).addItem(corpse);
                    map.removeActor(this);
                }

                return new DoNothingAction();
            }
        }
        else{
            return new DoNothingAction();
        }

    }

    private boolean growth(GameMap map){
        growth = growth + 1;
        if (growth >= timeToGrow){
            Location location = map.locationOf(this);
            int food = this.foodLevel;
            map.removeActor(this);
            location.addActor(new Allosaur("Allosaur", 'A', food));
            return true;
        }
        return false;
    }
}
