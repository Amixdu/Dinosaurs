package game;

import edu.monash.fit2099.engine.*;

public class VegetarianDinosaur extends Actor {
    int foodLevel;
    int maxFoodLevel;
    int unconsciousCount;
    int maxUconsciousRounds;
    int hungerAmount;
    int deathCount;
    int maxDeadRounds;
    private Behaviour wBehaviour;
    private Behaviour hBehaviour;
    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     */
    public VegetarianDinosaur(String name, char displayChar, int hitPoints, int foodLevel, int maxFoodLevel, int maxUconsciousRounds, int hungerAmount, int maxDeadRounds) {
        super(name, displayChar, hitPoints);
        this.foodLevel = foodLevel;
        this.maxFoodLevel = maxFoodLevel;
        this.maxUconsciousRounds = maxUconsciousRounds;
        this.hungerAmount = hungerAmount;
        this.maxDeadRounds = maxDeadRounds;
        wBehaviour = new WanderBehaviour();
        hBehaviour = new SeekFruitBehaviour();

    }



    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        Actions actions = new Actions();
        actions.add(new AttackAction(this));
        actions.add(new FeedVegetarianAction(this));
        return actions;

    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {

//		System.out.println(this.foodLevel);

        // reduce food level each turn
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
                System.out.println(this.name + "at (" + map.locationOf(this).x() + "," + map.locationOf(this).y() + ") is unconscious!");
            }
            else {
                System.out.println(this.name + "at (" + map.locationOf(this).x() + "," + map.locationOf(this).y() + ") died  due to lack of food!");
//                map.removeActor(this);
                if (deathCount < maxDeadRounds){
                    deathCount += 1;
                }
                else{
                    map.removeActor(this);
                }

            }

            return new DoNothingAction();
        }



//		return new DoNothingAction();
    }

    public String getName(){
        return this.name;
    }

    public int getFoodLevel() {
        return foodLevel;
    }

    public void increaseFoodLevel(int amount){
        this.foodLevel += amount;
        if (this.foodLevel > maxFoodLevel){
            this.foodLevel = maxFoodLevel;
        }
    }

    public int getMaxFoodLevel() {
        return maxFoodLevel;
    }
}
