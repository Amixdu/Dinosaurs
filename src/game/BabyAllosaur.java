package game;

import edu.monash.fit2099.engine.*;

public class BabyAllosaur extends CarnivorousDinosaur {
    int growth;
    int unconsciousCount;
    int foodLevel;
    int timeToGrow;
    Behaviour wBehaviour;
    Behaviour hBehaviour;
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


    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        boolean isAdult = growth(map);
        if (!isAdult){
            if (this.isConscious()){
                this.hurt(1);
                if (this.hitPoints < hungerAmount){
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
            int currentHitPoints = this.hitPoints;
            System.out.println("The baby allosaur at (" + location.x() + "," + location.y() + ") grew into an adult!");
            map.removeActor(this);
            location.addActor(new Allosaur("Allosaur", 'A', currentHitPoints));
            return true;
        }
        return false;
    }
}
